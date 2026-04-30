package com.aerotune.player.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.aerotune.player.ui.screens.MainScreen

sealed class Screen(val route: String) {
    data object Main : Screen("main")
}

@Composable
fun AeroTuneNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(Screen.Main.route) {
            MainScreen()
        }
    }
}
