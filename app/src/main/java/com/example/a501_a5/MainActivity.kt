package com.example.a501_a5

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.a501_a5.calendar.CalendarScreen
import com.example.a501_a5.calendar.CalendarViewModel
import com.example.a501_a5.navigation.BottomNavDestination
import com.example.a501_a5.notes.NotesScreen
import com.example.a501_a5.notes.NotesViewModel
import com.example.a501_a5.tasks.TasksScreen
import com.example.a501_a5.tasks.TasksViewModel
import com.example.a501_a5.ui.theme._501_A5Theme
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _501_A5Theme {
                PlannerApp()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalAnimationApi::class)
private fun PlannerApp() {
    val navController = rememberAnimatedNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = BottomNavDestination.fromRoute(backStackEntry?.destination?.route)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigation(
                backgroundColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ) {
                BottomNavDestination.allDestinations.forEach { destination ->
                    val isSelected = currentDestination?.baseRoute == destination.baseRoute
                    BottomNavigationItem(
                        selected = isSelected,
                        onClick = {
                            if (!isSelected) {
                                navController.navigateTo(
                                    destination = destination,
                                    from = currentDestination ?: BottomNavDestination.Notes
                                )
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = destination.icon,
                                contentDescription = destination.label
                            )
                        },
                        label = { Text(destination.label) },
                        alwaysShowLabel = true,
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        unselectedContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    ) { innerPadding ->
        AnimatedNavHost(
            navController = navController,
            startDestination = BottomNavDestination.Notes.graphRoute,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            enterTransition = { defaultEnterTransition(isPop = false) },
            exitTransition = { defaultExitTransition(isPop = false) },
            popEnterTransition = { defaultEnterTransition(isPop = true) },
            popExitTransition = { defaultExitTransition(isPop = true) }
        ) {
            composable(
                route = BottomNavDestination.Notes.graphRoute,
                arguments = listOf(
                    navArgument(BottomNavDestination.ARG_FROM_INDEX) {
                        type = NavType.IntType
                        defaultValue = BottomNavDestination.Notes.index
                    }
                )
            ) { entry ->
                val notesViewModel: NotesViewModel = viewModel(viewModelStoreOwner = entry)
                NotesScreen(
                    viewModel = notesViewModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                )
            }
            composable(
                route = BottomNavDestination.Tasks.graphRoute,
                arguments = listOf(
                    navArgument(BottomNavDestination.ARG_FROM_INDEX) {
                        type = NavType.IntType
                        defaultValue = BottomNavDestination.Tasks.index
                    }
                )
            ) { entry ->
                val tasksViewModel: TasksViewModel = viewModel(viewModelStoreOwner = entry)
                TasksScreen(
                    viewModel = tasksViewModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                )
            }
            composable(
                route = BottomNavDestination.Calendar.graphRoute,
                arguments = listOf(
                    navArgument(BottomNavDestination.ARG_FROM_INDEX) {
                        type = NavType.IntType
                        defaultValue = BottomNavDestination.Calendar.index
                    }
                )
            ) { entry ->
                val calendarViewModel: CalendarViewModel = viewModel(viewModelStoreOwner = entry)
                CalendarScreen(
                    viewModel = calendarViewModel,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                )
            }
        }
    }
}

private fun NavHostController.navigateTo(
    destination: BottomNavDestination,
    from: BottomNavDestination
) {
    val targetRoute = destination.routeWithArguments(from.index)
    navigate(targetRoute) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.defaultEnterTransition(
    isPop: Boolean
): EnterTransition {
    val direction = navSlideDirection(isPop = isPop)
    return slideIntoContainer(direction, animationSpec = tween(220)) + fadeIn(
        animationSpec = tween(220)
    )
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.defaultExitTransition(
    isPop: Boolean
): ExitTransition {
    val direction = navSlideDirection(isPop = isPop)
    return slideOutOfContainer(direction, animationSpec = tween(220)) + fadeOut(
        animationSpec = tween(200)
    )
}

private fun AnimatedContentTransitionScope<NavBackStackEntry>.navSlideDirection(
    isPop: Boolean
): AnimatedContentTransitionScope.SlideDirection {
    val fromIndex = initialState.arguments?.getInt(BottomNavDestination.ARG_FROM_INDEX)
        ?: BottomNavDestination.Notes.index
    val toIndex = targetState.arguments?.getInt(BottomNavDestination.ARG_FROM_INDEX)
        ?: BottomNavDestination.Notes.index
    if (fromIndex == toIndex) return AnimatedContentTransitionScope.SlideDirection.Up
    val forward = toIndex > fromIndex
    return if (isPop) {
        if (forward) AnimatedContentTransitionScope.SlideDirection.Right
        else AnimatedContentTransitionScope.SlideDirection.Left
    } else {
        if (forward) AnimatedContentTransitionScope.SlideDirection.Left
        else AnimatedContentTransitionScope.SlideDirection.Right
    }
}
