package ru.dreamteam.travelreminder.presentation.add_travel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerColors
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.dreamteam.travelreminder.domen.model.travel.Date
import ru.dreamteam.travelreminder.domen.model.travel.Time
import ru.dreamteam.travelreminder.presentation.CaughtErrorImpl
import ru.dreamteam.travelreminder.presentation.coomon_ui.CircularProgressBar
import ru.dreamteam.travelreminder.presentation.coomon_ui.ErrorText
import ru.dreamteam.travelreminder.presentation.coomon_ui.HeadingText
import ru.dreamteam.travelreminder.presentation.coomon_ui.InnerButtonsText
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledButton
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledPlaceholder
import ru.dreamteam.travelreminder.presentation.coomon_ui.StyledTextField
import ru.dreamteam.travelreminder.presentation.sing_in.SingInViewModel
import ru.dreamteam.travelreminder.presentation.sing_up.SignUpViewModel
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.bt_sign_in
import travelreminder.composeapp.generated.resources.cancel
import travelreminder.composeapp.generated.resources.enter_time
import travelreminder.composeapp.generated.resources.ic_date
import travelreminder.composeapp.generated.resources.ic_time
import travelreminder.composeapp.generated.resources.ok
import travelreminder.composeapp.generated.resources.save
import travelreminder.composeapp.generated.resources.time_before_remind
import travelreminder.composeapp.generated.resources.travel_date
import travelreminder.composeapp.generated.resources.travel_destination
import travelreminder.composeapp.generated.resources.travel_name
import travelreminder.composeapp.generated.resources.travel_time

@Composable
fun AddTravelScreen(
    viewModel: AddTravelViewModel,
    onNavigateToTravelList: () -> Unit,
    onNavigateToMap: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        val state = viewModel.state.value

        Spacer(modifier = Modifier.height(96.dp))
        LocalTextFieldStyle(
            value = viewModel.travelName.value,
            placeholderText = stringResource(Res.string.travel_name),
            onValueChange = { viewModel.onTravelNameTextChanged(it) },
            readOnly = false
        )
        ErrorText(
            modifier = Modifier
                .padding(
                    horizontal = 28.dp,
                    vertical = 4.dp
                ),
            text = ((state as? AddTravelViewModel.AddTravelState.ValidationError)?.error as? CaughtErrorImpl.ValidationError)
                ?.let {
                    if (AddTravelFieldsValidationErrors.entries[it.ordinal] == AddTravelFieldsValidationErrors.EMPTY_NAME)
                        stringResource(it.resId)
                    else ""
                } ?: ""
        )
        DefaultVerticalPadding()
        LocalTextFieldStyle(
            value = viewModel.travelDate.value?.format() ?: "",
            placeholderText = stringResource(Res.string.travel_date),
            onValueChange = { },
            readOnly = true,
            onClick = { viewModel.showDatePicker() },
            trailingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_date),
                    contentDescription = null
                )
            }
        )
        if (viewModel.isDatePickerVisible.value) {
            DatePickerModalInput(
                onDateSelected = { viewModel.setTravelDate(it) },
                onDismiss = { viewModel.hideDatePicker() }
            )
        }
        DefaultVerticalPadding()
        LocalTextFieldStyle(
            value = viewModel.travelTime.value?.format() ?: "",
            placeholderText = stringResource(Res.string.travel_time),
            onValueChange = { },
            readOnly = true,
            onClick = { viewModel.showTimePicker() },
            trailingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_time),
                    contentDescription = null
                )
            }
        )
        if (viewModel.isTimePickerVisible.value) {
            TimePickerModalInput(
                onTimeSelected = { viewModel.setTravelTime(it) },
                onDismiss = { viewModel.hideTimePicker() }
            )
        }
        DefaultVerticalPadding()
        LocalTextFieldStyle(
            value = viewModel.selectedPoints.value.second?.title ?: "",
            placeholderText = stringResource(Res.string.travel_destination),
            onValueChange = { },
            readOnly = true,
            onClick = { onNavigateToMap() },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.Place,
                    contentDescription = null
                )
            }
        )
        DefaultVerticalPadding()
        LocalTextFieldStyle(
            value = viewModel.timeBeforeRemind.value?.format() ?: "",
            placeholderText = stringResource(Res.string.time_before_remind),
            onValueChange = { },
            readOnly = true,
            onClick = { viewModel.showTimePicker() },
            trailingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_time),
                    contentDescription = null
                )
            }
        )
        if (viewModel.isTimePickerVisible.value) {
            TimePickerModalInput(
                onTimeSelected = { viewModel.setTimeBeforeRemind(it) },
                onDismiss = { viewModel.hideTimePicker() }
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        StyledButton(
            onButtonClicked = { viewModel.addTravel() },
            paddingValues = PaddingValues(horizontal = 78.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            ),
            content = {
                when (state) {
                    is AddTravelViewModel.AddTravelState.Loading -> CircularProgressBar(size = 24.dp)
                    else -> InnerButtonsText(text = stringResource(Res.string.save))
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
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
) {
    StyledTextField(
        value = value,
        readOnly = readOnly,
        textColor = MaterialTheme.colorScheme.onPrimary,
        backgroundColor = MaterialTheme.colorScheme.primary,
        trailingIcon = trailingIcon,
        placeholder = {
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
    onDateSelected: (Date?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(
        initialDisplayMode = DisplayMode.Input,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                println(utcTimeMillis >= getTodayAtMidnightMillis())
                return utcTimeMillis >= getTodayAtMidnightMillis()
            }
        }
    )
    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            DialogButton(
                onClick = {
                    onDateSelected(formatMillisToDate(datePickerState.selectedDateMillis))
                    onDismiss()
                },
                text = stringResource(Res.string.ok)
            )
        },
        dismissButton = {
            DialogButton(
                onClick = onDismiss,
                text = stringResource(Res.string.cancel)
            )
        }
    ) {
        DatePicker(
            state = datePickerState
        )
    }
}

@Composable
fun DefaultVerticalPadding() {
    Spacer(modifier = Modifier.height(32.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerModalInput(
    onTimeSelected: (Time) -> Unit,
    onDismiss: () -> Unit
) {
    val timePickerState = rememberTimePickerState(is24Hour = true)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            DialogButton(
                onClick = {
                    onTimeSelected(
                        Time(
                            hours = timePickerState.hour,
                            minutes = timePickerState.minute
                        )
                    )
                    onDismiss()
                },
                text = stringResource(Res.string.ok)
            )
        },
        dismissButton = {
            DialogButton(
                onClick = onDismiss,
                text = stringResource(Res.string.cancel)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeadingText(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(horizontal = 16.dp),
                text = stringResource(Res.string.enter_time)
            )
            Spacer(modifier = Modifier.height(16.dp))
            TimeInput(
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    timeSelectorSelectedContainerColor = TimePickerDefaults.colors().timeSelectorUnselectedContainerColor,
                    timeSelectorSelectedContentColor = TimePickerDefaults.colors().timeSelectorUnselectedContentColor
                )
            )
            HorizontalDivider()
        }
    }
}

@Composable
fun DialogButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        content = {
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    )
}


fun Time.format(): String {
    val hoursStr = if (hours < 10) "0$hours" else "$hours"
    val minutesStr = if (minutes < 10) "0$minutes" else "$minutes"
    return "$hoursStr:$minutesStr"
}

fun Date.format(): String {
    val dayStr = if (day < 10) "0$day" else "$day"
    val monthStr = if (month < 10) "0$month" else "$month"
    return "$dayStr.$monthStr.$year"
}

fun formatMillisToDate(millis: Long?): Date? {
    if (millis == null) {
        return null
    }
    val instant = Instant.fromEpochMilliseconds(millis)
    val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val day = dateTime.dayOfMonth
    val month = dateTime.monthNumber
    val year = dateTime.year

    return Date(day = day, month = month, year = year)
}

fun getTodayAtMidnightMillis() = Clock.System.now()
    .toLocalDateTime(TimeZone.currentSystemDefault())
    .date
    .atStartOfDayIn(TimeZone.currentSystemDefault())
    .toEpochMilliseconds()