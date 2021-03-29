package android.yushenko.openweather.data

import android.content.Context
import android.yushenko.openweather.model.WeatherOneCall

class WeatherRepository(context: Context) {
    val remoteDataSource = WeatherRemoteDataSource(context)

    fun getWeatherRepository(onWeatherRepositoryCallback: OnWeatherRepositoryCallback) {
        remoteDataSource.getWeatherData(object : OnWeatherRemoteReadyCallback {
            override fun onRemoteDataReady(data: WeatherOneCall?) {
                onWeatherRepositoryCallback.onDataReady(data)
            }
        })
    }
}

interface OnWeatherRepositoryCallback {
    fun onDataReady(data: WeatherOneCall?)
}