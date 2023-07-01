package android.yushenko.openweather.ui.location.util

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asFlow
import kotlinx.coroutines.flow.Flow

class LocationManager(
    private val context: Context
) : LocationListener {

    private var locationManager: LocationManager =
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    private val locationDetails = MutableLiveData<LocationDetails>()

    fun getLocation(requestPermission: () -> Unit): Flow<LocationDetails> {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            requestPermission()

        } else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)
        }
        return locationDetails.asFlow()
    }

    override fun onLocationChanged(location: Location) {
        locationDetails.value = LocationDetails(
            longitude = location.longitude,
            latitude = location.latitude
        )
    }

}