package ru.dreamteam.travelreminder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavGraph(
    navHostController: NavHostController,
    signInScreenContent: @Composable () -> Unit,
    signUpScreenContent: @Composable () -> Unit,
    travelsListScreenContent: @Composable () -> Unit,
    changePasswordScreenContent: @Composable () -> Unit,
    addTravelScreenContent: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.SignInScreen.route
    ) {
        composable(Screen.SignInScreen.route) {
            signInScreenContent()
        }
        composable(Screen.TravelsListScreen.route) {
            travelsListScreenContent()
        }
        composable(Screen.AddTravelScreen.route) {
            addTravelScreenContent()
        }
        composable(Screen.SignUpScreen.route) {
            signUpScreenContent()
        }
        composable(Screen.ChangePasswordScreen.route) {
            changePasswordScreenContent()
        }
    }
}