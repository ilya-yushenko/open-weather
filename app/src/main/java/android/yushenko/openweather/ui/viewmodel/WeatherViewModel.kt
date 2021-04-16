package android.yushenko.openweather.ui.viewmodel

import android.util.Log
import android.yushenko.openweather.data.model.weather.WeatherOneCall
import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.data.repository.*
import android.yushenko.openweather.ui.shared.BaseViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository): BaseViewModel() {

    var liveWeatherData = MutableLiveData<WeatherOneCall>()
    var liveHistorySearchData = MutableLiveData<List<Search>>()

    fun loadWeather(search: Search) = launch {
        Log.i("TAG", "loadWeather")
        liveWeatherData.postValue(weatherRepository.getWeatherData(search))
    }

    fun loadHistorySearch() = launch {
        Log.i("TAG", "loadHistorySearch")
        liveHistorySearchData.postValue(weatherRepository.getHistorySearches().asReversed())
    }

    fun deleteItemHistory(search: Search) {
        weatherRepository.deleteItemHistory(search)
    }

    fun isUserSignIn(): Boolean {
        return weatherRepository.isUserSignIn()
    }
}