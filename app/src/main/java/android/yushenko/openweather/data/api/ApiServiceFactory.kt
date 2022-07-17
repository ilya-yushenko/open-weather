package android.yushenko.openweather.data.api

import android.yushenko.openweather.data.repository.weather.WeatherApiRepository

interface ApiServiceFactory {
    fun apiService(): WeatherApiRepository
}