package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.maxclub.numfacts.core.update
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumType
import com.tech.maxclub.numfacts.feature.numfacts.domain.repositories.NumFactsRepository
import com.tech.maxclub.numfacts.feature.numfacts.domain.usecases.GetNumFacts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NumFactsViewModel @Inject constructor(
    private val numFactsRepository: NumFactsRepository,
    private val getNumFactsUseCase: GetNumFacts,
) : ViewModel() {

    private val _uiState = mutableStateOf(
        NumFactsUiState(
            numTypeValue = NumType.TRIVIA,
            numberValue = "",
            randomNumTypeValue = NumType.MATH,
            isListLoading = false,
            numFacts = emptyList(),
        )
    )
    val uiState: State<NumFactsUiState> = _uiState

    private val uiActionChannel = Channel<NumFactsUiAction>()
    val uiAction = uiActionChannel.receiveAsFlow()

    private var fetchNumFactJob: Job? = null

    init {
        permanentlyDeleteMarkedNumFacts()
        fetchNumFacts()
    }

    fun changeNumTypeValue(numType: NumType) {
        _uiState.update { it.copy(numTypeValue = numType) }
    }

    fun tryChangeNumberValue(number: String) =
        if (number.length <= 50) {
            _uiState.update { it.copy(numberValue = number) }

            true
        } else false

    fun changeRandomNumTypeValue(numType: NumType) {
        _uiState.update { it.copy(randomNumTypeValue = numType) }
    }

    fun fetchNumFact(number: String, type: NumType) {
        fetchNumFactByTypeAndNumber(type, number)
    }

    fun fetchRandomNumFact(type: NumType) {
        fetchNumFactByTypeAndNumber(type)
    }

    private fun fetchNumFactByTypeAndNumber(type: NumType, number: String? = null) {
        val getNumFactFun = number?.let { numFactsRepository.getNumFact(number, type) }
            ?: numFactsRepository.getRandomNumFact(type)

        fetchNumFactJob?.cancel()
        fetchNumFactJob = getNumFactFun
            .onStart {
                _uiState.update { it.copy(isListLoading = true) }
            }
            .onEach {
                _uiState.update { it.copy(isListLoading = false) }
            }
            .catch { e ->
                e.printStackTrace()

                _uiState.update { it.copy(isListLoading = false) }

                uiActionChannel.send(NumFactsUiAction.ShowErrorMessage(e.message.toString()))
            }
            .launchIn(viewModelScope)
    }

    private fun permanentlyDeleteMarkedNumFacts() {
        viewModelScope.launch {
            numFactsRepository.permanentlyDeleteMarkedNotes()
        }
    }

    private fun fetchNumFacts() {
        getNumFactsUseCase()
            .onStart {
                _uiState.update { it.copy(isListLoading = true) }
            }
            .onEach { numFacts ->
                _uiState.update {
                    it.copy(
                        isListLoading = false,
                        numFacts = numFacts,
                    )
                }
            }
            .catch { e ->
                e.printStackTrace()

                _uiState.update { it.copy(isListLoading = false) }
            }
            .launchIn(viewModelScope)
    }
}