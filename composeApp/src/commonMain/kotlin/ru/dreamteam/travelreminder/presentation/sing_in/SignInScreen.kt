package ru.dreamteam.travelreminder.presentation.sing_in

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
fun SignInScreen(
    viewModel: SingInViewModel,
    onNavigateToSignUp: () -> Unit,
    onNavigateToChangePassword: () -> Unit,
) {
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
        when (state) {
            is SingInViewModel.State.Loading -> Text(text = "Loading...")
            is SingInViewModel.State.Error -> Text(text = (state as SingInViewModel.State.Error).error)
            is SingInViewModel.State.Success -> Text(text = (state as SingInViewModel.State.Success).data)
        }
        Button(
            onClick = { onNavigateToSignUp() },
            content = { Text(text = "SignUp") })
        Button(
            onClick = { onNavigateToChangePassword() },
            content = { Text(text = "Change password") })
    }
}