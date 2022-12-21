package com.example.nyschoollist.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.nyschoollist.R
import com.example.nyschoollist.SchoolDataUiState
import com.example.nyschoollist.model.CombinedSchoolData
import java.util.*

/**
 * Defining a const value to help with unit testing
 */
const val SCHOOL_LIST_TEST_TAG = "school_list_test_tag"

/**
 * Method to display the list of schools in NewYork based on the result of the fetch API
 * In case of success  it displays  the list of all schools retrieved
 * In case of failure it tries to retry the connection
 * During the Loading phase it displays appropriate message to the user
 */

@Composable
fun SchoolListScreen(
    selectedSchool: (String) -> Unit,
    schoolDataUiState: SchoolDataUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (schoolDataUiState) {
        is SchoolDataUiState.Loading -> LoadingScreen(modifier)
        is SchoolDataUiState.Success -> SchoolDataListScreen(
            selectedSchool,
            schoolDataUiState.schoolData,
            modifier
        )
        else -> ErrorScreen(retryAction, modifier)
    }
}

/**
 * Composable function to Handle the Loading screen
 */
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column {
            Image(
                modifier = Modifier.size(200.dp),
                painter = painterResource(R.drawable.loading_img),
                contentDescription = stringResource(R.string.loading)
            )
            Text(
                text = stringResource(id = R.string.loading_data),
                modifier = Modifier.padding(top = 2.dp, start = 8.dp, bottom = 8.dp),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

/**
 * Composable method to display the error screen with retry button
 */
@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(stringResource(R.string.loading_failed))
        Button(onClick = retryAction) {
            Text(stringResource(R.string.retry))
        }
    }
}

/**
 * Composable screen to show the list of New York schools.
 * This data is based on the backend api call
 * The View model combines the data of the 2 api calls and sends
 * the list to this composable function
 * The composable function uses lazyColumn to show a list of schools
 */
@Composable
fun SchoolDataListScreen(
    selectedSchool: (String) -> Unit,
    schoolData: List<CombinedSchoolData>,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .testTag(SCHOOL_LIST_TEST_TAG),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(
            items = schoolData,
            key = { schoolData ->
                schoolData.dbn
            }
        ) { schoolData ->
            SchoolDetailsDataCard(selectedSchool, schoolData = schoolData)
        }
    }
}

@Composable
fun SchoolDetailsDataCard(
    selectedSchool: (String) -> Unit,
    schoolData: CombinedSchoolData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable(onClick = { selectedSchool(schoolData.dbn) })
            .fillMaxWidth(),
        elevation = 8.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(R.string.school_title,
                    schoolData.schoolName.replaceFirstChar {
                        if (it.isLowerCase()) it.titlecase(
                            Locale.getDefault()
                        ) else it.toString()
                    }),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp)
            )
        }
    }
}