package ru.dreamteam.travelreminder.presentation.coomon_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.dreamteam.travelreminder.presentation.sing_in.SingInViewModel
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.bt_sign_in
import travelreminder.composeapp.generated.resources.empty_now
import travelreminder.composeapp.generated.resources.retry
import travelreminder.composeapp.generated.resources.something_error

@Composable
fun SomethingErrorScreen(
    onRetryButtonClicked: () -> Unit
){
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .size(152.dp),
            imageVector         = Icons.Default.Warning,
            contentDescription  = null,
            tint                = MaterialTheme.colorScheme.error)
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            textAlign   = TextAlign.Center,
            text        = stringResource(Res.string.something_error),
            style       = MaterialTheme.typography.headlineMedium,
            color       = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(20.dp))
        StyledButton(
            onButtonClicked = onRetryButtonClicked,
            paddingValues = PaddingValues(horizontal = 28.dp),
            content = {
                InnerButtonsText(
                    text = stringResource(Res.string.retry)
                )
            }
        )
        Spacer(modifier = Modifier.weight(1f))
    }

}

@Composable
fun EmptyScreen(
    text: String
){
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1F))
        Text(
            textAlign   = TextAlign.Center,
            text        = text

        )
        Spacer(modifier = Modifier.weight(1F))
    }
}

@Composable
fun StyledTextField(
    value: String,
    placeholder: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    tint: Color = MaterialTheme.colorScheme.secondary,
    textColor: Color = MaterialTheme.colorScheme.onSecondary,
    height: Dp = 40.dp,
    contentPadding: PaddingValues = PaddingValues(12.dp)
) {
    Box(
        Modifier.then(modifier)
            .fillMaxWidth()
            .height(height)
            .background(color = tint, shape = RoundedCornerShape(4.dp))

    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            visualTransformation = visualTransformation,
            textStyle = MaterialTheme.typography.bodyMedium.copy(color = textColor),
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .fillMaxWidth()
                .padding(contentPadding),
            decorationBox = { inner ->
                Box(
                    Modifier.fillMaxSize(),
                ) {
                    if (value.isEmpty()) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = placeholder,
                            style = MaterialTheme.typography.bodyMedium,
                            color = textColor.copy(alpha = 0.5f)
                        )
                    }
                    inner()
                }
            }
        )
    }
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