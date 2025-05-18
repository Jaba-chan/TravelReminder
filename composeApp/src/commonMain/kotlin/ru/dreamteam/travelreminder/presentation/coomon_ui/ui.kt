package ru.dreamteam.travelreminder.presentation.coomon_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.retry
import travelreminder.composeapp.generated.resources.something_error

@Composable
fun ColumnScope.SomethingErrorScreen(
    onRetryButtonClicked: () -> Unit
){
    Icon(
        imageVector         = Icons.Default.Warning,
        contentDescription  = null,
        tint                = MaterialTheme.colorScheme.error)
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text    = stringResource(Res.string.something_error),
        style   = MaterialTheme.typography.headlineMedium,
        color   = MaterialTheme.colorScheme.error
    )
    Button(
        onClick = onRetryButtonClicked,
        content = {
            Text(
                text = stringResource(Res.string.retry)
            )
        },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}


@Composable
fun StyledTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    visualTransformation: VisualTransformation
) {
    TextField(
        value = value,
        onValueChange   = { onValueChange(it) },
        placeholder     = {
            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text        = placeholder,
                textAlign   = TextAlign.Center,
                color       = MaterialTheme.colorScheme.onSecondary,
                style       = MaterialTheme.typography.labelMedium
            )
        },
        visualTransformation    = visualTransformation,
        maxLines                = 1,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor   = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedContainerColor   = MaterialTheme.colorScheme.secondary,
            unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
            focusedTextColor        = MaterialTheme.colorScheme.onSecondary,
            unfocusedTextColor      = MaterialTheme.colorScheme.onSecondary
        ),
        modifier = Modifier
            .height(56.dp)
            .padding(horizontal = 28.dp)
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .wrapContentHeight()
    )
}

@Composable
fun StyledButton(
    onButtonClicked: () -> Unit,
    content: @Composable RowScope.() -> Unit,
    paddingValues: PaddingValues
){
    Button(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(14.dp))
            .height(40.dp),
        onClick = { onButtonClicked() },
        content = content,
        colors  = ButtonDefaults.buttonColors(
            containerColor  = MaterialTheme.colorScheme.primary,
            contentColor    = MaterialTheme.colorScheme.onPrimary
        ),
    )
}

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    size: Dp,
    strokeWidth: Dp = 2.dp
){
    CircularProgressIndicator(
        modifier = Modifier
            .size(size),
        color       = MaterialTheme.colorScheme.onPrimary,
        strokeWidth = strokeWidth
    )
}

@Composable
fun InnerButtonsText(text: String){
    Text(
        text    = text,
        style   = MaterialTheme.typography.headlineMedium
    )
}

@Composable
fun ScreenHeadingText(
    modifier: Modifier = Modifier,
    text: String
){
    Text(
        modifier = modifier,
        text    = text,
        style   = MaterialTheme.typography.displayLarge
    )
}

@Composable
fun HeadingTextWithIcon(
    text: String,
    iconRes: DrawableResource,
    iconSize: Dp,
    onIconClicked: () -> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        Icon(
            modifier = Modifier
                .size(iconSize)
                .clickable { onIconClicked() },
            painter             = painterResource(iconRes),
            contentDescription  = null
        )
        ScreenHeadingText(
            modifier = Modifier
                .align(Alignment.Center),
            text = text
        )
    }
}

@Composable
fun FullScreenLoading(
){
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressBar(
            size        = 48.dp,
            strokeWidth = 4.dp
        )
    }
}

@Composable
fun ColumnScope.ErrorText(
    modifier: Modifier = Modifier,
    text: String
){
    Box(modifier = modifier
                    .align(Alignment.Start)
    ){
        Text(
            text    = text,
            color   = MaterialTheme.colorScheme.error,
            style   = MaterialTheme.typography.labelSmall
        )
    }

}