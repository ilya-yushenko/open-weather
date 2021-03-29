package android.yushenko.openweather.data

import android.content.Context
import android.yushenko.openweather.data.network.common.Common
import android.yushenko.openweather.model.WeatherOneCall
import retrofit2.Callback
import retrofit2.Response

class WeatherRemoteDataSource(context: Context) {

    val settings = context.getSharedPreferences(Preferences.APP_PREFERENCES, Context.MODE_PRIVATE)

    fun getWeatherData(onWeatherRemoteReadyCallback: OnWeatherRemoteReadyCallback) {

        val name: String? = settings.getString(Preferences.APP_PREFERENCES_NAME, "Киев")
        val lat: Double = settings.getFloat(Preferences.APP_PREFERENCES_LAT, 50.4333f).toDouble()
        val lon: Double = settings.getFloat(Preferences.APP_PREFERENCES_LON, 30.5167f).toDouble()


        Common.weatherApi.getWeatherOneCall(lat, lon)
                .enqueue(object : Callback<WeatherOneCall> {
                    override fun onResponse(call: retrofit2.Call<WeatherOneCall>, response: Response<WeatherOneCall>) {
                        var weatherOneCall = response.body()
                        weatherOneCall?.nameLocale = name

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