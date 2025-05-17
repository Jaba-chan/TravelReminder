package ru.dreamteam.travelreminder.presentation.sing_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ru.dreamteam.travelreminder.presentation.coomon_ui.CircularProgressBar
import ru.dreamteam.travelreminder.presentation.coomon_ui.InnerButtonsText
import ru.dreamteam.travelreminder.presentation.coomon_ui.ScreenHeadingText
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledButton
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledTextField
import travelreminder.composeapp.generated.resources.Res
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
        Spacer(modifier = Modifier.height(24.dp))
        ScreenHeadingText(text = stringResource(Res.string.sign_up))
        Spacer(modifier = Modifier.height(96.dp))
        StyledTextField(
            value                   = viewModel.email.value,
            onValueChange           = { viewModel.onEmailTextChanged(it) },
            placeholder             = stringResource(Res.string.enter_email),
            visualTransformation    = VisualTransformation.None
        )
        Spacer(modifier = Modifier.height(80.dp))
        StyledTextField(
            value                   = viewModel.password.value,
            onValueChange           = { viewModel.onPasswordTextChanged(it) },
            placeholder             = stringResource(Res.string.enter_password),
            visualTransformation    = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(12.dp))
        StyledTextField(
            value                   = viewModel.passwordAgain.value,
            onValueChange           = { viewModel.onPasswordAgainTextChanged(it) },
            placeholder             = stringResource(Res.string.enter_password_again),
            visualTransformation    = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(80.dp))
        StyledButton(
            onButtonClicked     = viewModel::onSignUpButtonPressed,
            paddingValues       = PaddingValues(horizontal = 28.dp),
            content = {
                when (state) {
                    is SignUpViewModel.SignUpState.Loading  -> CircularProgressBar(size = 24.dp)
                    is SignUpViewModel.SignUpState.Error    -> InnerButtonsText(stringResource(Res.string.bt_sign_up))
                    is SignUpViewModel.SignUpState.Idle     -> InnerButtonsText(stringResource(Res.string.bt_sign_up))
                    SignUpViewModel.SignUpState.Success     -> onNavigateToSignIn()
                }
            }
        )
        Spacer(modifier = Modifier.weight(6F))
    }
}



