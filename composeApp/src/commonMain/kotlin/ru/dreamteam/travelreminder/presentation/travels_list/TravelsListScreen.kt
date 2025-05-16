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
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.stringResource
import ru.dreamteam.travelreminder.domen.model.travel.Travel
import ru.dreamteam.travelreminder.presentation.coomon_ui.CircularProgressBar
import ru.dreamteam.travelreminder.presentation.coomon_ui.SomethingErrorScreen
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.date_patter
import travelreminder.composeapp.generated.resources.empty_now
import travelreminder.composeapp.generated.resources.my_travels
import travelreminder.composeapp.generated.resources.time_pattern


@Composable
fun TravelsListScreen(
    viewModel: TravelsViewModel,
) {
    val state = viewModel.state.collectAsState().value
    LaunchedEffect(Unit) {
        viewModel.loadTravels()
    }

    Column {
        when (state) {
            TravelsViewModel.TravelsState.Empty -> Text(text = stringResource(Res.string.empty_now))
            is TravelsViewModel.TravelsState.Error -> SomethingErrorScreen(onRetryButtonClicked = {})
            TravelsViewModel.TravelsState.Loading -> CircularProgressBar()
            is TravelsViewModel.TravelsState.Success -> TravelsColumn(
                travels = state.data,
                onDeleteTravel = { viewModel.onTravelDeleted(it) })
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TravelsColumn(
    travels: List<Travel>,
    onDeleteTravel: (Travel) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE0E0E0))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = stringResource(Res.string.my_travels),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

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
                            .fillMaxSize()
                            .background(Color.Red)
                            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Icon(
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
                .background(MaterialTheme.colorScheme.tertiary, shape = RoundedCornerShape(4.dp))
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
