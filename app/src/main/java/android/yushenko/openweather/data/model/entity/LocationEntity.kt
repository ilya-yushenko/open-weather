package android.yushenko.openweather.data.model.entity

import android.yushenko.openweather.model.Search
import com.google.gson.annotations.SerializedName
import java.util.*

data class LocationEntity(
    @SerializedName("uuid")
    val uuid: String = "",
    @SerializedName("lat")
    val date: Long = 0,
    @SerializedName("country")
    val country: String = "",
    @SerializedName("name")
    val city: String = "",
    @SerializedName("lat")
    val lat: Float = 0f,
    @SerializedName("lon")
    val lon: Float = 0f,
)

fun Search.toLocationEntity() = LocationEntity(
    uuid = UUID.randomUUID().toString(),
    date = Date().time,
    country = country,
    city = city,
    lat = lat,
    lon = lon,
)