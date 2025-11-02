package com.example.a501_a5.tasks

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun TasksScreen(
    viewModel: TasksViewModel,
    modifier: Modifier = Modifier
) {
    val tasks = viewModel.tasks
    val newTaskTitle = viewModel.newTaskTitle

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = newTaskTitle,
            onValueChange = viewModel::updateNewTaskTitle,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("New task") },
            singleLine = true
        )
        Button(
            onClick = viewModel::addTask,
            enabled = newTaskTitle.isNotBlank()
        ) {
            Text("Add task")
        }
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(tasks, key = TaskItem::id) { task ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { viewModel.toggleTask(task.id) }
                        .padding(12.dp)
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Checkbox(
                            checked = task.isDone,
                            onCheckedChange = { viewModel.toggleTask(task.id) }
                        )
                        Text(
                            text = task.title,
                            style = MaterialTheme.typography.bodyLarge,
                            textDecoration = if (task.isDone) {
                                TextDecoration.LineThrough
                            } else {
                                null
                            }
                        )
                    }
                    Divider(modifier = Modifier.padding(top = 12.dp))
                }
            }
            if (tasks.isEmpty()) {
                item {
                    Text(
                        text = "No tasks yet. Add one above to get started.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
