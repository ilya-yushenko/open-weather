package android.yushenko.openweather.data.factory

import android.yushenko.openweather.data.service.ApiRetrofitService
import android.yushenko.openweather.data.repository.weather.WeatherApiRepository
import android.yushenko.openweather.data.repository.weather.WeatherApiRepositoryImpl

class ApiServiceFactoryImpl : ApiServiceFactory {

    override fun apiService(): WeatherApiRepository = WeatherApiRepositoryImpl(
        Retrofit().create(ApiRetrofitService::class.java)
    )
}