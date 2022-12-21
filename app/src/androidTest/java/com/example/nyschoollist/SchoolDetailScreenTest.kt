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
import com.example.nyschoollist.ui.screens.SchoolDetailsRow
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class SchoolDetailScreenTest {
    /**
     * To access to an empty activity, the code uses ComponentActivity instead of
     * MainActivity.
     */

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    /**
     * Function to validate the School Name is displayed correctly
     */
    @Test
     fun schoolDetails_verifyWhenSchoolNamePassed_correctDataIsDisplayed() {
        composeTestRule.setContent {
            SchoolDetailsRow(R.string.school_name,"test school")
        }
        composeTestRule.onNodeWithText("School Name").assertIsDisplayed()
        composeTestRule.onNodeWithText("test school").assertIsDisplayed()
     }

    /**
     * Function to validate the School Phone Number is displayed correctly
     */
    @Test
    fun schoolDetails_verifyWhenSchoolPhoneNumberPassed_correctDataIsDisplayed() {
        composeTestRule.setContent {
            SchoolDetailsRow(R.string.phone_number,"3424567895")
        }
        composeTestRule.onNodeWithText("Phone Number").assertIsDisplayed()
        composeTestRule.onNodeWithText("3424567895").assertIsDisplayed()
    }

    /**
     * Function to validate the School Website is displayed correctly
     */
    @Test
    fun schoolDetails_verifyWhenSchoolWebsitePassed_correctDataIsDisplayed() {
        composeTestRule.setContent {
            SchoolDetailsRow(R.string.website, "www.test.com")
        }
        composeTestRule.onNodeWithText("Website").assertIsDisplayed()
        composeTestRule.onNodeWithText("www.test.com").assertIsDisplayed()
    }

    /**
     * Function to validate the SAT Math Score is displayed correctly
     */
    @Test
    fun schoolDetails_verifyWhenMathScorePassed_correctDataIsDisplayed() {
        composeTestRule.setContent {
            SchoolDetailsRow(R.string.math_score, "545")
        }
        composeTestRule.onNodeWithText("SAT Math Score").assertIsDisplayed()
        composeTestRule.onNodeWithText("545").assertIsDisplayed()
    }

    /**
     * Function to validate the SAT Reading Score is displayed correctly
     */
    @Test
    fun schoolDetails_verifyWhenReadingScorePassed_correctDataIsDisplayed() {
        composeTestRule.setContent {
            SchoolDetailsRow(R.string.reading_score, "345")
        }
        composeTestRule.onNodeWithText("SAT Reading Score").assertIsDisplayed()
        composeTestRule.onNodeWithText("345").assertIsDisplayed()
    }

    /**
     * Function to validate the SAT Writing Score is displayed correctly
     */
    @Test
    fun schoolDetails_verifyWhenWritingScorePassed_correctDataIsDisplayed() {
        composeTestRule.setContent {
            SchoolDetailsRow(R.string.writing_score, "145")
        }
        composeTestRule.onNodeWithText("SAT Writing Score").assertIsDisplayed()
        composeTestRule.onNodeWithText("145").assertIsDisplayed()
    }
}