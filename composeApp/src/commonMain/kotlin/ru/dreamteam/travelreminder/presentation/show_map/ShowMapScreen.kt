package ru.dreamteam.travelreminder.presentation.show_map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.dreamteam.travelreminder.presentation.MainActivityViewModel

@Composable
expect fun GoogleMapView(
    modifier: Modifier,
    viewModel: MapViewModel,
    changeAddress: (isOriginPlace: Boolean) -> Unit,
    returnToAddTravel: () -> Unit
)