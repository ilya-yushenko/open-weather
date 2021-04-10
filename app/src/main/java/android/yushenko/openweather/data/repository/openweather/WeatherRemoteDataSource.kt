package android.yushenko.openweather.data.repository.openweather

import android.util.Log
import android.yushenko.openweather.data.api.ApiHelper
import android.yushenko.openweather.data.model.weather.WeatherOneCall
import android.yushenko.openweather.data.model.search.Search
import retrofit2.HttpException
import retrofit2.Response

class WeatherRemoteDataSource {

    suspend fun getWeatherData(search: Search) : WeatherOneCall? {
        val response: Response<WeatherOneCall> = ApiHelper.getWeatherData(search)
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
}
