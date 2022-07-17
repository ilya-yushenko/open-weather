package android.yushenko.openweather.model

import android.yushenko.openweather.data.model.response.weather.DailyResponse
import android.yushenko.openweather.shared.toUrl
import kotlin.math.roundToInt

data class DailyWeather(
    val iconUrl: String,
    val date: Long,
    val tempMax: Int,
    val tempMin: Int,
    val windSpeed: Double,
)

fun DailyResponse.toDailyWeather() = DailyWeather(
    iconUrl = weather.first().icon.toUrl(),
    date = dt,
    tempMax = temp.max.roundToInt(),
    tempMin = temp.min.roundToInt(),
    windSpeed = windSpeed
)