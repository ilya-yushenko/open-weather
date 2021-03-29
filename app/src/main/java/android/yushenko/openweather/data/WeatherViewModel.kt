package android.yushenko.openweather.data

import android.app.Application
import android.yushenko.openweather.data.OnWeatherRepositoryCallback
import android.yushenko.openweather.data.WeatherRepository
import android.yushenko.openweather.model.WeatherOneCall
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    init {
        loadWeatherRepositories()
    }

    var liveData = MutableLiveData<WeatherOneCall>()

    fun loadWeatherRepositories() {
        val weatherRepository = WeatherRepository(getApplication())

        weatherRepository.getWeatherRepository(object : OnWeatherRepositoryCallback{
            override fun onDataReady(data: WeatherOneCall?) {
                liveData.value = data
            }

        })
    }
}