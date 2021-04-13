package android.yushenko.openweather.ui.viewmodel

import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.data.repository.WeatherRepository
import android.yushenko.openweather.ui.shared.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {

    var liveSearchData = MutableLiveData<List<Search>>()
    private val repository = WeatherRepository()

    fun loadSearch(name: String) = launch {
        repository.getSearchData(name).let { liveSearchData.postValue(it) }
    }

    fun addItemHistory(search: Search) = repository.addItemHistory(search)


}