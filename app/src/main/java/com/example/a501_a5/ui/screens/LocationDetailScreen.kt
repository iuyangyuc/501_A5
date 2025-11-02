package com.example.a501_a5.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.a501_a5.data.Location
import com.example.a501_a5.data.findLocation

@Composable
fun LocationDetailScreen(
    modifier: Modifier = Modifier,
    categoryName: String,
    locationId: Int,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit,
) {
    val location = remember(categoryName, locationId) {
        findLocation(categoryName, locationId)
    }

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            if (location == null) {
                MissingLocation(
                    onNavigateBack = onNavigateBack,
                    onNavigateHome = onNavigateHome,
                )
            } else {
                LocationDetails(
                    location = location,
                    onNavigateBack = onNavigateBack,
                    onNavigateHome = onNavigateHome,
                )
            }
        }
    }
}

@Composable
private fun MissingLocation(
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = "We couldn't load this stop.",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "Try returning to the list or heading home to restart the tour.",
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onNavigateBack) {
            Text(text = "Back to list")
        }
        Button(onClick = onNavigateHome) {
            Text(text = "Return Home")
        }
    }
}

@Composable
private fun LocationDetails(
    location: Location,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = location.name,
            style = MaterialTheme.typography.headlineMedium,
        )
        Text(
            text = location.shortDescription,
            style = MaterialTheme.typography.bodyLarge,
        )
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Address",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
            Text(
                text = location.address,
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = "Highlights",
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
            location.highlights.forEach { highlight ->
                Text(
                    text = "• $highlight",
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = onNavigateBack) {
            Text(text = "Back to list")
        }
        Button(onClick = onNavigateHome) {
            Text(text = "Return Home")
        }
    }
}
