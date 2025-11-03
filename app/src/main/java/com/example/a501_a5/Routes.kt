package com.example.a501_a5

sealed class Routes(val route: String) {
    data object Home : Routes("home")
    data object Detail : Routes("detail")
    data object Add : Routes("add")
    data object Settings : Routes("settings")
}
