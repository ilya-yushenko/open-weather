package android.yushenko.openweather.data.repository.openweather

import android.yushenko.openweather.data.api.ApiHelper
import android.yushenko.openweather.data.model.weather.WeatherOneCall
import android.yushenko.openweather.data.model.search.Search
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private  val api: ApiHelper) {

    suspend fun getWeatherData(search: Search) : WeatherOneCall {
        val weather = api.getWeatherData(search)
        weather.nameLocale = search.name
        return weather
    }
}
