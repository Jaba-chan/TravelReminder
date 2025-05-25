package ru.dreamteam.travelreminder.presentation.sing_in

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.dreamteam.travelreminder.presentation.CaughtErrorImpl
import ru.dreamteam.travelreminder.presentation.coomon_ui.CircularProgressBar
import ru.dreamteam.travelreminder.presentation.coomon_ui.ErrorText
import ru.dreamteam.travelreminder.presentation.coomon_ui.InnerButtonsText
import ru.dreamteam.travelreminder.presentation.coomon_ui.HeadingText
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledButton
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledPlaceholder
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledTextField
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.bt_sign_in
import travelreminder.composeapp.generated.resources.bt_sign_in_with
import travelreminder.composeapp.generated.resources.bt_sign_up
import travelreminder.composeapp.generated.resources.enter_email
import travelreminder.composeapp.generated.resources.enter_password
import travelreminder.composeapp.generated.resources.ic_t_bank
import travelreminder.composeapp.generated.resources.sign_in
import travelreminder.composeapp.generated.resources.t_bank

@Composable
fun SignInScreen(
    viewModel: SingInViewModel,
    onNavigateToTravelsList: () -> Unit,
    onNavigateToSignUpScreen: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        HeadingText(text = stringResource(Res.string.sign_in))
        Spacer(modifier = Modifier.height(96.dp))
        StyledTextField(
            value = viewModel.email.value,
            onValueChange = { viewModel.onEmailTextChanged(it) },
            placeholder = {
                StyledPlaceholder(
                    text = stringResource(Res.string.enter_email),
                    textColor = MaterialTheme.colorScheme.onSecondary,
                )
            },
            visualTransformation = VisualTransformation.None,
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
            visualTransformation = PasswordVisualTransformation(),
        )

        ErrorText(
            modifier = Modifier
                .padding(
                    horizontal = 28.dp,
                    vertical = 4.dp
                ),
            text = ((state as? SingInViewModel.SignInState.Error)?.error as? CaughtErrorImpl.ErrorForUser)
                ?.let {
                    stringResource(it.resId)
                } ?: ""
        )

        Spacer(modifier = Modifier.height(56.dp))
        StyledButton(
            onButtonClicked = viewModel::onSignInButtonPressed,
            paddingValues = PaddingValues(horizontal = 28.dp),
            content = {
                when (state) {
                    is SingInViewModel.SignInState.Loading -> CircularProgressBar(size = 24.dp)
                    SingInViewModel.SignInState.Success -> {
                        onNavigateToTravelsList()
                    }
                    else -> InnerButtonsText(text = stringResource(Res.string.bt_sign_in))
                }
            },
        )
        Spacer(modifier = Modifier.height(80.dp))
        StyledButton(
            onButtonClicked = { },
            paddingValues = PaddingValues(horizontal = 78.dp),
            content = {
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Normal)) {
                            append(stringResource(Res.string.bt_sign_in_with))
                        }
                        withStyle(style = SpanStyle(fontWeight = FontWeight.SemiBold)) {
                            append(stringResource(Res.string.t_bank))
                        }
                    },
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.width(12.dp))
                Icon(
                    painter = painterResource(Res.drawable.ic_t_bank),
                    contentDescription = null,
                    tint = Color.Unspecified
                )
            }
        )
        Spacer(modifier = Modifier.height(40.dp))
        Text(
            modifier = Modifier
                .clickable { onNavigateToSignUpScreen() },
            text = stringResource(Res.string.bt_sign_up),
            style = MaterialTheme.typography.headlineMedium,
            textDecoration = TextDecoration.Underline,
        )
    }
}
