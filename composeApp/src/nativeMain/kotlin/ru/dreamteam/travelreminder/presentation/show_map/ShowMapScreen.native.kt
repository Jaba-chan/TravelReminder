package ru.dreamteam.travelreminder.presentation.show_map

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.dreamteam.travelreminder.presentation.MainActivityViewModel
import ru.dreamteam.travelreminder.presentation.add_travel.AddTravelViewModel

@Composable
actual fun GoogleMapView(
    modifier: Modifier,
    viewModel: AddTravelViewModel,
    paddingValues: PaddingValues,
    changeAddress: (isOriginPlace: Boolean) -> Unit,
    returnToAddTravel: () -> Unit
) {
}