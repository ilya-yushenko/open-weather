package android.yushenko.openweather.data.network.`interface`

import android.yushenko.openweather.model.weather.WeatherOneCall
import android.yushenko.openweather.model.search.Search
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitServices {
    @GET("data/2.5/onecall?&exclude=minutely&appid=5703a864f0e20a81fe6913ef3dc0e03b&lang=RU&units=metric")
    fun getWeatherOneCall(@Query("lat") lat: Double, @Query("lon") lon: Double): Call<WeatherOneCall>

    @GET("geo/1.0/direct?&limit=5&appid=5703a864f0e20a81fe6913ef3dc0e03b")
    fun getSearch(@Query("q") name_city: String?): Call<List<Search>>
}