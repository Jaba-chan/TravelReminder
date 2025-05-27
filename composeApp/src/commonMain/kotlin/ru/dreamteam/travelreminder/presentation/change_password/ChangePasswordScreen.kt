package ru.dreamteam.travelreminder.presentation.change_password

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun ChangePasswordScreen(
    viewModel: ChangePasswordViewModel,
    paddingValues: PaddingValues,
) {
    val state by viewModel.state.collectAsState()
    Column (
        modifier = Modifier
            .padding(paddingValues)
    ){
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
                viewModel.onSignInButtonPressed(
                    emailText,
                    passwordText,
                    repeatPasswordText
                )
            },
            content = { Text("SingUp") }
        )
        when (state) {
            is ChangePasswordViewModel.ChangePasswordState.Error -> Text(text = "Error")
            ChangePasswordViewModel.ChangePasswordState.Loading -> Text(text = "Loading...")
            is ChangePasswordViewModel.ChangePasswordState.Success -> Text(text = "Success")
        }
    }
}