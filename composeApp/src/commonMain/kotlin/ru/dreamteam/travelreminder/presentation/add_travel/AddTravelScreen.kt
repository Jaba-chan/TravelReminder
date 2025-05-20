package ru.dreamteam.travelreminder.presentation.add_travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledPlaceholder
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledTextField
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.cancel
import travelreminder.composeapp.generated.resources.ic_date
import travelreminder.composeapp.generated.resources.ic_time
import travelreminder.composeapp.generated.resources.ok
import travelreminder.composeapp.generated.resources.travel_date
import travelreminder.composeapp.generated.resources.travel_destination
import travelreminder.composeapp.generated.resources.travel_name
import travelreminder.composeapp.generated.resources.travel_time

@Composable
fun AddTravelScreen(
    viewModel: AddTravelViewModel,
    onNavigateToTravelList: () -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {

        Spacer(modifier = Modifier.height(96.dp))
        LocalTextFieldStyle(
            value = viewModel.travelName.value,
            placeholderText = stringResource(Res.string.travel_name),
            onValueChange = {viewModel.onTravelNameTextChanged(it)},
            readOnly = false
        )
        Spacer(modifier = Modifier.height(32.dp))
        LocalTextFieldStyle(
            value = "",
            placeholderText = stringResource(Res.string.travel_date),
            onValueChange = {viewModel.onTravelNameTextChanged(it)},
            readOnly = true,
            onClick = { },
            trailingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_date),
                    contentDescription = null
                )
            }
        )
        Spacer(modifier = Modifier.height(32.dp))
        var selectedDate by remember { mutableStateOf<Long?>(null) }
        var showModal by remember { mutableStateOf(false) }
        LocalTextFieldStyle(
            value = "",
            placeholderText = stringResource(Res.string.travel_time),
            onValueChange = {viewModel.onTravelNameTextChanged(it)},
            readOnly = true,
            onClick = {showModal = true
                      println("DDDDDDD")
            },
            trailingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_time),
                    contentDescription = null
                )
            }
        )
        if (showModal) {
            DatePickerModalInput(
                onDateSelected = { selectedDate = it },
                onDismiss = { showModal = false }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        LocalTextFieldStyle(
            value = "",
            placeholderText = stringResource(Res.string.travel_destination),
            onValueChange = {viewModel.onTravelNameTextChanged(it)},
            readOnly = true,
            onClick = {},
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Place,
                    contentDescription = null
                )
            }
        )
    }
}

@Composable
fun LocalTextFieldStyle(
    value: String,
    placeholderText: String,
    readOnly: Boolean = false,
    onValueChange: (String) -> Unit,
    onClick: (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
){
    StyledTextField(
        value = value,
        readOnly = readOnly,
        textColor = MaterialTheme.colorScheme.onPrimary,
        backgroundColor = MaterialTheme.colorScheme.primary,
        trailingIcon = trailingIcon,
        placeholder             = {
            StyledPlaceholder(
                text = placeholderText,
                textColor = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Start
            )
        },
        textAlign = TextAlign.Start,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.onPrimary),
        onValueChange = onValueChange,
        onClick = onClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(
                onClick = onDismiss,
                content = {
                    Text(
                        text = stringResource(Res.string.ok)
                    )
                }
            )
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                content = {
                    Text(
                        text = stringResource(Res.string.cancel)
                    )
                }
            )
        }
    ) {
        DatePicker(state = datePickerState)
    }
}
