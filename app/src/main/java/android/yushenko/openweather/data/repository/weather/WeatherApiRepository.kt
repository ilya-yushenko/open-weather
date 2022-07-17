package android.yushenko.openweather.data.repository.weather

import android.yushenko.openweather.data.model.response.location.LocationResponse
import android.yushenko.openweather.data.model.response.weather.WeatherDataResponse
import android.yushenko.openweather.data.model.response.search.SearchResponse

interface WeatherApiRepository {
    suspend fun getWeatherData(model: LocationResponse): WeatherDataResponse
    suspend fun getSearchByCity(query: String): List<SearchResponse>
}