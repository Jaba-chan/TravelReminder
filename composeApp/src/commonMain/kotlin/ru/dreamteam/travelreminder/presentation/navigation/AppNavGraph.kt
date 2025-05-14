package ru.dreamteam.travelreminder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.dreamteam.travelreminder.presentation.MainActivityViewModel
import ru.dreamteam.travelreminder.presentation.sing_up.SignUpViewModel

@Composable
fun AppNavGraph(
    viewModel: MainActivityViewModel,
    navHostController: NavHostController,
    signInScreenContent: @Composable () -> Unit,
    signUpScreenContent: @Composable () -> Unit,
    travelsListScreenContent: @Composable () -> Unit,
    changePasswordScreenContent: @Composable () -> Unit,
    addTravelScreenContent: @Composable () -> Unit,
    showMap: @Composable () -> Unit
) {
    NavHost(
        navController = navHostController,
        startDestination = if (viewModel.isFirstLaunch.value) Screen.SignInScreen.route else Screen.TravelsListScreen.route
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
        composable(Screen.ShowMap.route) {
            showMap()
        }
    }
}