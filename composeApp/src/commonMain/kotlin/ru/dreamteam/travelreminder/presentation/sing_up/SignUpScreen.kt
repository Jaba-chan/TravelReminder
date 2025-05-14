package ru.dreamteam.travelreminder.presentation.sing_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.bt_sign_in
import travelreminder.composeapp.generated.resources.bt_sign_up
import travelreminder.composeapp.generated.resources.enter_email
import travelreminder.composeapp.generated.resources.enter_password
import travelreminder.composeapp.generated.resources.enter_password_again
import travelreminder.composeapp.generated.resources.sign_up

@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel,
    onNavigateToSignIn: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = stringResource(Res.string.sign_up),
            style = MaterialTheme.typography.displayLarge
        )
        Spacer(modifier = Modifier.weight(9F))
        TextFieldBuilder(
            value = viewModel.email.value,
            onValueChange = { viewModel.onEmailTextChanged(it) },
            placeholder = stringResource(Res.string.enter_email),
            visualTransformation = VisualTransformation.None
        )
        Spacer(modifier = Modifier.weight(6F))
        TextFieldBuilder(
            value = viewModel.password.value,
            onValueChange = { viewModel.onPasswordTextChanged(it) },
            placeholder = stringResource(Res.string.enter_password),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.weight(6F))
        TextFieldBuilder(
            value = viewModel.passwordAgain.value,
            onValueChange = { viewModel.onPasswordAgainTextChanged(it) },
            placeholder = stringResource(Res.string.enter_password_again),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.weight(6F))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(32.dp)
                .padding(horizontal = 78.dp),
            onClick = { viewModel.onSignUpButtonPressed() },
            content = {
                when (state) {
                    is SignUpViewModel.SignUpState.Loading -> CircularProgressIndicator(modifier = Modifier.fillMaxSize())
                    is SignUpViewModel.SignUpState.Error -> Text(stringResource(Res.string.bt_sign_up))
                    is SignUpViewModel.SignUpState.Idle -> Text(stringResource(Res.string.bt_sign_up))
                    SignUpViewModel.SignUpState.Success -> onNavigateToSignIn()
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            ),
        )

        Spacer(modifier = Modifier.weight(6F))
    }
}

@Composable
fun TextFieldBuilder(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation
) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        placeholder = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = placeholder,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSecondary,
                style = MaterialTheme.typography.labelMedium
            )
        },
        visualTransformation = visualTransformation,
        maxLines = 1,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondary
        ),
        modifier = Modifier
            .padding(horizontal = 28.dp)
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .wrapContentHeight()
    )
}