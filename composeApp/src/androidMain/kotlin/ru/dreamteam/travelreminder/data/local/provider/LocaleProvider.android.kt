package ru.dreamteam.travelreminder.data.local.provider

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import ru.dreamteam.travelreminder.domen.model.travel.Point

actual class LocaleProvider(
    context: Context
) {
    private var listener: ((Point) -> Unit)? = null
    private val fused = LocationServices.getFusedLocationProviderClient(context)

    private val cb = object : LocationCallback() {
        override fun onLocationResult(res: LocationResult) {
            res.lastLocation?.let {
                listener?.invoke(Point(it.latitude, it.longitude))
            }
        }
    }

    @SuppressLint("MissingPermission")
    actual fun startLocationUpdate() {
        val req = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            100L)
            .setMinUpdateIntervalMillis(1000L)
            .build()

        fused.requestLocationUpdates(req, cb, Looper.getMainLooper())
    }

    actual fun setOnLocationChangedListener(listener: (Point) -> Unit) {
        this.listener = listener
    }
}