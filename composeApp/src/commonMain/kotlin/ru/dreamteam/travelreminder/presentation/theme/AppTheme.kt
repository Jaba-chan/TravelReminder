package ru.dreamteam.travelreminder.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    background = LightGray,
    primary = Yellow,
    onPrimary = Black,
    secondary = LightBlack,
    onSecondary = White,
    tertiary = DarkGray,
    onTertiary = White,
    error = Error,
    onError = White,
)

private val DarkColors = darkColorScheme(
    background = LightGray,
    primary = Yellow,
    onPrimary = Black,
    secondary = LightBlack,
    onSecondary = White,
    tertiary = DarkGray,
    onTertiary = White,
    error = Error,
    onError = White
)

@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (useDarkTheme) DarkColors else LightColors,
        typography = AppTypography,
        content = content
    )
}