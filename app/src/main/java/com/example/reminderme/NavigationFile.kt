package com.example.reminderme

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reminderme.Model.Screen
import com.example.reminderme.Veiw.MainScreen.MainScreen
import com.example.reminderme.Veiw.ReminderAddScreen.AddReminderScreen
import com.example.reminderme.Veiw.SplashScreen.SplashScreen
import com.example.reminderme.VeiwModel.ReminderViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onGetStartedClick = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Main.route) {
            val viewModel: ReminderViewModel = viewModel()
            MainScreen(
                viewModel = viewModel,
                onNavigateToAddReminder = {
                    navController.navigate(Screen.AddReminder.route)
                }
            )
        }

        composable(Screen.AddReminder.route) {
            val viewModel: ReminderViewModel = viewModel()
            AddReminderScreen(
                viewModel = viewModel,
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
