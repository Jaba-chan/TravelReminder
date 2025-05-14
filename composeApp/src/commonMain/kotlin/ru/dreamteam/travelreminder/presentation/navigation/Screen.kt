package ru.dreamteam.travelreminder.presentation.navigation

sealed class Screen(val route: String) {
    object SignInScreen: Screen(ROUTE_SIGN_IN)
    object SignUpScreen: Screen(ROUTE_SIGN_UP)
    object TravelsListScreen: Screen(ROUTE_TRAVELS_LIST)
    object AddTravelScreen: Screen(ROUTE_ADD_TRAVEL)
    object ChangePasswordScreen: Screen(ROUTE_CHANGE_PASSWORD)
    object ShowMap: Screen(ROUTE_SHOW_MAP)

    companion object {
        private const val ROUTE_SIGN_IN = "sign_in"
        private const val ROUTE_TRAVELS_LIST = "travels_list"
        private const val ROUTE_ADD_TRAVEL = "add_travel"
        private const val ROUTE_SIGN_UP = "sign_up"
        private const val ROUTE_CHANGE_PASSWORD = "change_password"
        private const val ROUTE_SHOW_MAP = "show_map"
    }
}