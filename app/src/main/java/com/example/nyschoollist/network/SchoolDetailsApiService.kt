package com.example.nyschoollist.network

import com.example.nyschoollist.model.SchoolDetails
import retrofit2.http.GET

/**
 * Public interface that exposes [getSchoolDetails] method
 */
interface SchoolDetailsApiService {
    @GET("s3k6-pzi2.json")
    suspend fun getSchoolDetails(): List<SchoolDetails>
}