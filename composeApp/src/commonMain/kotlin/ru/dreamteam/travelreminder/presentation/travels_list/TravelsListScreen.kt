package ru.dreamteam.travelreminder.presentation.travels_list

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.sp

@Composable
fun TravelsListScreen(viewModel: TravelsViewModel) {
    Column {
        Text(
            text = if (viewModel.travels.collectAsState().value.isNotEmpty()) viewModel.travels.collectAsState().value.get(
                0
            ).destinationByAddress ?: "ffff" else "fffff", fontSize = 30.sp
        )
        Button(onClick = {viewModel.onButtonPressed()},
            content = { Text("Click") })
    }

}