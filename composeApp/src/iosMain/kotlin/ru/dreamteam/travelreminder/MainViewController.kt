package ru.dreamteam.travelreminder

import androidx.compose.ui.window.ComposeUIViewController
import ru.dreamteam.travelreminder.di.initKoin
import ru.dreamteam.travelreminder.presentation.App

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}