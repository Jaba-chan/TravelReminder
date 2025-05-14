package ru.dreamteam.travelreminder.presentation.show_map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.dreamteam.travelreminder.domen.model.GoogleMapViewEntries

@Composable
expect fun GoogleMapView(modifier: Modifier, viewModel: MapViewModel)