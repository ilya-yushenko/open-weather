package android.yushenko.openweather.data.api

import android.yushenko.openweather.data.model.search.Search

object ApiHelper {

    suspend fun getWeatherData(search: Search) = RetrofitBuilder.apiService.getWeatherOneCall(search.lat.toDouble(), search.lon.toDouble())
    suspend fun getSearchData(name: String) = RetrofitBuilder.apiService.getSearch(name)
}