package android.yushenko.openweather.data.factory

import android.yushenko.openweather.data.repository.weather.WeatherApiRepository

interface ApiServiceFactory {
    fun apiService(): WeatherApiRepository
}