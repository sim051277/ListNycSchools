package com.example.nyschoollist.model

import com.squareup.moshi.Json

/**
 * Data class that defines details of a school which includes
 * unique school id
 * Phone number of the school
 * URL of the schools website
 */
data class SchoolDetails(
    val dbn: String,
    @Json(name = "phone_number") val phoneNumber: String,
    val website: String
)
