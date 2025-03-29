package ru.dreamteam.travelreminder.presentation.navigation

sealed class Screen(val route: String) {
    object AuthScreen: Screen(ROUTE_AUTH)
    object TravelsListScreen: Screen(ROUTE_TRAVELS_LIST)
    object AddTravelScreen: Screen(ROUTE_ADD_TRAVEL)

    companion object {
        const val ROUTE_AUTH = "auth"
        const val ROUTE_TRAVELS_LIST = "travels_list"
        const val ROUTE_ADD_TRAVEL = "add_travel"
    }
}