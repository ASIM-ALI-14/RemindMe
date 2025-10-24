package com.example.reminderme.Model

sealed class Screen (val route: String) {
    data object Splash : Screen("splash_screen")
    data object Main : Screen("main_screen")
    data object AddReminder : Screen("add_reminder_screen")
}