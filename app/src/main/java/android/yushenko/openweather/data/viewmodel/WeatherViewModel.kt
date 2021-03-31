package android.yushenko.openweather.data.viewmodel

import android.app.Application
import android.util.Log
import android.yushenko.openweather.data.OnSearchRepositoryCallback
import android.yushenko.openweather.data.OnWeatherRepositoryCallback
import android.yushenko.openweather.data.WeatherRepository
import android.yushenko.openweather.model.WeatherOneCall
import android.yushenko.openweather.search.Search
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class WeatherViewModel(application: Application) : AndroidViewModel(application) {

    var liveData = MutableLiveData<WeatherOneCall>()
    var liveSearchData = MutableLiveData<List<Search>>()
    var liveIsFound = MutableLiveData<Boolean>()
    val weatherRepository = WeatherRepository(getApplication())

    fun loadWeatherRepositories() : Boolean {
        weatherRepository.getWeatherRepository(object : OnWeatherRepositoryCallback {
            override fun onDataReady(data: WeatherOneCall?) {
                data?.let { liveData.value = it }
            }
        })
        return true
    }

    fun loadSearchRepository(name: String) {
        weatherRepository.getSearchRepository(name, object : OnSearchRepositoryCallback {
            override fun onSearchDataReady(searches: List<Search>) {
                liveSearchData.postValue(searches)
            }

            override fun onIsFound(isFound: Boolean) {
                liveIsFound.value = isFound
            }
        })
    }

    fun savePreference(search: Search) {
        weatherRepository.setSearchPreference(search)
    }
}