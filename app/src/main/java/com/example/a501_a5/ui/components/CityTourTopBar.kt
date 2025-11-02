package com.example.a501_a5.ui.components

import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.a501_a5.navigation.CityTourDestination

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CityTourTopBar(
    currentRoute: String?,
    canNavigateBack: Boolean,
    isBackDisabled: Boolean,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = deriveTitle(currentRoute),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        navigationIcon = {
            if (canNavigateBack && !isBackDisabled) {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                    )
                }
            }
        },
        actions = {
            val canNavigateHome =
                currentRoute != CityTourDestination.Home.route
            IconButton(
                onClick = onNavigateHome,
                enabled = canNavigateHome,
            ) {
                Icon(
                    imageVector = Icons.Filled.Home,
                    contentDescription = "Go Home",
                )
            }
        },
    )
}

private fun deriveTitle(route: String?): String {
    if (route == null) return "City Tour"
    return when {
        route == CityTourDestination.Home.route -> "City Tour"
        route == CityTourDestination.Categories.route -> "Choose a Category"
        route.startsWith("list/") -> {
            val category = Uri.decode(route.removePrefix("list/"))
            "$category Highlights"
        }
        route.startsWith("detail/") -> {
            val segments = route.removePrefix("detail/").split("/")
            val category = segments.getOrNull(0)?.let(Uri::decode).orEmpty()
            val nameSuffix =
                if (category.isNotEmpty()) "$category Detail" else "Location Detail"
            nameSuffix
        }
        else -> "City Tour"
    }
}
