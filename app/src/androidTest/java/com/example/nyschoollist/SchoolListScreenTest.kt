package com.example.nyschoollist

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.nyschoollist.model.CombinedSchoolData
import com.example.nyschoollist.ui.screens.SCHOOL_LIST_TEST_TAG
import com.example.nyschoollist.ui.screens.SchoolDataListScreen
import org.junit.Assert
import org.junit.Rule
import org.junit.Test


class SchoolListScreenTest {
    /**
     * To access to an empty activity, the code uses ComponentActivity instead of
     * MainActivity.
     */

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    private val listOfSchools = mutableListOf<CombinedSchoolData>(
        CombinedSchoolData(
            "M1290",
            "Test School1",
            "6542728828",
            "www.test.com",
            "654",
            "453",
            "234"
        ),
        CombinedSchoolData(
            "A2290",
            "Test School2",
            "654343828",
            "www.test2.com",
            "354",
            "653",
            "134"
        ),
        CombinedSchoolData(
            "F5290",
            "Test School3",
            "8754728828",
            "www.test3.com",
            "354",
            "553",
            "334"
        ),
        CombinedSchoolData(
            "V1290",
            "Test School4",
            "6542728828",
            "www.test4.com",
            "154",
            "463",
            "274"
        )
    )

    /**
     * Function to validate the screen shown is the list of schools screen
     */
    @Test
    fun navHost_verifyStartDestination() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            AppNavigation()
        }
        Assert.assertEquals(
            AppDestinations.SCHOOL_LIST_ROUTE,
            navController.currentDestination?.route ?: AppDestinations.SCHOOL_LIST_ROUTE
        )
    }

    /**
     * Function to validate if no list of schools present then lazyColumn shows no data
     * We use the test tag to identify the compose element under test
     */
    @Test
    fun schoolList_verifyWhenNoDataPassed_noListShown() {
        composeTestRule.setContent {
            SchoolDataListScreen(
                {},
                listOf()
            )
        }
        composeTestRule.onNodeWithTag(SCHOOL_LIST_TEST_TAG).onChildren().assertCountEquals(0)
    }

    /**
     * Function to validate if list of schools present then the number of schools shown matches
     * the size of list passed
     * We pass a mock list of school as data
     */
    @Test
    fun schoolList_verifyWhenDataPassed_corectCountOfSchoolsDisplayed() {
        composeTestRule.setContent {
            SchoolDataListScreen(
                {},
                listOfSchools
            )
        }
        composeTestRule.onNodeWithTag(SCHOOL_LIST_TEST_TAG).onChildren().assertCountEquals(4)
    }

    /**
     * Function to validate each row in list of school is clickable
     * We pass a mock list of school as data
     */
    @Test
    fun schoolList_verifyWhenDataPassed_listItemIsClickable() {
        composeTestRule.setContent {
            SchoolDataListScreen(
                {},
                listOfSchools
            )
        }
        composeTestRule.onNodeWithTag(SCHOOL_LIST_TEST_TAG).onChildAt(0).assertHasClickAction()
        composeTestRule.onNodeWithTag(SCHOOL_LIST_TEST_TAG).onChildAt(1).assertHasClickAction()
        composeTestRule.onNodeWithTag(SCHOOL_LIST_TEST_TAG).onChildAt(2).assertHasClickAction()
        composeTestRule.onNodeWithTag(SCHOOL_LIST_TEST_TAG).onChildAt(3).assertHasClickAction()
    }

    /**
     * Function to validate data displayed at each row is correct
     * i.e the correct data is displayed
     * We pass a mock list of school as data
     */
    @Test
    fun schoolList_verifyWhenDataPassed_correctSchoolNameDisplayed() {
        composeTestRule.setContent {
            SchoolDataListScreen(
                {},
                listOfSchools
            )
        }
        composeTestRule.onNodeWithTag(SCHOOL_LIST_TEST_TAG).onChildAt(0).assert(hasText("Test School1"))
        composeTestRule.onNodeWithTag(SCHOOL_LIST_TEST_TAG).onChildAt(1).assert(hasText("Test School2"))
        composeTestRule.onNodeWithTag(SCHOOL_LIST_TEST_TAG).onChildAt(2).assert(hasText("Test School3"))
        composeTestRule.onNodeWithTag(SCHOOL_LIST_TEST_TAG).onChildAt(3).assert(hasText("Test School4"))
    }
}