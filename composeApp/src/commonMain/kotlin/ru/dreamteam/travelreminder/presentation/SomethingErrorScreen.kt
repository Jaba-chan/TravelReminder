package ru.dreamteam.travelreminder.presentation

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.retry
import travelreminder.composeapp.generated.resources.something_error

@Composable
fun ColumnScope.SomethingErrorScreen(onRetryButtonClicked: () -> Unit){
    Spacer(modifier = Modifier.weight(1F))
    Icon(imageVector = Icons.Default.Warning,
        contentDescription= null,
        tint = MaterialTheme.colorScheme.error)
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = stringResource(Res.string.something_error),
        style = MaterialTheme.typography.headlineMedium,
        color = MaterialTheme.colorScheme.error
    )
    Spacer(modifier = Modifier.weight(1F))
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
    Spacer(modifier = Modifier.height(16.dp))
}