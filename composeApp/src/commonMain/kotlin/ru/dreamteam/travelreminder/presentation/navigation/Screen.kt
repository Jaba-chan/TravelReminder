package ru.dreamteam.travelreminder.presentation.navigation

sealed class Screen(val route: String) {
    object SignInScreen: Screen(ROUTE_SIGN_IN)
    object SignUpScreen: Screen(ROUTE_SIGN_UP)
    object TravelsListScreen: Screen(ROUTE_TRAVELS_LIST)
    object AddTravelScreen: Screen(ROUTE_ADD_TRAVEL)
    object ChangePasswordScreen: Screen(ROUTE_CHANGE_PASSWORD)

    companion object {
        const val ROUTE_SIGN_IN = "sign_in"
        const val ROUTE_TRAVELS_LIST = "travels_list"
        const val ROUTE_ADD_TRAVEL = "add_travel"
        const val ROUTE_SIGN_UP = "sign_up"
        const val ROUTE_CHANGE_PASSWORD = "change_password"
    }
}