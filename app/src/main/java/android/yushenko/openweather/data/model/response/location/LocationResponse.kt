package android.yushenko.openweather.data.model.response.location

import android.yushenko.openweather.model.Location

data class LocationResponse(
    val city: String,
    val lat: Double,
    val lon: Double,
)

fun Location.toLocationResponse() = LocationResponse(
    city = city,
    lat = lat.toDouble(),
    lon = lon.toDouble(),
)