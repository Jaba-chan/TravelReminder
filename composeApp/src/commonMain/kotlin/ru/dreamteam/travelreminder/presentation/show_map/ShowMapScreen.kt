package ru.dreamteam.travelreminder.presentation.show_map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.dreamteam.travelreminder.presentation.add_travel.AddTravelViewModel

@Composable
expect fun GoogleMapView(
    modifier: Modifier,
    viewModel: AddTravelViewModel,
    changeAddress: (isOriginPlace: Boolean) -> Unit,
    returnToAddTravel: () -> Unit
)