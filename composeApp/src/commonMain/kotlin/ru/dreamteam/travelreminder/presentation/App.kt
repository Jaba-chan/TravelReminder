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

            val navController = rememberNavController()
            val navState = remember { NavigationState(navController) }

            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            Scaffold(
                floatingActionButton = {
                    if (currentRoute == Screen.TravelsListScreen.route) {
                        FloatingActionButton(
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            onClick = { navState.navigateTo(Screen.ShowMap.route) },
                            content = {
                                Icon(
                                    painter = painterResource(Res.drawable.ic_add),
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
                            onNavigateToTravelsList = { navState.navigateTo(Screen.TravelsListScreen.route) },
                            onNavigateToSignUpScreen = { navState.navigateTo(Screen.SignUpScreen.route) })
                    },
                    signUpScreenContent = { SignUpScreen(
                        viewModel = signUpViewModel,
                        onNavigateToSignIn = { navState.navigateTo(Screen.SignInScreen.route) }
                    )
                                          },
                    travelsListScreenContent = { TravelsListScreen(travelsViewModel) },
                    addTravelScreenContent = { Text(text = "fsaf") },
                    changePasswordScreenContent = { ChangePasswordScreen(changePasswordViewModel) },
                    showMap = {
                        GoogleMapView(
                            modifier = Modifier,
                            viewModel = mapViewModel,
                            changeAddress = { navState.navigateTo(Screen.PlaceSuggestionsScreen.route) }
                        )
                    },
                    placeSuggestionsScreen = {
                        PlaceSuggestionsScreen(
                            viewModel = mapViewModel,
                            returnToMap = { navState.navigateTo(Screen.ShowMap.route) },
                        )
                    }
                )
            }
        }
    }
}