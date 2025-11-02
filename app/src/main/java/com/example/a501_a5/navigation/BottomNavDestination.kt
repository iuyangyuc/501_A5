package com.example.a501_a5.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavDestination(
    val baseRoute: String,
    val label: String,
    val icon: ImageVector,
    val index: Int
) {

    object Notes : BottomNavDestination("notes", "Notes", Icons.Default.Edit, 0)
    object Tasks : BottomNavDestination("tasks", "Tasks", Icons.Default.List, 1)
    object Calendar : BottomNavDestination("calendar", "Calendar", Icons.Default.CalendarToday, 2)

    val graphRoute: String = "$baseRoute?$ARG_FROM_INDEX={$ARG_FROM_INDEX}"

    fun routeWithArguments(fromIndex: Int): String =
        "$baseRoute?$ARG_FROM_INDEX=$fromIndex"

    companion object {
        const val ARG_FROM_INDEX = "fromIndex"

        val allDestinations: List<BottomNavDestination> = listOf(Notes, Tasks, Calendar)

        fun fromRoute(route: String?): BottomNavDestination? {
            val base = route?.substringBefore("?") ?: return null
            return allDestinations.firstOrNull { it.baseRoute == base }
        }
    }
}
