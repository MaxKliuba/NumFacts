package com.tech.maxclub.numfacts.feature.main.presentation

sealed class MainUiAction {
    data class ShowNumFactDeletedMessage(val numFactId: Int) : MainUiAction()
}
