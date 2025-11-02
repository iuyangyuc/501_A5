package com.example.a501_a5.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.a501_a5.data.Location
import com.example.a501_a5.data.getLocationsForCategory

@Composable
fun LocationListScreen(
    modifier: Modifier = Modifier,
    categoryName: String,
    onLocationSelected: (Int) -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateHome: () -> Unit,
) {
    val locations = remember(categoryName) {
        getLocationsForCategory(categoryName)
    }

    Surface(
        modifier = modifier.fillMaxSize(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        text = "$categoryName Picks",
                        style = MaterialTheme.typography.titleLarge,
                    )
                    Text(
                        text = "Choose a stop to see more detail or jump back home.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            if (locations.isEmpty()) {
                item {
                    Text(
                        text = "No featured locations were found for this category.",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            } else {
                items(
                    items = locations,
                    key = Location::id,
                ) { location ->
                    LocationCard(
                        location = location,
                        onDetailsClick = { onLocationSelected(location.id) },
                    )
                }
            }
            item { HorizontalDivider() }
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    TextButton(onClick = onNavigateBack) {
                        Text(text = "Back to Categories")
                    }
                    Button(onClick = onNavigateHome) {
                        Text(text = "Return Home")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationCard(
    location: Location,
    onDetailsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onDetailsClick,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = location.name,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = location.shortDescription,
                style = MaterialTheme.typography.bodyMedium,
            )
            TextButton(onClick = onDetailsClick) {
                Text(text = "View details")
            }
        }
    }
}
