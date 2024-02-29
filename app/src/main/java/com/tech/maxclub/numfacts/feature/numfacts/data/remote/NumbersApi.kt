package com.tech.maxclub.numfacts.feature.numfacts.data.remote

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface NumbersApi {

    @GET("{number}/{type}")
    suspend fun getNumFact(
        @Path("number") number: String,
        @Path("type") type: String,
    ): String

    @GET("random/{type}")
    suspend fun getRandomNumFact(
        @Path("type") type: String,
    ): String

    companion object {
        private const val BASE_URL = "http://numbersapi.com/"

        fun create(): NumbersApi =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
                .create(NumbersApi::class.java)
    }
}