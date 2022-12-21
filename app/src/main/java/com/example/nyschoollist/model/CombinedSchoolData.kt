package com.example.nyschoollist.model

/**
 * Data class that combines data received from Test Score query API and School List API
 * The data represented here is a combination of SchoolDetails and SchoolTestScore class
 */
data class CombinedSchoolData(
    val dbn: String = "",
    val schoolName: String = "",
    val phoneNumber: String = "",
    val website: String = "",
    val satReading: String = "",
    val satMath: String = "",
    val satWriting: String = ""
)