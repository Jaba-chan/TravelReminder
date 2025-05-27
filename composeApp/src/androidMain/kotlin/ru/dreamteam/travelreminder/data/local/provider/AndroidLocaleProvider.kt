package ru.dreamteam.travelreminder.data.local.provider

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import ru.dreamteam.travelreminder.domen.model.travel.Point

class AndroidLocaleProvider(
    private val activity: ComponentActivity,
    private val context: Context
){

    private var listener: ((Point) -> Unit)? = null
    private var lastResult: Boolean = false
    private val fused = LocationServices.getFusedLocationProviderClient(context)

    private val cb = object : LocationCallback() {
        override fun onLocationResult(res: LocationResult) {
            res.lastLocation?.let {
                listener?.invoke(Point(it.latitude, it.longitude))
            }
        }
    }

     fun startLocationUpdate() {
        val req = LocationRequest.Builder(
            Priority.PRIORITY_HIGH_ACCURACY,
            100L)
            .setMinUpdateIntervalMillis(1000L)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            lastResult.takeIf { it } ?: run {
                launcher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                lastResult
            }
        }
        fused.requestLocationUpdates(req, cb, Looper.getMainLooper())
    }

     fun setOnLocationChangedListener(listener: (Point) -> Unit) {
        this.listener = listener
    }

    private val launcher = activity
        .registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            lastResult = isGranted
        }
}
