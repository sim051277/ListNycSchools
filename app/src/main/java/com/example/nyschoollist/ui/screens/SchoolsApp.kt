package com.example.nyschoollist.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.nyschoollist.R
import com.example.nyschoollist.SchoolsViewModel

/**
 * Composable method that passes data needed to display list of schools in New York
 */
@Composable
fun SchoolsApp(
    selectedSchool: (String) -> Unit,
    schoolsViewModel: SchoolsViewModel
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(stringResource(R.string.list_name)) }) }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            SchoolListScreen(
                selectedSchool,
                schoolDataUiState = schoolsViewModel.schoolDataUiState,
                retryAction = schoolsViewModel::getCombinedSchoolData
            )
        }
    }
}