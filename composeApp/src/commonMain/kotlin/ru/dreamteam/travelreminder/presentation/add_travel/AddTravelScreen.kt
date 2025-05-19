package ru.dreamteam.travelreminder.presentation.add_travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledTextField
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.travel_name

@Composable
fun AddTravelScreen(
    viewModel: AddTravelViewModel,
    onNavigateToTravelList: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {
        Spacer(modifier = Modifier.height(96.dp))
        StyledTextField(
            value = "",
            placeholder = stringResource(Res.string.travel_name),
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(32.dp))
        StyledTextField(
            value = "",
            placeholder = stringResource(Res.string.travel_name),
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(32.dp))
        StyledTextField(
            value = "",
            placeholder = stringResource(Res.string.travel_name),
            onValueChange = {},
        )
        Spacer(modifier = Modifier.height(32.dp))
        StyledTextField(
            value = "",
            placeholder = stringResource(Res.string.travel_name),
            onValueChange = {},
        )
    }
}