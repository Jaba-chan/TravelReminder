package ru.dreamteam.travelreminder.presentation.show_map

import ru.dreamteam.travelreminder.presentation.add_travel.AddTravelViewModel
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun GoogleMapView(
    modifier: Modifier,
    viewModel: AddTravelViewModel,
    paddingValues: PaddingValues,
    changeAddress: (isOriginPlace: Boolean) -> Unit,
    returnToAddTravel: () -> Unit
)