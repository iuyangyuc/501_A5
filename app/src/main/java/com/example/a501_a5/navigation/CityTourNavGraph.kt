package com.example.a501_a5.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.a501_a5.ui.screens.CategoriesScreen
import com.example.a501_a5.ui.screens.HomeScreen
import com.example.a501_a5.ui.screens.LocationDetailScreen
import com.example.a501_a5.ui.screens.LocationListScreen

sealed class CityTourDestination(val route: String) {
    data object Home : CityTourDestination("home")
    data object Categories : CityTourDestination("categories")
    data object LocationList : CityTourDestination("list/{category}") {
        const val ARG_CATEGORY = "category"
        fun createRoute(category: String): String {
            val encodedCategory = Uri.encode(category)
            return "list/$encodedCategory"
        }
    }

    data object LocationDetail :
        CityTourDestination("detail/{category}/{locationId}") {
        const val ARG_CATEGORY = "category"
        const val ARG_LOCATION_ID = "locationId"

        fun createRoute(category: String, locationId: Int): String {
            val encodedCategory = Uri.encode(category)
            return "detail/$encodedCategory/$locationId"
        }
    }
}

@Composable
fun CityTourNavGraph(
    navController: NavHostController,
    onStackClearedAtHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val actions = remember(navController) {
        CityTourActions(
            navigateToCategories = {
                navController.navigate(CityTourDestination.Categories.route)
            },
            navigateToLocationList = { category ->
                navController.navigate(
                    CityTourDestination.LocationList.createRoute(category),
                )
            },
            navigateToLocationDetail = { category, locationId ->
                navController.navigate(
                    CityTourDestination.LocationDetail.createRoute(
                        category,
                        locationId,
                    ),
                )
            },
            navigateBack = {
                navController.popBackStack()
            },
            navigateHome = {
                navController.navigate(CityTourDestination.Home.route) {
                    popUpTo(CityTourDestination.Home.route) {
                        inclusive = true
                    }
                }
                onStackClearedAtHome()
            },
        )
    }

    NavHost(
        navController = navController,
        startDestination = CityTourDestination.Home.route,
    ) {
        composable(route = CityTourDestination.Home.route) {
            HomeScreen(
                modifier = modifier,
                onBeginTour = actions.navigateToCategories,
            )
        }
        composable(route = CityTourDestination.Categories.route) {
            CategoriesScreen(
                modifier = modifier,
                onCategorySelected = { selectedCategory ->
                    actions.navigateToLocationList(selectedCategory)
                },
            )
        }
        composable(
            route = CityTourDestination.LocationList.route,
            arguments = listOf(
                navArgument(CityTourDestination.LocationList.ARG_CATEGORY) {
                    type = NavType.StringType
                },
            ),
        ) { entry ->
            val category = entry.arguments
                ?.getString(CityTourDestination.LocationList.ARG_CATEGORY)
                ?.let(Uri::decode)
                .orEmpty()

            LocationListScreen(
                modifier = modifier,
                categoryName = category,
                onLocationSelected = { locationId ->
                    actions.navigateToLocationDetail(category, locationId)
                },
                onNavigateBack = actions.navigateBack,
                onNavigateHome = actions.navigateHome,
            )
        }
        composable(
            route = CityTourDestination.LocationDetail.route,
            arguments = listOf(
                navArgument(CityTourDestination.LocationDetail.ARG_CATEGORY) {
                    type = NavType.StringType
                },
                navArgument(CityTourDestination.LocationDetail.ARG_LOCATION_ID) {
                    type = NavType.IntType
                },
            ),
        ) { entry ->
            val category = entry.arguments
                ?.getString(CityTourDestination.LocationDetail.ARG_CATEGORY)
                ?.let(Uri::decode)
                .orEmpty()
            val locationId = entry.arguments
                ?.getInt(CityTourDestination.LocationDetail.ARG_LOCATION_ID)
                ?: -1

            LocationDetailScreen(
                modifier = modifier,
                categoryName = category,
                locationId = locationId,
                onNavigateBack = actions.navigateBack,
                onNavigateHome = actions.navigateHome,
            )
        }
    }
}

private class CityTourActions(
    val navigateToCategories: () -> Unit,
    val navigateToLocationList: (String) -> Unit,
    val navigateToLocationDetail: (String, Int) -> Unit,
    val navigateBack: () -> Unit,
    val navigateHome: () -> Unit,
)
