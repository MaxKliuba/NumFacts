package com.tech.maxclub.numfacts.feature.numfacts.di

import android.content.Context
import com.tech.maxclub.numfacts.feature.numfacts.data.local.NumFactsDao
import com.tech.maxclub.numfacts.feature.numfacts.data.local.NumFactsDatabase
import com.tech.maxclub.numfacts.feature.numfacts.data.remote.NumbersApi
import com.tech.maxclub.numfacts.feature.numfacts.data.repositories.NumFactsRepositoryImpl
import com.tech.maxclub.numfacts.feature.numfacts.domain.repositories.NumFactsRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NumbersApiModule {

    @Singleton
    @Provides
    fun provideNumbersApi(): NumbersApi = NumbersApi.create()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class NumFactsRepositoryModule {

    @Singleton
    @Binds
    abstract fun bindNumFactsRepository(
        numFactsRepositoryImpl: NumFactsRepositoryImpl
    ): NumFactsRepository
}

@Module
@InstallIn(SingletonComponent::class)
object NumFactsDatabaseModule {

    @Singleton
    @Provides
    fun provideNumFactsDatabase(@ApplicationContext context: Context): NumFactsDatabase =
        NumFactsDatabase.getInstance(context)

    @Singleton
    @Provides
    fun provideNumFactsDao(numFactsDatabase: NumFactsDatabase): NumFactsDao =
        numFactsDatabase.numFactsDao
}