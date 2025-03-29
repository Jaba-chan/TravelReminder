package ru.dreamteam.travelreminder.presentation.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun AuthScreen(viewModel: AuthViewModel) {
    val state by viewModel.state.collectAsState()
    Column {
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