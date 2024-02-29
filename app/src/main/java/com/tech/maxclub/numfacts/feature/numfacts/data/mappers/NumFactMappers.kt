package com.tech.maxclub.numfacts.feature.numfacts.data.mappers

import com.tech.maxclub.numfacts.feature.numfacts.data.local.entities.NumFactEntity
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFact

fun NumFactEntity.toNumFact(): NumFact =
    NumFact(
        id = id,
        type = type,
        number = number,
        fact = fact,
        timestamp = timestamp,
    )