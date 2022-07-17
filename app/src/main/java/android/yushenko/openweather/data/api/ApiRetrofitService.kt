package android.yushenko.openweather.data.api

import android.yushenko.openweather.data.model.response.weather.WeatherDataResponse
import android.yushenko.openweather.data.model.response.search.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRetrofitService {

    @GET("data/2.5/onecall?")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") appId: String,
        @Query("units") units: String,
        @Query("exclude") exclude: String,
    ): WeatherDataResponse

    @GET("geo/1.0/direct?")
    suspend fun getSearch(
        @Query("q") query: String?,
        @Query("appid") appId: String,
        @Query("limit") limit: Int,
    ): List<SearchResponse>
}