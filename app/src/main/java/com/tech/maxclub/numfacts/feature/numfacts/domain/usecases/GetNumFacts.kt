package com.tech.maxclub.numfacts.feature.numfacts.domain.usecases

import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFact
import com.tech.maxclub.numfacts.feature.numfacts.domain.repositories.NumFactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetNumFacts @Inject constructor(
    private val numFactsRepository: NumFactsRepository,
) {
    operator fun invoke(): Flow<List<NumFact>> =
        numFactsRepository.getNumFacts()
            .map { numFacts ->
                numFacts.sortedByDescending { it.timestamp }
            }
}