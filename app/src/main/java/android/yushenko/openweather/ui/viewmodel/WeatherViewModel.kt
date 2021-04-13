package android.yushenko.openweather.ui.viewmodel

import android.yushenko.openweather.data.model.weather.WeatherOneCall
import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.data.repository.*
import android.yushenko.openweather.ui.shared.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch

class WeatherViewModel : BaseViewModel() {

    private val weatherRepository = WeatherRepository()

    var liveWeatherData = MutableLiveData<WeatherOneCall>()
    var liveHistorySearchData = MutableLiveData<List<Search>>()

    fun loadWeather(search: Search) = launch {
        liveWeatherData.postValue(weatherRepository.getWeatherData(search))
    }

    fun loadHistorySearch() = launch {
        liveHistorySearchData.postValue(weatherRepository.getHistorySearches().asReversed())
    }

    fun deleteItemHistory(search: Search) {
        weatherRepository.deleteItemHistory(search)
    }

    fun isUserSignIn(): Boolean {
        return weatherRepository.isUserSignIn()
    }
}