package android.yushenko.openweather.model

import android.yushenko.openweather.data.model.response.weather.CurrentResponse
import android.yushenko.openweather.shared.toUrl
import kotlin.math.roundToInt

data class CurrentWeather(
    val iconUrl: String,
    val localeName: String,
    val country: String,
    val date: Long,
    val description: String,
    val temp: Int,
    val sunrise: Long,
    val sunset: Long,
    val feelsLike: Int,
    val humidity: Int,
    val pressure: Int,
    val clouds: Int,
    val visibility: Int,
    val uvi: Int,
    val windSpeed: Double,
    val windGust: Double,
)

fun CurrentResponse.toCurrentWeather(localeName: String, country: String) = CurrentWeather(
    iconUrl = weather.first().icon.toUrl(),
    localeName = localeName,
    country = country,
    date = dt,
    description = weather.first().description,
    temp = temp.roundToInt(),
    sunrise = sunrise,
    sunset = sunset,
    feelsLike = feelsLike.roundToInt(),
    humidity = humidity,
    pressure = pressure,
    clouds = clouds,
    visibility = visibility,
    uvi = uvi.roundToInt(),
    windSpeed = windSpeed,
    windGust = windGust,
)