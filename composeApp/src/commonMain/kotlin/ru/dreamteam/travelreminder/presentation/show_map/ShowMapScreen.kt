package ru.dreamteam.travelreminder.presentation.show_map

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun GoogleMapView(modifier: Modifier, viewModel: MapViewModel, changeAddress: () -> Unit)