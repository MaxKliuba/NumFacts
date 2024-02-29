package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactdetail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.maxclub.numfacts.core.Screen
import com.tech.maxclub.numfacts.feature.numfacts.domain.repositories.NumFactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class NumFactDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val numFactsRepository: NumFactsRepository,
) : ViewModel() {

    private val _uiState = mutableStateOf<NumFactDetailUiState>(
        NumFactDetailUiState.Loading(
            numFactId = savedStateHandle[Screen.NumFactDetail.ARG_NUM_FACT_ID]
                ?: Screen.NumFactDetail.DEFAULT_NUM_FACT_ID
        )
    )
    val uiState: State<NumFactDetailUiState> = _uiState

    init {
        (_uiState.value as? NumFactDetailUiState.Loading)?.let { loadingState ->
            fetchNumFact(loadingState.numFactId)
        }
    }

    private fun fetchNumFact(numFactId: Int) {
        numFactsRepository.getNumFactById(numFactId)
            .onStart {
                _uiState.value = NumFactDetailUiState.Loading(numFactId)
            }
            .onEach { numFact ->
                _uiState.value = NumFactDetailUiState.Success(numFact)
            }
            .catch { e ->
                e.printStackTrace()
            }
            .launchIn(viewModelScope)
    }
}