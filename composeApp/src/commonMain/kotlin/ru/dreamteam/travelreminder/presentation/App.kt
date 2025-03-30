package ru.dreamteam.travelreminder.presentation

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
import ru.dreamteam.travelreminder.presentation.navigation.Screen
import ru.dreamteam.travelreminder.presentation.sing_in.SignInScreen
import ru.dreamteam.travelreminder.presentation.sing_up.SignUpScreen
import ru.dreamteam.travelreminder.presentation.sing_up.SignUpViewModel
import ru.dreamteam.travelreminder.presentation.travels_list.TravelsListScreen
import ru.dreamteam.travelreminder.presentation.travels_list.TravelsViewModel
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.ic_add

@OptIn(KoinExperimentalAPI::class)
@Preview
@Composable
fun App() {
    KoinContext {
        val singInViewModel = koinViewModel<SingInViewModel>()
        val travelsViewModel = koinViewModel<TravelsViewModel>()
        val signUpViewModel = koinViewModel<SignUpViewModel>()
        val changePasswordViewModel = koinViewModel<ChangePasswordViewModel>()
        val navController = rememberNavController()
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { navController.navigate(Screen.TravelsListScreen.route) },
                    content = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_add),
                            contentDescription = null
                        )
                    }
                )
            }
        ) {
            AppNavGraph(
                navController,
                signInScreenContent = {
                    SignInScreen(singInViewModel,
                        onNavigateToSignUp = {navController.navigate(Screen.SignUpScreen.route)},
                        onNavigateToChangePassword = {navController.navigate(Screen.ChangePasswordScreen.route)}
                    )
                },
                travelsListScreenContent = { TravelsListScreen(travelsViewModel) },
                addTravelScreenContent = { Text(text = "fsaf") },
                signUpScreenContent = { SignUpScreen(signUpViewModel) },
                changePasswordScreenContent = { ChangePasswordScreen(changePasswordViewModel) }
            )
        }
    }
}