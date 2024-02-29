package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.maxclub.numfacts.core.update
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFactPreview
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumType
import com.tech.maxclub.numfacts.feature.numfacts.domain.repositories.NumFactsRepository
import com.tech.maxclub.numfacts.feature.numfacts.domain.usecases.GetNumFacts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NumFactsViewModel @Inject constructor(
    private val numFactsRepository: NumFactsRepository,
    private val getNumFactsUseCase: GetNumFacts,
) : ViewModel() {

    private val _uiState = mutableStateOf(
        NumFactsUiState(
            numType = NumType.TRIVIA,
            number = "0",
            randomNumType = NumType.MATH,
            isListLoading = false,
            numFactPreview = null,
            numFacts = emptyList(),
        )
    )
    val uiState: State<NumFactsUiState> = _uiState

    private var fetchNumFactJob: Job? = null


    init {
        permanentlyDeleteMarkedNumFacts()
        fetchNumFacts()
    }

    fun fetchNumFact() {
        fetchNumFact(_uiState.value.numType, _uiState.value.number)
    }

    fun fetchRandomNumFact() {
        fetchNumFact(_uiState.value.randomNumType)
    }

    private fun fetchNumFact(type: NumType, number: String? = null) {
        val getNumFactFun = number?.let { numFactsRepository.getNumFact(number, type) }
            ?: numFactsRepository.getRandomNumFact(type)

        fetchNumFactJob?.cancel()
        fetchNumFactJob = getNumFactFun
            .onStart {
                val numFactPreview = NumFactPreview(
                    type = type,
                    number = number ?: "Random"
                )
                _uiState.update {
                    it.copy(numFactPreview = numFactPreview)
                }
            }
            .onEach {
                _uiState.update {
                    it.copy(numFactPreview = null)
                }
            }
            .catch { e ->
                e.printStackTrace()

                _uiState.update {
                    it.copy(numFactPreview = null)
                }
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
                _uiState.update {
                    it.copy(isListLoading = true)
                }
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
            }
            .launchIn(viewModelScope)
    }
}