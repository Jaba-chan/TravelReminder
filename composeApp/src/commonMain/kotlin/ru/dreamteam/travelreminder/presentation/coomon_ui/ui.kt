package ru.dreamteam.travelreminder.presentation.coomon_ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
fun SomethingErrorScreen(
    onRetryButtonClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            modifier = Modifier
                .size(152.dp),
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            textAlign = TextAlign.Center,
            text = stringResource(Res.string.something_error),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error
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
) {
    Column(
        modifier = Modifier
            .padding(vertical = 16.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1F))
        Text(
            textAlign = TextAlign.Center,
            text = text

        )
        Spacer(modifier = Modifier.weight(1F))
    }
}

@Composable
fun StyledTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onClick: (() -> Unit)? = null,
    placeholder: @Composable () -> Unit,
    readOnly: Boolean = false,
    modifier: Modifier = Modifier.padding(horizontal = 28.dp),
    height: Dp = 40.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    textColor: Color = MaterialTheme.colorScheme.onSecondary,
    cursorBrush: Brush = SolidColor(MaterialTheme.colorScheme.primary),
    textAlign: TextAlign = TextAlign.Start,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    contentPadding: PaddingValues = PaddingValues(horizontal = 12.dp),
    maxLength: Int = 50,
    trailingIcon: (@Composable () -> Unit)? = null,
) {
    val selectionColors = TextSelectionColors(
        handleColor = textColor,
        backgroundColor = textColor.copy(alpha = 0.3f)
    )

    CompositionLocalProvider(
        LocalTextSelectionColors.provides(selectionColors)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(height)
                .background(
                    backgroundColor,
                    RoundedCornerShape(4.dp)
                )
                .padding(contentPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = { new ->
                        if (new.length <= maxLength) {
                            onValueChange(new)
                        } else {
                            onValueChange(new.take(maxLength))
                        }
                    },
                    singleLine = true,
                    readOnly = readOnly,
                    visualTransformation = visualTransformation,
                    cursorBrush = cursorBrush,
                    textStyle = MaterialTheme.typography.labelMedium.copy(
                        color = textColor,
                        textAlign = textAlign
                    ),
                    modifier = Modifier
                        .fillMaxSize(),
                    decorationBox = { inner ->
                        val noRippleInteractionSource = remember { MutableInteractionSource() }
                        Box(
                            Modifier
                                .fillMaxSize()
                                .run {
                                    if (onClick != null)
                                        clickable(
                                            interactionSource = noRippleInteractionSource,
                                            indication = null
                                        ) {
                                            onClick()
                                        }
                                    else this
                                },
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (value.isEmpty()) placeholder()
                            inner()
                        }
                    }
                )
            }
            trailingIcon?.let {
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .wrapContentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    it()
                }
            }
        }
    }
}

@Composable
fun StyledButton(
    onButtonClicked: () -> Unit,
    content: @Composable RowScope.() -> Unit,
    paddingValues: PaddingValues,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary
    )
) {
    Button(
        modifier = Modifier
            .height(40.dp)
            .padding(paddingValues)
            .fillMaxWidth(),
        onClick = { onButtonClicked() },
        content = content,
        colors = colors,
        shape = RoundedCornerShape(14.dp)
    )
}


@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    strokeWidth: Dp = 2.dp
) {
    CircularProgressIndicator(
        modifier = Modifier
            .size(size),
        color = color,
        strokeWidth = strokeWidth
    )
}

@Composable
fun InnerButtonsText(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.headlineMedium
    )
}

@Composable
fun HeadingText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.displayLarge
    )
}

@Composable
fun HeadingTextWithIcon(
    text: String,
    iconRes: DrawableResource,
    iconSize: Dp,
    onIconClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
    ) {
        CircleIconButton(
            iconRes = iconRes,
            iconSize = iconSize,
            tint = MaterialTheme.colorScheme.onPrimary,
            onClick = onIconClicked
        )
        HeadingText(
            modifier = Modifier
                .align(Alignment.Center),
            text = text
        )
    }
}

@Composable
fun CircleIconButton(
    onClick: () -> Unit,
    iconSize: Dp,
    iconRes: DrawableResource,
    tint: Color
){
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .clip(CircleShape)
            .size(iconSize)

    ) {
        Icon(
            painter = painterResource(iconRes),
            tint = tint,
            contentDescription = null,
        )
    }
}

@Composable
fun FullScreenLoading(
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressBar(
            size = 48.dp,
            strokeWidth = 4.dp
        )
    }
}

@Composable
fun ColumnScope.ErrorText(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .align(Alignment.Start)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.labelSmall
        )
    }

}

@Composable
fun StyledPlaceholder(
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    textColor: Color
) {
    Text(
        modifier = Modifier
            .fillMaxWidth(),
        text = text,
        textAlign = textAlign,
        style = MaterialTheme.typography.labelMedium,
        color = textColor
    )
}