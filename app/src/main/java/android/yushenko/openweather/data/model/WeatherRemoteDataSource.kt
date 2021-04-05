package android.yushenko.openweather.data

import android.yushenko.openweather.data.network.common.Common
import android.yushenko.openweather.model.weather.WeatherOneCall
import android.yushenko.openweather.model.search.Search
import retrofit2.Callback
import retrofit2.Response

class WeatherRemoteDataSource {
//    private lateinit var search: Search
//
//    fun setData(search: Search) {
//        this.search = search
//    }

    fun getWeatherData(search: Search, onWeatherRemoteReadyCallback: OnWeatherRemoteReadyCallback) {
        Common.weatherApi.getWeatherOneCall(search.lat.toDouble(), search.lon.toDouble())
                .enqueue(object : Callback<WeatherOneCall> {
                    override fun onResponse(call: retrofit2.Call<WeatherOneCall>, response: Response<WeatherOneCall>) {
                        val weatherOneCall = response.body()
                        weatherOneCall?.nameLocale = search.name

                        onWeatherRemoteReadyCallback.onRemoteDataReady(response.body())
                    }

                    override fun onFailure(call: retrofit2.Call<WeatherOneCall>, t: Throwable) {
                    }
                })
    }
}

interface OnWeatherRemoteReadyCallback {
    fun onRemoteDataReady(data: WeatherOneCall?)
}