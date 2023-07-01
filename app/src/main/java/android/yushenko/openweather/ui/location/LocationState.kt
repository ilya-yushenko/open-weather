package android.yushenko.openweather.ui.location

import android.yushenko.openweather.ui.location.util.LocationDetails

data class LocationState(
    val status: LocationStatus = LocationStatus.Determinate,
    val locationData: LocationDetails? = null,
)

enum class LocationStatus {
    RequestPermission, Determinate, Ready
}
