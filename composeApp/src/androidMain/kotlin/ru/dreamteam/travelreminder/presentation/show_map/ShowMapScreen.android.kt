package ru.dreamteam.travelreminder.presentation.show_map


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.domen.model.travel.TransportationMode

@Composable
actual fun GoogleMapView(
    modifier: Modifier,
    viewModel: MapViewModel
) {
    val userLocation = viewModel.userLocation.value
    val cameraPositionState = rememberCameraPositionState()

    LaunchedEffect(userLocation) {
        userLocation.let { p ->
            if (p != null) {
                cameraPositionState.animate(
                    update = CameraUpdateFactory.newLatLngZoom(
                        LatLng(p.latitude, p.longitude),
                        15f
                    )
                )
            }
        }
    }
    val mapUiSettings = remember {
        MapUiSettings(
            zoomGesturesEnabled = true,
            myLocationButtonEnabled = true
        )
    }

    val mapProperties = remember {
        MapProperties(
            isMyLocationEnabled = true
        )
    }

    val routePoints by viewModel.polyline
    Column(Modifier.fillMaxSize()) {
        Row(Modifier.padding(8.dp)) {
            TextField(
                value = "",
                onValueChange = { },
                modifier = Modifier.weight(1f),
                label = { Text("Начальный адрес") }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Button(onClick = {
                viewModel.setPointSelectorAsOrigin()
            }) {
                Text("A")
            }
        }

        Row(Modifier.padding(8.dp)) {
            TextField(
                value = "",
                onValueChange = { },
                modifier = Modifier.weight(1f),
                label = { Text("Конечный адрес") }
            )
            Spacer(modifier = Modifier.width(4.dp))
            Button(onClick = {
                viewModel.setPointSelectorAsDestination()
            }) {
                Text("B")
            }
        }


        Box(modifier = Modifier.weight(1f)) {
            if (userLocation == null) {

            } else {
                var selectedPoints by remember {
                    mutableStateOf<Pair<LatLng?, LatLng?>>(Pair(null, null))
                }

                GoogleMap(
                    modifier = modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    uiSettings = mapUiSettings,
                    properties = mapProperties,
                    onMapClick = { clickedLatLng ->
                        selectedPoints = if (viewModel.isOriginPoint) {
                            Pair(clickedLatLng, selectedPoints.second)
                        } else {
                            Pair(selectedPoints.first, clickedLatLng)
                        }

                        if (selectedPoints.first != null && selectedPoints.second != null) {
                            val origin = selectedPoints.first
                            val destination = selectedPoints.second

                            if (destination != null && origin != null) {
                                viewModel.getRoute(
                                    origin = Point(origin.latitude, origin.longitude),
                                    destination = Point(
                                        destination.latitude,
                                        destination.longitude
                                    ),
                                    mode = TransportationMode.DRIVE
                                )
                            }
                        }
                    }
                ) {
                    selectedPoints.first.let {
                        if (it != null) {
                            Marker(
                                state = MarkerState(position = LatLng(it.latitude, it.longitude)),
                                title = "Начало маршрута"
                            )
                        }

                    }
                    selectedPoints.second.let {
                        if (it != null) {
                            Marker(
                                state = MarkerState(position = LatLng(it.latitude, it.longitude)),
                                title = "Начало маршрута"
                            )
                        }

                    }

                    if (routePoints.isNotEmpty()) {
                        Polyline(
                            points = routePoints.map { LatLng(it.latitude, it.longitude) },
                            color = Color.Blue,
                            width = 5f
                        )
                    }
                }
            }
        }
    }
}