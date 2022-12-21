package com.example.nyschoollist.data

import com.example.nyschoollist.model.SchoolTestScore
import com.example.nyschoollist.network.SchoolTestScoreApiService

/**
 * Repository retrieves sat test score data for all schools
 * from underlying data source.
 */
interface SchoolTestScoreRepository {
    /** Retrieves list of test scores for all schools */
    suspend fun getSchoolSatScores(): List<SchoolTestScore>
}

/**
 * Network Implementation of repository that retrieves school test scores from underlying data source.
 */
class DefaultSchoolTestScoreRepository(
    private val schoolTestScoreApiService: SchoolTestScoreApiService
) : SchoolTestScoreRepository {
    override suspend fun getSchoolSatScores(): List<SchoolTestScore> = schoolTestScoreApiService.getTestScore()
}