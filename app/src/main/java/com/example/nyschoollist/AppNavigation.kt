package com.example.nyschoollist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nyschoollist.AppDestinations.SCHOOL_ID_KEY
import com.example.nyschoollist.ui.screens.SchoolDetailsScreen
import com.example.nyschoollist.ui.screens.SchoolsApp

/**
 * Mapping the different route the App can take
 * SCHOOL_LIST_ROUTE is for the screen showing list of school
 * SCHOOL_DETAILS_ROUTE is for the details of an individial school
 * SCHOOL_ID_KEY is the unique id for each school
 */
object AppDestinations {
    const val SCHOOL_LIST_ROUTE = "schools"
    const val SCHOOL_DETAILS_ROUTE = "schoolDetails"
    const val SCHOOL_ID_KEY = "schoolId"
}

@Composable
fun AppNavigation(
    startDestination: String = AppDestinations.SCHOOL_LIST_ROUTE
) {
    val navController = rememberNavController()
    val actions = remember(navController) { AppActions(navController) }
    val schoolsViewModel: SchoolsViewModel =
        viewModel(factory = SchoolsViewModel.Factory)

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            AppDestinations.SCHOOL_LIST_ROUTE
        ) {
            SchoolsApp(selectedSchool = actions.selectedSchool, schoolsViewModel)
        }
        composable(
            "${AppDestinations.SCHOOL_DETAILS_ROUTE}/{$SCHOOL_ID_KEY}",
            arguments = listOf(
                navArgument(SCHOOL_ID_KEY) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            arguments.getString(SCHOOL_ID_KEY)?.let {
                SchoolDetailsScreen(
                    schoolsViewModel,
                    schoolId = it,
                    navigateUp = actions.navigateUp
                )
            }
        }
    }
}


private class AppActions(
    navController: NavHostController
) {
    val selectedSchool: (String) -> Unit = { schoolId: String ->
        navController.navigate("${AppDestinations.SCHOOL_DETAILS_ROUTE}/$schoolId")
    }
    val navigateUp: () -> Unit = {
        navController.navigateUp()
    }
}