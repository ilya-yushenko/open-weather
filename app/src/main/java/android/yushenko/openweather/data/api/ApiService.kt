package android.yushenko.openweather.data.api

import android.yushenko.openweather.data.model.weather.WeatherOneCall
import android.yushenko.openweather.data.model.search.Search
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/onecall?&exclude=minutely&appid=2fe33e8f98b90ba79130c937d64be144&lang=RU&units=metric")
    suspend fun getWeatherOneCall(@Query("lat") lat: Double, @Query("lon") lon: Double): WeatherOneCall

    @GET("geo/1.0/direct?&limit=5&appid=2fe33e8f98b90ba79130c937d64be144")
    suspend fun getSearch(@Query("q") name_city: String?): List<Search>
}