package android.yushenko.openweather.data.repository.openweather

import android.yushenko.openweather.data.api.ApiHelper
import android.yushenko.openweather.data.model.search.Search
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRemoteDataSource @Inject constructor(private  val api: ApiHelper) {

    fun getWeatherData(search: Search) = flow {
        val weather = api.getWeatherData(search)
        weather.nameLocale = search.name
        emit(weather)
    }
}
