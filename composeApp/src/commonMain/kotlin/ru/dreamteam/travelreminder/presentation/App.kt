package ru.dreamteam.travelreminder.presentation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
@Composable
fun App() {
    AppTheme {
        KoinContext {
            val addTravelViewModel = koinViewModel<AddTravelViewModel>()

            val navController = rememberNavController()
            val navState = remember { NavigationState(navController) }

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val snackBarHostState = remember { SnackbarHostState() }

            LaunchedEffect(Unit) {
                addTravelViewModel.snackBarMessages.collect { message ->
                    snackBarHostState.showSnackbar(message)
                }
            }

            SetStatusBarColor(
                color = MaterialTheme.colorScheme.background,
                darkIcons = true
            )
            Scaffold(
                modifier = Modifier
                    .fillMaxSize(),
                floatingActionButton = {
                    when (currentRoute) {
                        Screen.TravelsListScreen.route, Screen.ShowMap.route ->
                            FloatingActionButton(
                                backgroundColor = MaterialTheme.colorScheme.primary,
                                onClick = {
                                    if (currentRoute == Screen.TravelsListScreen.route) addTravelViewModel.resetFields()
                                    navState.navigateTo(Screen.AddTravelScreen.route)
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
                },
                snackbarHost = {
                    SnackbarHost(hostState = snackBarHostState)
                }
            ) {
                val innerPadding = WindowInsets.systemBars.asPaddingValues()
                AppNavGraph(
                    navHostController = navController,
                    signInScreenContent = {
                        SignInScreen(
                            paddingValues = innerPadding,
                            onNavigateToTravelsList = {
                                navState.navigateToTravelListForSignedUser()
                            },
                            onNavigateToSignUpScreen = {
                                navState.navigateTo(Screen.SignUpScreen.route)
                            }
                        )
                    },
                    signUpScreenContent = {
                        SignUpScreen(
                            paddingValues = innerPadding,
                            onNavigateToSignIn = { navState.navigateToSignIn() }
                        )
                    },
                    travelsListScreenContent = {
                        TravelsListScreen(
                            paddingValues = innerPadding,
                            onNavigateToEditScreen = {
                                navState.navigateTo(Screen.AddTravelScreen.createRoute(it))
                            },
                            logOut = {
                                navState.navigateToSignIn()
                            }
                        )
                    },
                    addTravelScreenContent = { travelId ->
                        AddTravelScreen(
                            viewModel = addTravelViewModel,
                            paddingValues = innerPadding,
                            onNavigateToTravelList = {
                                navState.navigateTo(Screen.TravelsListScreen.route)
                            },
                            onNavigateToMap = { navState.navigateTo(Screen.ShowMap.route) },
                            editedTravelId = travelId
                        )
                    },
                    changePasswordScreenContent = {
                        ChangePasswordScreen(
                            paddingValues = innerPadding,
                        )
                    },
                    showMap = {
                        GoogleMapView(
                            modifier = Modifier,
                            viewModel = addTravelViewModel,
                            paddingValues = innerPadding,
                            changeAddress = {
                                navState.navigateTo(Screen.PlaceSuggestionsScreen.createRoute(it))
                            },
                            returnToAddTravel = { navState.navigateTo(Screen.AddTravelScreen.route) }
                        )
                    },
                    placeSuggestionsScreen = { isOriginPlace ->
                        PlaceSuggestionsScreen(
                            viewModel = addTravelViewModel,
                            paddingValues = innerPadding,
                            returnToMap = { navState.navigateTo(Screen.ShowMap.route) },
                            isOriginPlace = isOriginPlace
                        )
                    }
                )
            }
        }
    }
}
