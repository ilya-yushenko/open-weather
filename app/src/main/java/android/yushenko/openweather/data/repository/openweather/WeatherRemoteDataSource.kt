package android.yushenko.openweather.data

import android.util.Log
import android.yushenko.openweather.data.api.ApiHelper
import android.yushenko.openweather.data.model.weather.WeatherOneCall
import android.yushenko.openweather.data.model.search.Search
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response

class WeatherRemoteDataSource {

    suspend fun getWeatherData(search: Search) : WeatherOneCall? {
        val response: Response<WeatherOneCall> = ApiHelper.weatherApi.getWeatherOneCall(search.lat.toDouble(), search.lon.toDouble())
        try {
            if (response.isSuccessful) {
                val weatherOneCall = response.body()
                weatherOneCall?.nameLocale = search.name
                return weatherOneCall
            } else {
                Log.i("TAG", "Error: ${response.code()}")
            }
        } catch (e: HttpException) {
            Log.i("TAG", "Exception ${e.message}")
        } catch (e: Throwable) {
            Log.i("TAG", "Ooops: Something else went wrong")
        }
        return null
    }


//    fun getWeatherData(search: Search, onWeatherRemoteReadyCallback: OnWeatherRemoteReadyCallback) {
//        ApiHelper.weatherApi.getWeatherOneCall(search.lat.toDouble(), search.lon.toDouble())
//                .enqueue(object : Callback<WeatherOneCall> {
//                    override fun onResponse(call: retrofit2.Call<WeatherOneCall>, response: Response<WeatherOneCall>) {
//                        val weatherOneCall = response.body()
//                        weatherOneCall?.nameLocale = search.name
//
//                        onWeatherRemoteReadyCallback.onRemoteDataReady(response.body())
//                    }
//
//                    override fun onFailure(call: retrofit2.Call<WeatherOneCall>, t: Throwable) {
//                    }
//                })
//    }
}

//interface OnWeatherRemoteReadyCallback {
//    fun onRemoteDataReady(data: WeatherOneCall?)
//}