package ru.dreamteam.travelreminder.presentation.show_map


import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import ru.dreamteam.travelreminder.R
import ru.dreamteam.travelreminder.domen.model.travel.Place
import ru.dreamteam.travelreminder.domen.model.travel.Point
import ru.dreamteam.travelreminder.presentation.MainActivityViewModel

@Composable
actual fun GoogleMapView(
    modifier: Modifier,
    viewModel: MapViewModel,
    changeAddress: (Boolean) -> Unit,
    returnToAddTravel: () -> Unit
) {
    val userLocation = viewModel.userLocation.value
    val cameraState = rememberCameraPositionState()
    val padding = PaddingValues(bottom = 64.dp)

    LaunchedEffect(userLocation) {
        userLocation?.let { p ->
            cameraState.animate(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(p.latitude, p.longitude), 15f
                )
            )
        }
    }

    Column(Modifier.fillMaxSize()) {
        MapHead(
            viewModel = viewModel,
            changeAddress = changeAddress,
            returnToAddTravel = returnToAddTravel
        )

        Box(Modifier.weight(1f)) {
            MapContent(
                modifier = modifier,
                cameraState = cameraState,
                contentPadding = padding,
                onMapClick = { viewModel.onMapClicked(it) },
                selectedPoints = viewModel.selectedPoints.value,
                routePoints = viewModel.route.value?.poly
            )

        }
    }
}

@Composable
fun rememberMarkerIcon(@DrawableRes drawableRes: Int, tint: Color): BitmapDescriptor? {
    val context = LocalContext.current
    return remember(drawableRes) {
        val drawable = ContextCompat.getDrawable(context, drawableRes) ?: return@remember null
        drawable.setTint(tint.toArgb())
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)

        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

@Composable
private fun MapContent(
    modifier: Modifier,
    cameraState: CameraPositionState,
    contentPadding: PaddingValues,
    onMapClick: (Point) -> Unit,
    selectedPoints: Pair<Place?, Place?>,
    routePoints: List<Point>?
) {

    var isMapLoaded by remember { mutableStateOf(false) }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraState,
        uiSettings = remember {
            MapUiSettings(
                zoomGesturesEnabled = true,
                myLocationButtonEnabled = true
            )
        },
        properties = remember { MapProperties(isMyLocationEnabled = true) },
        contentPadding = contentPadding,
        onMapClick = { onMapClick(Point(it.latitude, it.longitude)) },
        onMapLoaded = { isMapLoaded = true }
    ) {
        val (start, end) = selectedPoints
        val icon = if (isMapLoaded) rememberMarkerIcon(R.drawable.ic_circle, Color.Blue) else null

        start?.let {
            MapMarker(
                point = it.point,
                icon = icon,
                title = "Начало маршрута"
            )
        }
        end?.let {
            MapMarker(
                point = it.point,
                icon = null,
                title = "Конец маршрута"
            )
        }
        routePoints
            ?.takeIf(List<Point>::isNotEmpty)
            ?.map { LatLng(it.latitude, it.longitude) }
            ?.let {
                Polyline(
                    points = it,
                    color = Color.Blue,
                    width = 5f
                )
            }
    }
}

@Composable
private fun MapMarker(point: Point, icon: BitmapDescriptor?, title: String) {
    Marker(
        state = MarkerState(LatLng(point.latitude, point.longitude)),
        icon = icon,
        title = title
    )
}
