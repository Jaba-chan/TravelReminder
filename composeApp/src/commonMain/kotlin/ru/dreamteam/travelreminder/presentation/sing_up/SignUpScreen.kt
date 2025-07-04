package ru.dreamteam.travelreminder.presentation.sing_up

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ru.dreamteam.travelreminder.presentation.CaughtErrorImpl
import ru.dreamteam.travelreminder.presentation.add_travel.AddTravelViewModel
import ru.dreamteam.travelreminder.presentation.coomon_ui.CircularProgressBar
import ru.dreamteam.travelreminder.presentation.coomon_ui.ErrorText
import ru.dreamteam.travelreminder.presentation.coomon_ui.HeadingTextWithIcon
import ru.dreamteam.travelreminder.presentation.coomon_ui.InnerButtonsText
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledButton
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledPlaceholder
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledTextField
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.bt_sign_up
import travelreminder.composeapp.generated.resources.enter_email
import travelreminder.composeapp.generated.resources.enter_password
import travelreminder.composeapp.generated.resources.enter_password_again
import travelreminder.composeapp.generated.resources.ic_arrow_back
import travelreminder.composeapp.generated.resources.sign_up

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = koinViewModel<SignUpViewModel>(),
    paddingValues: PaddingValues,
    onNavigateToSignIn: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        HeadingTextWithIcon(
            iconRes = Res.drawable.ic_arrow_back,
            iconSize = 24.dp,
            text = stringResource(Res.string.sign_up),
            onIconClicked = onNavigateToSignIn
        )
        Spacer(modifier = Modifier.height(96.dp))
        StyledTextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.onEmailTextChanged(it) },
            placeholder = {
                StyledPlaceholder(
                    text = stringResource(Res.string.enter_email),
                    textColor = MaterialTheme.colorScheme.onSecondary
                )
            },
            visualTransformation = VisualTransformation.None
        )
        Spacer(modifier = Modifier.height(80.dp))
        StyledTextField(
            value = viewModel.password.value,
            onValueChange = { viewModel.onPasswordTextChanged(it) },
            placeholder = {
                StyledPlaceholder(
                    text = stringResource(Res.string.enter_password),
                    textColor = MaterialTheme.colorScheme.onSecondary
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(12.dp))
        StyledTextField(
            value = viewModel.passwordAgain.value,
            onValueChange = { viewModel.onPasswordAgainTextChanged(it) },
            placeholder = {
                StyledPlaceholder(
                    text = stringResource(Res.string.enter_password_again),
                    textColor = MaterialTheme.colorScheme.onSecondary
                )
            },
            visualTransformation = PasswordVisualTransformation()
        )
        ErrorText(
            modifier = Modifier
                .padding(
                    horizontal = 28.dp,
                    vertical = 4.dp
                ),
            text = ((state as? SignUpViewModel.SignUpState.Error)?.error as? CaughtErrorImpl.CommonError)
                ?.let {
                    stringResource(it.resId)
                } ?: ""
        )
        Spacer(modifier = Modifier.height(80.dp))
        StyledButton(
            onButtonClicked = viewModel::onSignUpButtonPressed,
            paddingValues = PaddingValues(horizontal = 28.dp),
            content = {
                when (state) {
                    is SignUpViewModel.SignUpState.Loading -> CircularProgressBar(size = 24.dp)
                    is SignUpViewModel.SignUpState.Error -> InnerButtonsText(stringResource(Res.string.bt_sign_up))
                    is SignUpViewModel.SignUpState.Idle -> InnerButtonsText(stringResource(Res.string.bt_sign_up))
                    SignUpViewModel.SignUpState.Success -> {
                        viewModel.resetStateToIdle()
                        onNavigateToSignIn()
                    }
                }
            }
        )
        Spacer(modifier = Modifier.weight(6F))
    }
}



