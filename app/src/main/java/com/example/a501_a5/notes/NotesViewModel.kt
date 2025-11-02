package com.example.a501_a5.notes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class NotesViewModel : ViewModel() {

    private val _notes = mutableStateListOf<String>()
    val notes: List<String> = _notes

    var currentEntry by mutableStateOf("")
        private set

    fun onNoteChanged(value: String) {
        currentEntry = value
    }

    fun addNote() {
        val sanitized = currentEntry.trim()
        if (sanitized.isEmpty()) return
        _notes.add(0, sanitized)
        currentEntry = ""
    }
}
