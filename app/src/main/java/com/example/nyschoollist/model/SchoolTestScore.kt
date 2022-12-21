package com.example.nyschoollist.model

import com.squareup.moshi.Json

/**
 * Data class that defines test scores of a school which include
 * unique school id
 * Name of the school
 * SAT scores for Math, Reading and Writing
 */
data class SchoolTestScore(
    val dbn: String,
    @Json(name = "school_name") val schoolName: String,
    @Json(name = "sat_critical_reading_avg_score") val satReading: String,
    @Json(name = "sat_math_avg_score") val satMath: String,
    @Json(name = "sat_writing_avg_score") val satWriting: String,
)