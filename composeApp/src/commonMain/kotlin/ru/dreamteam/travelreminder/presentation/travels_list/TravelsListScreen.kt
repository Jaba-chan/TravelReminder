package ru.dreamteam.travelreminder.presentation.travels_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.presentation.add_travel.AddTravelViewModel
import ru.dreamteam.travelreminder.presentation.add_travel.beforeRemindFormat
import ru.dreamteam.travelreminder.presentation.add_travel.format
import ru.dreamteam.travelreminder.presentation.coomon_ui.EmptyScreen
import ru.dreamteam.travelreminder.presentation.coomon_ui.FullScreenLoading
import ru.dreamteam.travelreminder.presentation.coomon_ui.HeadingTextWithIcon
import ru.dreamteam.travelreminder.presentation.coomon_ui.SomethingErrorScreen
import ru.dreamteam.travelreminder.presentation.show_map.durationToDHM
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.arrival_time_pattern
import travelreminder.composeapp.generated.resources.date_patter
import travelreminder.composeapp.generated.resources.empty_now
import travelreminder.composeapp.generated.resources.end_place_pattern
import travelreminder.composeapp.generated.resources.ic_logout
import travelreminder.composeapp.generated.resources.my_travels
import travelreminder.composeapp.generated.resources.remind_time_pattern
import travelreminder.composeapp.generated.resources.start_place_pattern
import travelreminder.composeapp.generated.resources.transportation_mode_bicycle
import travelreminder.composeapp.generated.resources.transportation_mode_drive
import travelreminder.composeapp.generated.resources.transportation_mode_pattern
import travelreminder.composeapp.generated.resources.transportation_mode_transit
import travelreminder.composeapp.generated.resources.transportation_mode_two_wheeler
import travelreminder.composeapp.generated.resources.transportation_mode_walk
import travelreminder.composeapp.generated.resources.travel_time_pattern


@OptIn(KoinExperimentalAPI::class)
@Composable
fun TravelsListScreen(
    viewModel: TravelsViewModel = koinViewModel<TravelsViewModel>(),
    paddingValues: PaddingValues,
    onNavigateToEditScreen: (String) -> Unit,
    logOut: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.loadTravels()
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .padding(paddingValues)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        HeadingTextWithIcon(
            iconRes = Res.drawable.ic_logout,
            iconSize = 28.dp,
            text = stringResource(Res.string.my_travels),
            onIconClicked = {
                viewModel.logOut()
                logOut()
            },
        )
        when (state) {
            TravelsViewModel.TravelsState.Empty -> EmptyScreen(text = stringResource(Res.string.empty_now))
            is TravelsViewModel.TravelsState.Error -> SomethingErrorScreen(onRetryButtonClicked = {})
            TravelsViewModel.TravelsState.Loading -> FullScreenLoading()
            is TravelsViewModel.TravelsState.Success ->
                TravelsColumn(
                    travels = state.data,
                    onNavigateToEditScreen = onNavigateToEditScreen,
                    onDeleteTravel = { viewModel.onTravelDeleted(it) }
                )
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TravelsColumn(
    travels: List<Travel>,
    onNavigateToEditScreen: (String) -> Unit,
    onDeleteTravel: (Travel) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(travels, key = { it.id }) { travel ->
            val dismissState = rememberDismissState(
                confirmStateChange = {
                    if (it == DismissValue.DismissedToStart) {
                        onDeleteTravel(travel)
                    }
                    true
                }
            )
            SwipeToDismiss(
                state = dismissState,
                directions = setOf(DismissDirection.EndToStart),
                background = {
                    Box(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxSize()
                            .background(
                                color = MaterialTheme.colorScheme.error,
                                shape = RoundedCornerShape(4.dp)
                            ),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
                            modifier = Modifier.padding(end = 16.dp),
                            imageVector = Icons.Default.Delete,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onError
                        )
                    }
                },
                dismissContent = {
                    TravelCard(
                        onNavigateToEditScreen = onNavigateToEditScreen,
                        travel = travel
                    )
                }
            )
        }

    }
}

@Composable
fun TravelCard(
    onNavigateToEditScreen: (String) -> Unit,
    travel: Travel
) {
    Column(
        modifier = Modifier
            .clickable { onNavigateToEditScreen(travel.id) }
    ) {
        LocalText(
            text = travel.title,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Medium
            ),
            color = MaterialTheme.colorScheme.onPrimary
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.tertiary,
                    shape = RoundedCornerShape(4.dp)
                )
                .padding(20.dp)

        ) {
            LocalText(text = stringResource(Res.string.date_patter) + travel.date.format())
            DefaultVerticalPadding()
            LocalText(text = stringResource(Res.string.arrival_time_pattern) + travel.arrivalTime.format())
            DefaultVerticalPadding()
            LocalText(text = stringResource(Res.string.start_place_pattern) + travel.startPlace.title)
            DefaultVerticalPadding()
            LocalText(text = stringResource(Res.string.end_place_pattern) + travel.destinationPlace.title)
            DefaultVerticalPadding()
            LocalText(
                text = stringResource(Res.string.travel_time_pattern)
                        + listTimeToString(durationToDHM(travel.route.duration))
            )
            DefaultVerticalPadding()
            LocalText(
                text = stringResource(Res.string.transportation_mode_pattern) +
                        transportationModeToTitle(travel.transportationMode)
            )
            DefaultVerticalPadding()
            LocalText(text = travel.arrivalTime.beforeRemindFormat())
        }
    }

}

@Composable
private fun LocalText(
    text: String,
    style: TextStyle = MaterialTheme.typography.headlineMedium,
    color: Color = MaterialTheme.colorScheme.onTertiary
) {
    Text(
        text = text,
        style = style,
        color = color
    )
}

@Composable
private fun DefaultVerticalPadding(
) {
    Spacer(modifier = Modifier.height(12.dp))
}

@Composable
fun transportationModeToTitle(mode: TransportationMode): String {
    return when (mode) {
        TransportationMode.DRIVE -> stringResource(Res.string.transportation_mode_drive)
        TransportationMode.BICYCLE -> stringResource(Res.string.transportation_mode_bicycle)
        TransportationMode.WALK -> stringResource(Res.string.transportation_mode_walk)
        TransportationMode.TWO_WHEELER -> stringResource(Res.string.transportation_mode_two_wheeler)
        TransportationMode.TRANSIT -> stringResource(Res.string.transportation_mode_transit)
    }
}

private fun listTimeToString(list: List<String>): String {
    return buildString {
        list.forEach {
            append(it)
            append(" ")
        }
    }
}