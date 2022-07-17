package android.yushenko.openweather.data.api

import android.yushenko.openweather.data.repository.weather.WeatherApiRepository
import android.yushenko.openweather.data.repository.weather.WeatherApiRepositoryImpl

class ApiServiceFactoryImpl : ApiServiceFactory {

    override fun apiService(): WeatherApiRepository = WeatherApiRepositoryImpl(
        Retrofit().create(ApiRetrofitService::class.java)
    )
}