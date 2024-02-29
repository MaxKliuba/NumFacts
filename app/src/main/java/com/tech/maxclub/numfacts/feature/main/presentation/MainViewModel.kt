package com.tech.maxclub.numfacts.feature.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech.maxclub.numfacts.feature.numfacts.domain.repositories.NumFactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val numFactsRepository: NumFactsRepository,
) : ViewModel() {

    private val uiActionChannel = Channel<MainUiAction>()
    val uiAction = uiActionChannel.receiveAsFlow()

    fun deleteNumFact(numFactId: Int) {
        viewModelScope.launch {
            numFactsRepository.deleteNumFactById(numFactId)
            uiActionChannel.send(MainUiAction.ShowNumFactDeletedMessage(numFactId))
        }
    }

    fun tryRestoreNumFact(numFactId: Int) {
        viewModelScope.launch {
            numFactsRepository.tryRestoreNumFactById(numFactId)
        }
    }
}