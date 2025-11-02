package com.example.a501_a5.calendar

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

class CalendarViewModel : ViewModel() {

    private val _yearMonth = mutableStateOf(YearMonth.now())
    val yearMonth: State<YearMonth> = _yearMonth

    private val _selectedDay = mutableIntStateOf(_yearMonth.value.atDay(1).dayOfMonth)
    val selectedDay: State<Int> = _selectedDay

    val monthLabel: String
        get() = _yearMonth.value.month.getDisplayName(TextStyle.FULL, Locale.getDefault())

    val yearLabel: String
        get() = _yearMonth.value.year.toString()

    val daysInMonth: Int
        get() = _yearMonth.value.lengthOfMonth()

    fun goToPreviousMonth() {
        _yearMonth.value = _yearMonth.value.minusMonths(1)
        clampSelectedDay()
    }

    fun goToNextMonth() {
        _yearMonth.value = _yearMonth.value.plusMonths(1)
        clampSelectedDay()
    }

    fun selectDay(day: Int) {
        _selectedDay.intValue = day.coerceIn(1, daysInMonth)
    }

    private fun clampSelectedDay() {
        _selectedDay.intValue = _selectedDay.intValue.coerceAtMost(daysInMonth)
    }
}
