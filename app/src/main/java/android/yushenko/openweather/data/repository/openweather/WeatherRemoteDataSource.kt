package android.yushenko.openweather.data.repository.openweather

import android.yushenko.openweather.data.api.ApiHelper
import android.yushenko.openweather.data.model.weather.WeatherOneCall
import android.yushenko.openweather.data.model.search.Search

class WeatherRemoteDataSource {

    suspend fun getWeatherData(search: Search) : WeatherOneCall {
        val weather = ApiHelper.getWeatherData(search)
        weather.nameLocale = search.name
        return weather
    }
}
