package ru.dreamteam.travelreminder.presentation.navigation

import androidx.navigation.NavController

class NavigationState(
    private val navController: NavController
) {
    fun navigateTo(route: String){
        navController.navigate(route){
            launchSingleTop = true
            restoreState = true
        }
    }
}