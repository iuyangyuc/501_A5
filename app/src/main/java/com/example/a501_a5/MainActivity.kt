package com.example.a501_a5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                RecipeApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeApp(recipeViewModel: RecipeViewModel = viewModel()) {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination
    val bottomRoutes = listOf(Routes.Home, Routes.Add)
    val shouldShowBottomBar = currentDestination?.route?.startsWith(Routes.Detail.route) != true

    Scaffold(
        topBar = {
            RecipeTopBar(
                currentDestination = currentDestination,
                onNavigateBack = { navController.popBackStack() }
            )
        },
        bottomBar = {
            if (shouldShowBottomBar) {
                RecipeBottomBar(
                    navController = navController,
                    currentDestination = currentDestination,
                    bottomRoutes = bottomRoutes
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Routes.Home.route) {
                HomeScreen(
                    recipes = recipeViewModel.recipes,
                    onRecipeSelected = { recipeId ->
                        navController.navigate("${Routes.Detail.route}/$recipeId") {
                            launchSingleTop = true
                        }
                    }
                )
            }
            composable(Routes.Add.route) {
                AddRecipeScreen(
                    onRecipeSaved = { title, ingredients, steps ->
                        val newId = recipeViewModel.addRecipe(title, ingredients, steps)
                        if (newId != null) {
                            navController.navigate("${Routes.Detail.route}/$newId") {
                                launchSingleTop = true
                                popUpTo(Routes.Home.route) { inclusive = false }
                            }
                        }
                    }
                )
            }
            composable(
                route = "${Routes.Detail.route}/{id}",
                arguments = listOf(
                    navArgument("id") {
                        nullable = false
                        type = NavType.StringType
                    }
                )
            ) { entry ->
                val recipeId = entry.arguments?.getString("id")
                val recipe = recipeId?.let(recipeViewModel::findRecipeById)
                if (recipe != null) {
                    DetailScreen(
                        recipe = recipe,
                        onNavigateBack = { navController.popBackStack() }
                    )
                } else {
                    MissingRecipeScreen(
                        onNavigateHome = {
                            navController.navigate(Routes.Home.route) {
                                launchSingleTop = true
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipeTopBar(
    currentDestination: NavDestination?,
    onNavigateBack: () -> Unit
) {
    val route = currentDestination?.route.orEmpty()
    val showBack = route.startsWith(Routes.Detail.route)
    val title = when {
        showBack -> "Recipe Details"
        route == Routes.Add.route -> "Add Recipe"
        else -> "Recipes"
    }

    CenterAlignedTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (showBack) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
private fun RecipeBottomBar(
    navController: NavHostController,
    currentDestination: NavDestination?,
    bottomRoutes: List<Routes>
) {
    NavigationBar {
        bottomRoutes.forEach { route ->
            val isSelected = currentDestination?.hierarchy?.any { it.route == route.route } == true
            val (icon, label) = when (route) {
                Routes.Home -> Icons.Default.Home to "Home"
                Routes.Add -> Icons.Default.Add to "Add"
                Routes.Detail -> Icons.Default.Home to "Detail"
            }
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(route.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
                icon = { Icon(imageVector = icon, contentDescription = label) },
                label = { Text(text = label) }
            )
        }
    }
}
