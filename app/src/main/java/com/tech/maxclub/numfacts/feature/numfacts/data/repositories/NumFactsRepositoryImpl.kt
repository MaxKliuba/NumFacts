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
import retrofit2.HttpException
import java.util.Date
import javax.inject.Inject

class NumFactsRepositoryImpl @Inject constructor(
    private val numbersApi: NumbersApi,
    private val numFactsDao: NumFactsDao,
) : NumFactsRepository {

    override fun getNumFact(number: String, type: NumType): Flow<NumFact> = flow {
        val response = numbersApi.getNumFact(number, type.name.lowercase())
        val body = response.body()

        if (response.isSuccessful && body != null) {
            val numFactEntity = NumFactEntity(
                type = type,
                number = number,
                fact = body,
                timestamp = Date().time
            )
            numFactsDao.insertNumFacts(numFactEntity)

            emit(numFactEntity.toNumFact())
        } else {
            throw HttpException(response)
        }
    }

    override fun getRandomNumFact(type: NumType): Flow<NumFact> = flow {
        val response = numbersApi.getRandomNumFact(type.name.lowercase())
        val body = response.body()

        if (response.isSuccessful && body != null) {
            val number = body.substringBefore("is", missingDelimiterValue = "Random").trim()

            val numFactEntity = NumFactEntity(
                type = type,
                number = number,
                fact = body,
                timestamp = Date().time
            )
            numFactsDao.insertNumFacts(numFactEntity)

            emit(numFactEntity.toNumFact())
        } else {
            throw HttpException(response)
        }
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