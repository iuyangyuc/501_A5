package com.example.a501_a5.tasks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class TaskItem(
    val id: Int,
    val title: String,
    val isDone: Boolean
)

class TasksViewModel : ViewModel() {

    private val _tasks = mutableStateListOf(
        TaskItem(id = 0, title = "Skim today's notes", isDone = false),
        TaskItem(id = 1, title = "Review Compose navigation docs", isDone = false),
        TaskItem(id = 2, title = "Plan calendar placeholder", isDone = true)
    )
    val tasks: List<TaskItem> = _tasks

    var newTaskTitle by mutableStateOf("")
        private set

    fun updateNewTaskTitle(value: String) {
        newTaskTitle = value
    }

    fun toggleTask(id: Int) {
        val index = _tasks.indexOfFirst { it.id == id }
        if (index == -1) return
        val task = _tasks[index]
        _tasks[index] = task.copy(isDone = !task.isDone)
    }

    fun addTask() {
        val sanitized = newTaskTitle.trim()
        if (sanitized.isEmpty()) return
        val nextId = (_tasks.maxOfOrNull { it.id } ?: -1) + 1
        _tasks.add(TaskItem(id = nextId, title = sanitized, isDone = false))
        newTaskTitle = ""
    }
}
