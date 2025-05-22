package ru.dreamteam.travelreminder.presentation.travels_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.presentation.coomon_ui.EmptyScreen
import ru.dreamteam.travelreminder.presentation.coomon_ui.FullScreenLoading
import ru.dreamteam.travelreminder.presentation.coomon_ui.HeadingTextWithIcon
import ru.dreamteam.travelreminder.presentation.coomon_ui.SomethingErrorScreen
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.date_patter
import travelreminder.composeapp.generated.resources.empty_now
import travelreminder.composeapp.generated.resources.ic_logout
import travelreminder.composeapp.generated.resources.my_travels
import travelreminder.composeapp.generated.resources.time_pattern


@Composable
fun TravelsListScreen(
    viewModel: TravelsViewModel,
    logOut: () -> Unit
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.loadTravels()
    }

    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        HeadingTextWithIcon(
            iconRes = Res.drawable.ic_logout,
            iconSize = 28.dp,
            text = stringResource(Res.string.my_travels),
            onIconClicked = logOut,
        )
        when (state) {
            TravelsViewModel.TravelsState.Empty -> EmptyScreen(text = stringResource(Res.string.empty_now))
            is TravelsViewModel.TravelsState.Error -> SomethingErrorScreen(onRetryButtonClicked = {})
            TravelsViewModel.TravelsState.Loading -> FullScreenLoading()
            is TravelsViewModel.TravelsState.Success ->
                TravelsColumn(
                    travels = state.data,
                    onDeleteTravel = { viewModel.onTravelDeleted(it) }
                )
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TravelsColumn(
    travels: List<Travel>,
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
                    TravelCard(travel)
                }
            )
        }

    }
}

@Composable
fun TravelCard(travel: Travel) {
    Column {
        Text(
            text = travel.title,
            style = MaterialTheme.typography.headlineMedium
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
            Text(
                text = "${stringResource(Res.string.date_patter)}${travel.date}",
                color = MaterialTheme.colorScheme.onTertiary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "${stringResource(Res.string.time_pattern)}${travel.arrivalTime}",
                color = MaterialTheme.colorScheme.onTertiary
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = travel.destinationByAddress ?: "",
                color = MaterialTheme.colorScheme.onTertiary
            )
        }
    }

}
