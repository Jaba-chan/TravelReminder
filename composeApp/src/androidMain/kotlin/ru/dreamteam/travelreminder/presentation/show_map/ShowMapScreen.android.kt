package ru.dreamteam.travelreminder.presentation.show_map


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import org.jetbrains.compose.resources.painterResource
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.presentation.coomon_ui.CircularProgressBar
import travelreminder.composeapp.generated.resources.Res
import travelreminder.composeapp.generated.resources.ic_circle
import travelreminder.composeapp.generated.resources.ic_more_vert
import travelreminder.composeapp.generated.resources.ic_swap

@Composable
actual fun GoogleMapView(
    modifier: Modifier,
    viewModel: MapViewModel,
    changeAddress: () -> Unit
) {
    val userLocation = viewModel.userLocation.value
    val cameraPositionState = rememberCameraPositionState()
    val mapPadding = PaddingValues(
        bottom = 64.dp
    )
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

    val routePoints = viewModel.route.value?.poly


    Column(Modifier.wrapContentHeight()) {
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            val columnHeight = 100.dp
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.height(columnHeight)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_circle),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {}
                        .size(16.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Icon(
                    painter = painterResource(Res.drawable.ic_more_vert),
                    contentDescription = null,
                    modifier = Modifier
                        .height(20.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Icon(
                    Icons.Outlined.Place,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {}
                        .size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.height(columnHeight)
            ) {
                val textFieldsHeight = 40.dp
                OutlinedTextField(
                    modifier = Modifier
                        .height(textFieldsHeight),
                    value = viewModel.selectedPoints.value.first?.title ?: "",
                    onValueChange = { changeAddress() },
                )
                Spacer(modifier = Modifier.height(8.dp))
                CompactTextField(
                    modifier = Modifier.height(textFieldsHeight),
                    value = viewModel.selectedPoints.value.second?.title ?: "",
                    onValueChange = { changeAddress() },
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.height(columnHeight)
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_swap),
                    contentDescription = null,
                    modifier = Modifier
                        .clickable {}
                        .size(24.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))
        }

        Box(modifier = Modifier.weight(1f)) {
            if (userLocation == null) {
                CircularProgressBar()
            } else {
                val selectedPoints by remember {
                    viewModel.selectedPoints
                }
                GoogleMap(
                    modifier = modifier.fillMaxSize(),
                    cameraPositionState = cameraPositionState,
                    uiSettings = mapUiSettings,
                    properties = mapProperties,
                    contentPadding = mapPadding,
                    onMapClick = {
                        viewModel.onMapClicked(
                            Point(
                                latitude = it.latitude,
                                longitude = it.longitude
                            )
                        )
                    }
                ) {
                    selectedPoints.first.let {
                        if (it != null) {
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        it.point.latitude,
                                        it.point.longitude
                                    )
                                ),
                                title = "Начало маршрута"
                            )
                        }

                    }
                    selectedPoints.second.let {
                        if (it != null) {
                            Marker(
                                state = MarkerState(
                                    position = LatLng(
                                        it.point.latitude,
                                        it.point.longitude
                                    )
                                ),
                                title = "Начало маршрута"
                            )
                        }

                    }

                    if (routePoints != null) {
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
}


@Composable
fun CompactTextField(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        textStyle = MaterialTheme.typography.bodySmall,
        modifier = modifier
            .border(
                1.dp,
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 8.dp, vertical = 6.dp)
            .fillMaxWidth(),
        decorationBox = { innerTextField ->
            Row(modifier = Modifier.wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically) {
                innerTextField()
            }
        }
    )
}


