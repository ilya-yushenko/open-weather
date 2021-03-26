package android.yushenko.openweather.data.network.common

import android.yushenko.openweather.data.network.`interface`.RetrofitServices
import android.yushenko.openweather.data.network.retrofit.RetrofitClient

object Common {
    private val BASE_URL = "http://api.openweathermap.org/"
    val weatherApi: RetrofitServices
        get() = RetrofitClient.getClient(BASE_URL).create(RetrofitServices::class.java)
}