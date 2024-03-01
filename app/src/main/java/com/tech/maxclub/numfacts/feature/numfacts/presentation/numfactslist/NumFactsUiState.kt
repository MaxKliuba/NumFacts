package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist

import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFact
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumType

data class NumFactsUiState(
    val numTypeValue: NumType,
    val numberValue: String,
    val randomNumTypeValue: NumType,
    val isListLoading: Boolean,
    val numFacts: List<NumFact>,
)