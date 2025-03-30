package ru.dreamteam.travelreminder.presentation.sing_up

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
fun SignUpScreen(viewModel: SignUpViewModel) {
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

        var repeatPasswordText by remember { mutableStateOf("") }
        OutlinedTextField(
            value = repeatPasswordText,
            onValueChange = { repeatPasswordText = it },
            label = { Text("Password") },
            placeholder = { Text("Введите password ещё раз") }
        )

        Button(
            onClick = {
                if (passwordText == repeatPasswordText) {
                    viewModel.onSignInButtonPressed(emailText, passwordText)
                }
            },
            content = { Text("SingUp") }
        )
        when (state) {
            is SignUpViewModel.State.Error -> Text(text = (state as SignUpViewModel.State.Error).error)
            SignUpViewModel.State.Loading -> Text(text = "Loading...")
            is SignUpViewModel.State.Success -> Text(text = (state as SignUpViewModel.State.Success).data)
        }
    }
}