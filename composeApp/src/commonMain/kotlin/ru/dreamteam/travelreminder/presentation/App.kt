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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ru.dreamteam.travelreminder.presentation.auth.AuthViewModel
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.ic_add

@OptIn(KoinExperimentalAPI::class)
@Preview
@Composable
fun App() {
    KoinContext {
        val viewModel = koinViewModel<AuthViewModel>()
        val state by viewModel.state.collectAsState()

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {},
                    content = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_add),
                            contentDescription = null
                        )
                    }
                )
            }
        ) {
            Column {
                Text(
                    text = if (viewModel.travels.collectAsState().value.isNotEmpty()) viewModel.travels.collectAsState().value.get(
                        0
                    ).destinationByAddress ?: "ffff" else "fffff", fontSize = 30.sp
                )
                var emailText by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = emailText,
                    onValueChange = { emailText = it },
                    label = { Text("Email") },
                    placeholder = { Text("Введите email") }
                )
                var passwordText by remember { mutableStateOf("") }

                OutlinedTextField(
                    value = passwordText,
                    onValueChange = { passwordText = it },
                    label = { Text("Password") },
                    placeholder = { Text("Введите password") }
                )
                Button(
                    onClick = { viewModel.onSignInButtonPressed(emailText, passwordText) },
                    content = { Text("SingIn") }
                )
                when(state){
                    is AuthViewModel.State.Loading -> Text(text = "Loading...")
                    is AuthViewModel.State.Error -> Text(text = (state as AuthViewModel.State.Error).error)
                    is AuthViewModel.State.Success -> Text(text = (state as AuthViewModel.State.Success).data)
                }
            }

        }
    }
}