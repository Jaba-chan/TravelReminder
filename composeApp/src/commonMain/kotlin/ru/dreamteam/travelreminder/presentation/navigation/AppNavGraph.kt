package ru.dreamteam.travelreminder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ru.dreamteam.travelreminder.presentation.MainActivityViewModel

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AppNavGraph(
    viewModel: MainActivityViewModel = koinViewModel<MainActivityViewModel>(),
    navHostController: NavHostController,
    signInScreenContent: @Composable () -> Unit,
    signUpScreenContent: @Composable () -> Unit,
    travelsListScreenContent: @Composable () -> Unit,
    changePasswordScreenContent: @Composable () -> Unit,
    addTravelScreenContent: @Composable (editedTravel: String?) -> Unit,
    showMap: @Composable () -> Unit,
    placeSuggestionsScreen: @Composable (isOriginPlace: Boolean) -> Unit
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
        composable(
            route = Screen.AddTravelScreen.route,
            arguments = listOf(
                navArgument("editedTravelId") {
                    nullable = true
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            var travelId = backStackEntry.arguments?.getString("editedTravelId")
            if( travelId == "{editedTravelId}"){
                travelId = null
            }
            addTravelScreenContent(travelId)
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
        composable(
            route = Screen.PlaceSuggestionsScreen.route,
            arguments = listOf(navArgument("isOriginPlace") { type = NavType.BoolType })
        ) { backStackEntry ->
            val isOriginPlace = backStackEntry.arguments?.getBoolean("isOriginPlace") ?: true
            placeSuggestionsScreen(isOriginPlace)
        }
    }
}