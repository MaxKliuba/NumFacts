package com.tech.maxclub.numfacts.feature.numfacts.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.tech.maxclub.numfacts.feature.numfacts.data.local.entities.NumFactEntity

@Database(
    entities = [NumFactEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class NumFactsDatabase : RoomDatabase() {
    abstract val numFactsDao: NumFactsDao

    companion object {
        private const val DATABASE_NAME = "num_facts_db"

        @Volatile
        private var INSTANCE: NumFactsDatabase? = null

        fun getInstance(context: Context): NumFactsDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    NumFactsDatabase::class.java,
                    DATABASE_NAME,
                )
                    .build()
                    .also { INSTANCE = it }
            }
    }
}