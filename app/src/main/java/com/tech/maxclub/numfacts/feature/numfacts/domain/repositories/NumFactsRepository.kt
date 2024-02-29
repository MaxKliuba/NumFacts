package com.tech.maxclub.numfacts.feature.numfacts.domain.repositories

import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFact
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumType
import kotlinx.coroutines.flow.Flow

interface NumFactsRepository {

    fun getNumFact(number: String, type: NumType): Flow<NumFact>

    fun getRandomNumFact(type: NumType): Flow<NumFact>

    fun getNumFacts(): Flow<List<NumFact>>

    fun getNumFactById(numFactId: Int): Flow<NumFact>

    suspend fun deleteNumFactById(numFactId: Int)

    suspend fun tryRestoreNumFactById(numFactId: Int)

    suspend fun permanentlyDeleteMarkedNotes()
}