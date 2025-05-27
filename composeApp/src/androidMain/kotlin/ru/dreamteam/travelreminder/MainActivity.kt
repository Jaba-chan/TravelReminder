package ru.dreamteam.travelreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import ru.dreamteam.travelreminder.data.local.provider.LocaleProvider
import ru.dreamteam.travelreminder.presentation.App

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}
