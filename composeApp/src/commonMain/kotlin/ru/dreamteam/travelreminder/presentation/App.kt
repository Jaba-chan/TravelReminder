package ru.dreamteam.travelreminder.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ru.dreamteam.travelreminder.presentation.auth.AuthScreen
import ru.dreamteam.travelreminder.presentation.auth.AuthViewModel
import ru.dreamteam.travelreminder.presentation.navigation.AppNavGraph
import ru.dreamteam.travelreminder.presentation.navigation.Screen
import ru.dreamteam.travelreminder.presentation.travels_list.TravelsListScreen
import ru.dreamteam.travelreminder.presentation.travels_list.TravelsViewModel
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.ic_add

@OptIn(KoinExperimentalAPI::class)
@Preview
@Composable
fun App() {
    KoinContext {
        val authViewModel = koinViewModel<AuthViewModel>()
        val travelsViewModel = koinViewModel<TravelsViewModel>()
        val navController = rememberNavController()
        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {navController.navigate(Screen.TravelsListScreen.route)},
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
                { AuthScreen(authViewModel) },
                { TravelsListScreen(travelsViewModel) },
                { Text(text = "fsaf") }
            )
        }
    }
}