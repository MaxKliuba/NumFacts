package com.tech.maxclub.numfacts.feature.numfacts.data.repositories

import com.tech.maxclub.numfacts.feature.numfacts.data.local.NumFactsDao
import com.tech.maxclub.numfacts.feature.numfacts.data.local.entities.NumFactEntity
import com.tech.maxclub.numfacts.feature.numfacts.data.mappers.toNumFact
import com.tech.maxclub.numfacts.feature.numfacts.data.remote.NumbersApi
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumFact
import com.tech.maxclub.numfacts.feature.numfacts.domain.models.NumType
import com.tech.maxclub.numfacts.feature.numfacts.domain.repositories.NumFactsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.Date
import javax.inject.Inject

class NumFactsRepositoryImpl @Inject constructor(
    private val numbersApi: NumbersApi,
    private val numFactsDao: NumFactsDao,
) : NumFactsRepository {

    override fun getNumFact(number: String, type: NumType): Flow<NumFact> = flow {
        val fact = numbersApi.getNumFact(number, type.value)

        val numFactEntity = NumFactEntity(
            type = type,
            number = number,
            fact = fact,
            timestamp = Date().time
        )
        numFactsDao.insertNumFacts(numFactEntity)

        emit(numFactEntity.toNumFact())
    }

    override fun getRandomNumFact(type: NumType): Flow<NumFact> = flow {
        val fact = numbersApi.getRandomNumFact(type.value)
        val number = fact.substringBefore("is", missingDelimiterValue = "Random").trim()

        val numFactEntity = NumFactEntity(
            type = type,
            number = number,
            fact = fact,
            timestamp = Date().time
        )
        numFactsDao.insertNumFacts(numFactEntity)

        emit(numFactEntity.toNumFact())
    }

    override fun getNumFacts(): Flow<List<NumFact>> =
        numFactsDao.getNumFacts()
            .map { entities ->
                entities.map { it.toNumFact() }
            }

    override fun getNumFactById(numFactId: Int): Flow<NumFact> =
        numFactsDao.getNumFactById(numFactId)
            .map { it.toNumFact() }

    override suspend fun deleteNumFactById(numFactId: Int) {
        numFactsDao.deleteNumFactById(numFactId)
    }

    override suspend fun tryRestoreNumFactById(numFactId: Int) {
        numFactsDao.tryRestoreNumFactById(numFactId)
    }

    override suspend fun permanentlyDeleteMarkedNotes() {
        numFactsDao.permanentlyDeleteMarkedNumFacts()
    }
}