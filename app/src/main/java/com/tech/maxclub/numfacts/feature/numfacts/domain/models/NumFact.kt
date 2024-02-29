package com.tech.maxclub.numfacts.feature.numfacts.domain.models

data class NumFact(
    val id: Int,
    val type: NumType,
    val number: String,
    val fact: String,
    val timestamp: Long,
)
