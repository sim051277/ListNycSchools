package com.example.nyschoollist.network

import com.example.nyschoollist.model.SchoolTestScore
import retrofit2.http.GET

/**
 * Public interface that exposes [getTestScore] method
 */
interface SchoolTestScoreApiService {
    @GET("f9bf-2cp4.json")
    suspend fun getTestScore(): List<SchoolTestScore>
}