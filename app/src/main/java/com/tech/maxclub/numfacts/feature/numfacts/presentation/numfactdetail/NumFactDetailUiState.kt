package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactdetail

import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFact

sealed class NumFactDetailUiState {
    data class Loading(val numFactId: Int) : NumFactDetailUiState()
    data class Success(val numFact: NumFact) : NumFactDetailUiState()
}