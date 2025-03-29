package ru.dreamteam.travelreminder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    authScreenContent: @Composable () -> Unit,
    travelsListScreenContent: @Composable () -> Unit,
    addTravelScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.AuthScreen.route
    ) {
        composable(Screen.AuthScreen.route) {
            authScreenContent()
        }
        composable(Screen.TravelsListScreen.route) {
            travelsListScreenContent()
        }
        composable(Screen.AddTravelScreen.route) {
            addTravelScreenContent()
        }
    }
}