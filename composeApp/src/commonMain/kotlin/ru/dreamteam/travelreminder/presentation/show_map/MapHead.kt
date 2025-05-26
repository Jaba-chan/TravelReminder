package ru.dreamteam.travelreminder.presentation.show_map

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.presentation.add_travel.AddTravelViewModel
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.days_pattern
import travelreminder.composeapp.generated.resources.hours_pattern
import travelreminder.composeapp.generated.resources.ic_bicycle
import travelreminder.composeapp.generated.resources.ic_circle
import travelreminder.composeapp.generated.resources.ic_drive
import travelreminder.composeapp.generated.resources.ic_location
import travelreminder.composeapp.generated.resources.ic_more_vert
import travelreminder.composeapp.generated.resources.ic_swap
import travelreminder.composeapp.generated.resources.ic_transit
import travelreminder.composeapp.generated.resources.ic_two_wheeler
import travelreminder.composeapp.generated.resources.ic_walk
import travelreminder.composeapp.generated.resources.minutes_pattern

@Composable
fun MapHead(
    viewModel: AddTravelViewModel,
    changeAddress: (isOriginPlace: Boolean) -> Unit,
) {
    Spacer(modifier = Modifier.height(8.dp))
    Row(modifier = Modifier.fillMaxWidth()) {
        val columnHeight = 100.dp
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .height(columnHeight)
                .weight(1f)
        ) {
            viewModel.route.value?.let {
                val time = durationToDHM(it.duration)
                repeat(time.size) { pos ->
                    Text(
                        text = time[pos],
                        style = MaterialTheme.typography.labelLarge.copy(fontSize = 13.sp),
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
        Spacer(modifier = Modifier.width(8.dp))

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(columnHeight)
        ) {
            CircleIconButton(
                onClick = viewModel::setPointSelectorAsOrigin,
                iconSize = 16.dp,
                iconRes = Res.drawable.ic_circle,
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Icon(
                painter = painterResource(Res.drawable.ic_more_vert),
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.4f),
                contentDescription = null,
                modifier = Modifier
                    .height(20.dp)
            )
            Spacer(modifier = Modifier.height(4.dp))
            CircleIconButton(
                onClick = viewModel::setPointSelectorAsDestination,
                iconSize = 20.dp,
                iconRes = Res.drawable.ic_location,
                tint = MaterialTheme.colorScheme.error
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.height(columnHeight)
        ) {
            val textFieldsHeight = 40.dp
            CompactTextField(
                modifier = Modifier
                    .height(textFieldsHeight),
                value = viewModel.selectedPoints.value.first?.title ?: "",
                onClick = {
                    viewModel.setPlaceSuggestionsQuery(true)
                    changeAddress(true)
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            CompactTextField(
                modifier = Modifier
                    .height(textFieldsHeight),
                value = viewModel.selectedPoints.value.second?.title ?: "",
                onClick = {
                    viewModel.setPlaceSuggestionsQuery(false)
                    changeAddress(false)
                }
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.height(columnHeight)
        ) {
            CircleIconButton(
                iconRes = Res.drawable.ic_swap,
                tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                iconSize = 24.dp,
                onClick = viewModel::onReverseButtonPressed
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
    Spacer(modifier = Modifier.height(8.dp))

    val mode = viewModel.transportationMode.value
    TransportationModeSelector(
        selectedMode = mode,
        onModeSelected = { viewModel.onTransportationModeChanged(it) })
    Spacer(modifier = Modifier.height(16.dp))
    HorizontalDivider(thickness = 2.dp)
}

@Composable
fun CompactTextField(
    modifier: Modifier,
    value: String,
    onClick: () -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = { },
        readOnly = true,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodySmall,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        modifier = modifier
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(4.dp)
            )
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .width(200.dp),
        decorationBox = { innerTextField ->
            val noRippleInteractionSource = remember { MutableInteractionSource() }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(
                        interactionSource = noRippleInteractionSource,
                        indication = null
                    ) { onClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                innerTextField()
            }
        }
    )
}


@Composable
fun TransportationModeSelector(
    selectedMode: TransportationMode,
    onModeSelected: (TransportationMode) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Spacer(modifier = Modifier.weight(1f))
        Spacer(modifier = Modifier.width(16.dp))
        TransportationMode.entries.forEach { mode ->
            val isSelected = mode == selectedMode
            val noRippleInteractionSource = remember { MutableInteractionSource() }
            Box(
                modifier = Modifier
                    .clickable(indication = null, interactionSource = noRippleInteractionSource) {
                        onModeSelected(mode)
                    }
                    .size(48.dp, 28.dp)
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = when (mode) {
                        TransportationMode.DRIVE -> painterResource(Res.drawable.ic_drive)
                        TransportationMode.BICYCLE -> painterResource(Res.drawable.ic_bicycle)
                        TransportationMode.WALK -> painterResource(Res.drawable.ic_walk)
                        TransportationMode.TRANSIT -> painterResource(Res.drawable.ic_transit)
                        TransportationMode.TWO_WHEELER -> painterResource(Res.drawable.ic_two_wheeler)
                    },
                    modifier = Modifier.size(16.dp),
                    contentDescription = mode.name,
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun CircleIconButton(
    onClick: () -> Unit,
    iconSize: Dp,
    iconRes: DrawableResource,
    tint: Color
){
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .clip(CircleShape)
            .size(iconSize)

    ) {
        Icon(
            painter = painterResource(iconRes),
            tint = tint,
            contentDescription = null,
        )
    }
}

@Composable
fun durationToDHM(duration: String): List<String> {
    val seconds = duration.removeSuffix("s").toIntOrNull() ?: return listOf("0")
    val totalMinutes = (seconds + 30) / 60

    val days = totalMinutes / (24 * 60)
    val hours = (totalMinutes % (24 * 60)) / 60
    val minutes = totalMinutes % 60

    val list = mutableListOf<String>()
    return list.apply {
        if (days != 0) {
            list.add("$days${stringResource(Res.string.days_pattern)}")
        }
        if (hours != 0) {
            list.add("$hours${stringResource(Res.string.hours_pattern)}")
        }
        list.add("$minutes${stringResource(Res.string.minutes_pattern)}")
    }
}