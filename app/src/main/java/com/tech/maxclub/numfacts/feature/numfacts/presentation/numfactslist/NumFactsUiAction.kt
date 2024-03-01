package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist

sealed class NumFactsUiAction {
    data class ShowErrorMessage(val errorMessage: String) : NumFactsUiAction()
}