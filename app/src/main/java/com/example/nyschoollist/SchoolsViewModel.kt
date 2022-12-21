package com.example.nyschoollist

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nyschoollist.data.SchoolDetailsRepository
import com.example.nyschoollist.data.SchoolTestScoreRepository
import com.example.nyschoollist.model.CombinedSchoolData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

/**
 * UI state for displaying List of schools screen
 */
sealed interface SchoolDataUiState {
    data class Success(val schoolData: List<CombinedSchoolData>) : SchoolDataUiState
    object Error : SchoolDataUiState
    object Loading : SchoolDataUiState
}

/**
 * ViewModel containing the data fetched from the two API calls and method to retrieve the data
 */
class SchoolsViewModel(
    private val schoolTestScoreRepository: SchoolTestScoreRepository,
    private val schoolDetailsRepository: SchoolDetailsRepository
) : ViewModel() {

    var schoolDataUiState: SchoolDataUiState by mutableStateOf(SchoolDataUiState.Loading)
        private set

    private var schoolDetailsList = listOf<CombinedSchoolData>()

    /**
     * Using the Coroutine exception handler to handle any errors scenarios
     */
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        //Handle your exception
        schoolDataUiState= SchoolDataUiState.Error
    }

    init {
        getCombinedSchoolData()
    }

    fun getCombinedSchoolData() {
        viewModelScope.launch(coroutineExceptionHandler) {
            schoolDataUiState = SchoolDataUiState.Loading
            schoolDataUiState = try {
                /**
                 * Call the network API's here to fetch the details for all schools
                 * getSchoolsTestScore -> Fetches SAT test scores for all schools
                 * getSchoolDetails -> Fetches Details for all schools
                 */
                val getSchoolsTestScore = async { schoolTestScoreRepository.getSchoolSatScores() }
                val getSchoolDetails = async { schoolDetailsRepository.getSchoolDetails() }

                /** We need to wait for both queries to complete to combine data for each school */
                val testScoreDataResponse = getSchoolsTestScore.await()
                val schoolDetailsDataResponse = getSchoolDetails.await()

                /**
                 * Create a mutable list to whole the combined data for each school
                 */
                val combinedSchoolData = mutableListOf<CombinedSchoolData>()

                /**
                 * Every school has a unique id represented by the dbn key
                 * We use that to append data corresponding to the school
                 * The below logic can be improved by using a hashmap to store key-value
                 * The key can be dbn value since its unique. We can traverse the test score response
                 * and populate the hashmap and then use this hashmap to append further school details based
                 * on the response of the second api. Hashmap's have O(1) time and hence guarantee a faster lookup
                 * However in the interest of time I have kept the logic simpler here by doing a list traversal
                 * */
                for (testScore in testScoreDataResponse) {
                    for (schoolData in schoolDetailsDataResponse) {
                        if (testScore.dbn == schoolData.dbn) {
                            combinedSchoolData.add(
                                CombinedSchoolData(
                                    testScore.dbn,
                                    testScore.schoolName,
                                    schoolData.phoneNumber,
                                    schoolData.website,
                                    testScore.satReading,
                                    testScore.satMath,
                                    testScore.satWriting
                                )
                            )
                        }
                    }
                }
                schoolDetailsList = combinedSchoolData
                SchoolDataUiState.Success(combinedSchoolData)
            } catch (e: IOException) {
                SchoolDataUiState.Error
            } catch (e: HttpException) {
                SchoolDataUiState.Error
            } catch (e: Exception) {
                SchoolDataUiState.Error
            }
        }
    }

    fun getSchoolDetailsList(): List<CombinedSchoolData> {
        return schoolDetailsList
    }

    /**
     * Factory  for SchoolsViewModel that takes schoolDetailsRepository and schoolTestScoreRepository as dependency
     */
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as SchoolListApplication)
                val schoolTestScoreRepository = application.container.schoolTestScoreRepository
                val schoolDetailsRepository = application.container.schoolDetailsRepository
                SchoolsViewModel(schoolTestScoreRepository, schoolDetailsRepository)
            }
        }
    }
}