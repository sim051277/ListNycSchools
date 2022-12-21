package com.example.nyschoollist.ui.screens

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nyschoollist.R
import com.example.nyschoollist.SchoolsViewModel
import com.example.nyschoollist.model.CombinedSchoolData

/**
 * Function to show the details of the School when a user selects a particular school
 * The following information are displayed to the user
 * School Name, Phone number, Website, SAT Maths, Reading and Writing score
 * In the future we can save this data in a local database to support offline first architecture
 */
@Composable
fun SchoolDetailsScreen(
    schoolsViewModel: SchoolsViewModel,
    schoolId: String,
    navigateUp: () -> Unit
) {
    val schoolData : CombinedSchoolData? = schoolsViewModel.getSchoolDetailsList().find {
        it.dbn == schoolId
    }
    Column(
        modifier = Modifier
            .padding(bottom = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Header(navigateUp)
        schoolData?.let { school ->
            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                SchoolDetailsRow(infoTitle = R.string.school_name, infoDetails = school.schoolName)
                SchoolDetailsRow(infoTitle = R.string.website, infoDetails = school.website)
                SchoolDetailsRow(infoTitle = R.string.phone_number, infoDetails = school.phoneNumber)
                SchoolDetailsRow(infoTitle = R.string.math_score, infoDetails = school.satMath)
                SchoolDetailsRow(infoTitle = R.string.reading_score, infoDetails = school.satReading)
                SchoolDetailsRow(infoTitle = R.string.writing_score, infoDetails = school.satWriting)
            }
        } ?: Text(
            text = stringResource(id = R.string.loading_data),
            modifier = Modifier.padding(top = 2.dp, start = 8.dp, bottom = 8.dp),
            style = MaterialTheme.typography.body1,
            color = MaterialTheme.colors.onSurface
        )
    }
}

/**
 * Composable function to display each information of the School as a card
 * Each card has a heading and a corresponding value
 */
@Composable
fun SchoolDetailsRow(
    @StringRes infoTitle: Int,
    infoDetails: String
) {
    Card(
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.background,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .animateContentSize()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Text(
                text = stringResource(infoTitle),
                modifier = Modifier.padding(top = 8.dp, start = 8.dp),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                text = infoDetails,
                modifier = Modifier.padding(top = 4.dp, start = 8.dp, bottom = 8.dp),
                style = MaterialTheme.typography.subtitle1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

/**
 * Composable function to display the header. This is mainly shown to give the user an option to navigate back
 */
@Composable
private fun Header(
    navigateUp: () -> Unit
) {
    Box {
        TopAppBar(
            backgroundColor = Color.LightGray,
            elevation = 0.dp,
            contentColor = Color.White
        ) {

            IconButton(onClick = navigateUp) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = stringResource(R.string.back_button)
                )
            }
        }
    }
}