package com.example.a501_a5

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.a501_a5.navigation.CityTourDestination
import com.example.a501_a5.navigation.CityTourNavGraph
import com.example.a501_a5.ui.components.CityTourTopBar
import com.example.a501_a5.ui.theme.CityTourTheme

@Composable
fun CityTourApp() {
    CityTourTheme {
        val navController = rememberNavController()
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route
        var isBackDisabled by rememberSaveable { mutableStateOf(false) }

        LaunchedEffect(currentRoute) {
            if (currentRoute != CityTourDestination.Home.route) {
                isBackDisabled = false
            }
        }

        val shouldBlockBack =
            currentRoute == CityTourDestination.Home.route && isBackDisabled
        BackHandler(enabled = shouldBlockBack) {
            // Consume the back press to keep the user on Home after the tour.
        }

        Scaffold(
            topBar = {
                CityTourTopBar(
                    currentRoute = currentRoute,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    isBackDisabled = shouldBlockBack,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateHome = {
                        navController.navigate(CityTourDestination.Home.route) {
                            popUpTo(CityTourDestination.Home.route) {
                                inclusive = true
                            }
                        }
                        isBackDisabled = true
                    },
                )
            },
        ) { innerPadding ->
            CityTourNavGraph(
                navController = navController,
                onStackClearedAtHome = { isBackDisabled = true },
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
            )
        }
    }
}
