package com.example.nyschoollist.data

import com.example.nyschoollist.network.SchoolDetailsApiService
import com.example.nyschoollist.network.SchoolTestScoreApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * Dependency Injection container at the application level.
 */
interface AppContainer {
    val schoolTestScoreRepository: SchoolTestScoreRepository
    val schoolDetailsRepository: SchoolDetailsRepository
}

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val BASE_URL = "https://data.cityofnewyork.us/resource/"

    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    /**
     * Use the Retrofit builder to build a retrofit object using a Moshi converter
     */
    @kotlinx.serialization.ExperimentalSerializationApi
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

    /**
     * Retrofit service object for creating api calls
     * This API gets SAT test scores of all New York schools
     */
    private val retrofitTestScoreService: SchoolTestScoreApiService by lazy {
        retrofit.create(SchoolTestScoreApiService::class.java)
    }

    /**
     * DI implementation for the SchoolTestScoreApiService
     */
    override val schoolTestScoreRepository: SchoolTestScoreRepository by lazy {
        DefaultSchoolTestScoreRepository(retrofitTestScoreService)
    }

    /**
     * Retrofit service object for creating api calls
     * This API gets details of all New York Schools
     */
    private val retrofitSchoolDetailsService: SchoolDetailsApiService by lazy {
        retrofit.create(SchoolDetailsApiService::class.java)
    }

    /**
     * DI implementation for the SchoolDetailsApiService
     */
    override val schoolDetailsRepository: SchoolDetailsRepository by lazy {
        DefaultSchoolDetailsRepository(retrofitSchoolDetailsService)
    }
}