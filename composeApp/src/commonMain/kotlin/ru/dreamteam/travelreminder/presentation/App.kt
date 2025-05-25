package ru.dreamteam.travelreminder.presentation

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ru.dreamteam.travelreminder.presentation.add_travel.AddTravelScreen
import ru.dreamteam.travelreminder.presentation.add_travel.AddTravelViewModel
import ru.dreamteam.travelreminder.presentation.change_password.ChangePasswordScreen
import ru.dreamteam.travelreminder.presentation.change_password.ChangePasswordViewModel
import ru.dreamteam.travelreminder.presentation.sing_in.SingInViewModel
import ru.dreamteam.travelreminder.presentation.navigation.AppNavGraph
import ru.dreamteam.travelreminder.presentation.navigation.NavigationState
import ru.dreamteam.travelreminder.presentation.navigation.Screen
import ru.dreamteam.travelreminder.presentation.show_map.GoogleMapView
import ru.dreamteam.travelreminder.presentation.show_map.MapViewModel
import ru.dreamteam.travelreminder.presentation.show_map.PlaceSuggestionsScreen
import ru.dreamteam.travelreminder.presentation.sing_in.SignInScreen
import ru.dreamteam.travelreminder.presentation.sing_up.SignUpScreen
import ru.dreamteam.travelreminder.presentation.sing_up.SignUpViewModel
import ru.dreamteam.travelreminder.presentation.theme.AppTheme
import ru.dreamteam.travelreminder.presentation.travels_list.TravelsListScreen
import ru.dreamteam.travelreminder.presentation.travels_list.TravelsViewModel
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.ic_add
import travelreminder.composeapp.generated.resources.ic_check

@OptIn(KoinExperimentalAPI::class)
@Preview
@Composable
fun App() {
    AppTheme {
        KoinContext {
            val mainActivityViewModel = koinViewModel<MainActivityViewModel>()
            val singInViewModel = koinViewModel<SingInViewModel>()
            val travelsViewModel = koinViewModel<TravelsViewModel>()
            val signUpViewModel = koinViewModel<SignUpViewModel>()
            val changePasswordViewModel = koinViewModel<ChangePasswordViewModel>()
            val mapViewModel = koinViewModel<MapViewModel>()
            val addTravelViewModel = koinViewModel<AddTravelViewModel>()

            val navController = rememberNavController()
            val navState = remember { NavigationState(navController) }

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            SetStatusBarColor(
                color = MaterialTheme.colorScheme.background,
                darkIcons = true
            )
            Scaffold(
                floatingActionButton = {
                    when (currentRoute) {
                        Screen.TravelsListScreen.route, Screen.ShowMap.route ->
                            FloatingActionButton(
                                backgroundColor = MaterialTheme.colorScheme.primary,
                                onClick = {
                                    if (currentRoute == Screen.TravelsListScreen.route)
                                        navState.navigateTo(Screen.AddTravelScreen.route)
                                    else navState.navigateTo(Screen.ShowMap.route)
                                },
                                content = {
                                    Icon(
                                        painter = if (currentRoute == Screen.TravelsListScreen.route)
                                            painterResource(Res.drawable.ic_add) else painterResource(
                                            Res.drawable.ic_check
                                        ),
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            )
                    }
                }
            ) {
                AppNavGraph(
                    viewModel = mainActivityViewModel,
                    navHostController = navController,
                    signInScreenContent = {
                        SignInScreen(
                            viewModel = singInViewModel,
                            onNavigateToTravelsList = { navState.navigateToTravelListForSignedUser() },
                            onNavigateToSignUpScreen = { navState.navigateTo(Screen.SignUpScreen.route) })
                    },
                    signUpScreenContent = {
                        SignUpScreen(
                            viewModel = signUpViewModel,
                            onNavigateToSignIn = { navState.navigateToSignIn() }
                        )
                    },
                    travelsListScreenContent = {
                        TravelsListScreen(
                            viewModel = travelsViewModel,
                            logOut = { navState.navigateToSignIn() }
                        )
                    },
                    addTravelScreenContent = {
                        AddTravelScreen(
                            viewModel = addTravelViewModel,
                            onNavigateToTravelList = { navState.navigateTo(Screen.TravelsListScreen.route) },
                            onNavigateToMap = { navState.navigateTo(Screen.ShowMap.route) }
                        )
                    },
                    changePasswordScreenContent = {
                        ChangePasswordScreen(
                            viewModel = changePasswordViewModel
                        )
                    },
                    showMap = {
                        GoogleMapView(
                            modifier = Modifier,
                            viewModel = mapViewModel,
                            changeAddress = {
                                navState.navigateTo(
                                    Screen.PlaceSuggestionsScreen.createRoute(
                                        it
                                    )
                                )
                            },
                            returnToAddTravel = { navState.navigateTo(Screen.AddTravelScreen.route) }
                        )
                    },
                    placeSuggestionsScreen = { isOriginPlace ->
                        PlaceSuggestionsScreen(
                            viewModel = mapViewModel,
                            returnToMap = { navState.navigateTo(Screen.ShowMap.route) },
                            isOriginPlace = isOriginPlace
                        )
                    }
                )
            }
        }
    }
}
