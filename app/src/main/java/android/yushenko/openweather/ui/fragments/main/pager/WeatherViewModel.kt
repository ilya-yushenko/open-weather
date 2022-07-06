package android.yushenko.openweather.ui.fragments.main.pager

import android.yushenko.openweather.data.model.weather.WeatherOneCall
import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.data.repository.*
import android.yushenko.openweather.ui.shared.BaseViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
        private val weatherRepository: WeatherRepository,
) : BaseViewModel() {

    val liveWeatherData = MutableLiveData<WeatherOneCall>()

    fun loadWeather(search: Search) = launch {
        weatherRepository.getWeatherData(search).collect {
            liveWeatherData.value = it
        }
    }

    fun deleteItemHistory(search: Search) = weatherRepository.deleteItemHistory(search)
}
