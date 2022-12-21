package com.example.nyschoollist.data

import com.example.nyschoollist.model.SchoolDetails
import com.example.nyschoollist.network.SchoolDetailsApiService

/**
 * Repository retrieves details for all schools from underlying data source.
 */
interface SchoolDetailsRepository {
    /** Retrieves list of test scores for all schools */
    suspend fun getSchoolDetails(): List<SchoolDetails>
}

/**
 * Network Implementation of repository that retrieves school test scores from underlying data source.
 */
class DefaultSchoolDetailsRepository(
    private val schoolDetailsApiService: SchoolDetailsApiService
) : SchoolDetailsRepository {
    override suspend fun getSchoolDetails(): List<SchoolDetails> = schoolDetailsApiService.getSchoolDetails()
}
