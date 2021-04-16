package android.yushenko.openweather.ui.viewmodel

import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.data.repository.WeatherRepository
import android.yushenko.openweather.ui.shared.BaseViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: WeatherRepository)  : BaseViewModel() {

    var liveSearchData = MutableLiveData<List<Search>>()

    fun loadSearch(name: String) = launch {
        repository.getSearchData(name).let { liveSearchData.postValue(it) }
    }

    fun addItemHistory(search: Search) = repository.addItemHistory(search)
}