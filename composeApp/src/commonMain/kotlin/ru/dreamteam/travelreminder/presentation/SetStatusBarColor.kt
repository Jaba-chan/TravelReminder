package ru.dreamteam.travelreminder.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
expect fun SetStatusBarColor(color: Color, darkIcons: Boolean = false)