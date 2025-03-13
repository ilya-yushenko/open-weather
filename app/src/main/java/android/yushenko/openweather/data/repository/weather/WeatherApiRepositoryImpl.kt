package android.yushenko.openweather.data.repository.weather

import android.yushenko.openweather.data.model.response.location.LocationResponse
import android.yushenko.openweather.data.model.response.search.SearchResponse
import android.yushenko.openweather.data.model.response.settings.Units
import android.yushenko.openweather.data.model.response.weather.WeatherDataResponse
import android.yushenko.openweather.data.service.ApiRetrofitService

private const val APP_ID = "6960084fef590dc618fd30a2cde979b0"

class WeatherApiRepositoryImpl constructor(
    private val retrofit: ApiRetrofitService
) : WeatherApiRepository {

    override suspend fun getWeatherData(model: LocationResponse): WeatherDataResponse {
        return retrofit.getWeatherData(
            lat = model.lat,
            lon = model.lon,
            appId = APP_ID,
            units = Units.Metric.prefix,
            exclude = "minutely, alerts"
        )
    }

    override suspend fun getSearchByCity(query: String): List<SearchResponse> {
        return retrofit.getSearch(
            query = query,
            appId = APP_ID,
            limit = 5
        ).filter {
            !it.city.isNullOrEmpty()
        }
    }
}