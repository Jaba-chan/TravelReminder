package ru.dreamteam.travelreminder.presentation.navigation

import androidx.navigation.NavController

class NavigationState(
    private val navController: NavController
) {
    fun navigateTo(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true
        }
    }

    fun navigateToTravelListForSignedUser() {
        navController.navigate(Screen.TravelsListScreen.route) {
            popUpTo(Screen.SignInScreen.route) {
                inclusive = true
            }
        }
    }

    fun navigateToSignIn() {
        navController.navigate(Screen.SignInScreen.route) {
            popUpTo(Screen.TravelsListScreen.route) {
                inclusive = true
            }
        }
    }
}