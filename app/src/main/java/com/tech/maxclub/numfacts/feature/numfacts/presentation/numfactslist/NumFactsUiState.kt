package com.tech.maxclub.numfacts.feature.numfacts.presentation.numfactslist

import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFact
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFactPreview
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumType

data class NumFactsUiState(
    val numType: NumType,
    val number: String,
    val randomNumType: NumType,
    val isListLoading: Boolean,
    val numFactPreview: NumFactPreview?,
    val numFacts: List<NumFact>,
)