package com.example.reminderme

import ReminderViewModel
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reminderme.Model.Screen
import com.example.reminderme.View.ReminderAddScreen.AddReminderScreen
import com.example.reminderme.View.SplashScreen.SplashScreen
import com.example.reminderme.View.MainScreen.MainScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val viewModel: ReminderViewModel = viewModel()
    NavHost(
        navController =
            navController, startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Splash.route) {
                            inclusive = true
                        }
                    }
                })
        }
        composable(Screen.Main.route) {
            MainScreen(
                viewModel = viewModel,
                onNavigateToAddReminder = { navController.navigate(Screen.AddReminder.route) })
        }
        composable(Screen.AddReminder.route) { // use the same viewModel instance from AppNavigation
            AddReminderScreen(
                onNavigateBack = { navController.popBackStack() },
                viewModel = viewModel
            )
        }
    }
}