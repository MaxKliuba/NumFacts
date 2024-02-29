package com.tech.maxclub.numfacts.feature.numfacts.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tech.maxclub.numfacts.feature.numfacts.data.local.entities.NumFactEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NumFactsDao {

    @Query("SELECT * FROM num_facts WHERE deleted = 0")
    fun getNumFacts(): Flow<List<NumFactEntity>>

    @Query("SELECT * FROM num_facts WHERE deleted = 0 AND id = :numFactId")
    fun getNumFactById(numFactId: Int): Flow<NumFactEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNumFacts(vararg numFacts: NumFactEntity)

    @Query("UPDATE num_facts SET deleted = 1 WHERE id = :numFactId")
    suspend fun deleteNumFactById(numFactId: Int)

    @Query("UPDATE num_facts SET deleted = 0 WHERE id = :numFactId")
    suspend fun tryRestoreNumFactById(numFactId: Int)

    @Query("DELETE FROM num_facts WHERE deleted = 1")
    suspend fun permanentlyDeleteMarkedNumFacts()
}