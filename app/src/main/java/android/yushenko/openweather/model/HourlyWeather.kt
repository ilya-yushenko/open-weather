package android.yushenko.openweather.model

import android.yushenko.openweather.data.model.response.weather.HourlyResponse
import android.yushenko.openweather.shared.toUrl
import kotlin.math.roundToInt

data class HourlyWeather(
    val iconUrl: String,
    val date: Long,
    val temp: Int,
    val windSpeed: Double,
)

fun HourlyResponse.toHourlyWeather() = HourlyWeather(
    iconUrl = weather.first().icon.toUrl(),
    date = dt,
    temp = temp.roundToInt(),
    windSpeed = windSpeed
)


