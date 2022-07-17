package android.yushenko.openweather.model

import android.yushenko.openweather.data.model.entity.LocationEntity

data class Location(
    val uuid: String,
    val date: Long,
    val country: String,
    val city: String,
    val lat: Float,
    val lon: Float,
)

fun LocationEntity.toLocationUi() = Location(
    uuid = uuid,
    date = date,
    country = country,
    city = city,
    lat = lat,
    lon = lon,
)