package ru.dreamteam.travelreminder

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.koin.android.ext.koin.androidContext
import org.koin.compose.KoinApplication
import ru.dreamteam.travelreminder.di.platformModule
import ru.dreamteam.travelreminder.di.sharedModule
import ru.dreamteam.travelreminder.presentation.App
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledButton
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledPlaceholder
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledTextField

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}


@Preview
@Composable
fun AndroidPreview() {
    val ctx = LocalContext.current
    KoinApplication(
        application = {
            modules(sharedModule, platformModule)
            androidContext(ctx)
        },

    ) {
        StyledTextField(
            value = "value",
            readOnly = false,
            textColor = MaterialTheme.colorScheme.onPrimary,
            backgroundColor = MaterialTheme.colorScheme.primary,
            trailingIcon = null,
            placeholder             = {
                StyledPlaceholder(
                    text = "fsfsf",
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Start
                )
            },
            textAlign = TextAlign.Start,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimary),
            onValueChange = {  },
            onClick = {  }
        )
    }
}
