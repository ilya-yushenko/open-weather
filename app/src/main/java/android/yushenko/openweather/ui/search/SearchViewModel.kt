package android.yushenko.openweather.ui.search

import android.yushenko.openweather.data.model.entity.toLocationEntity
import android.yushenko.openweather.data.repository.db.DataBaseFirebaseRepository
import android.yushenko.openweather.data.repository.weather.WeatherApiRepository
import android.yushenko.openweather.model.Search
import android.yushenko.openweather.model.toSearch
import android.yushenko.openweather.shared.BaseViewModel
import android.yushenko.openweather.shared.initLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val weatherApiRepository: WeatherApiRepository,
    private val dataBaseRepository: DataBaseFirebaseRepository,
) : BaseViewModel() {

    private val query = MutableStateFlow("")
    private val _searchData = MutableStateFlow(emptyList<Search>())
    val searchData: StateFlow<List<Search>> = _searchData

    init {
        observeQueryChanges()
    }

    fun setQuery(newQuery: String) {
        query.value = newQuery
    }

    fun addItemHistory(search: Search) {
        dataBaseRepository.addItemHistory(search.toLocationEntity())
    }

    private fun observeQueryChanges() {
        query.debounce(Debounce).onEach {
            loadSearch(it)
        }.launchIn(this)
    }

    private fun loadSearch(name: String) {
        launch {
            initLoading(viewState)
            weatherApiRepository.getSearchByCity(name)
                .map {
                    it.toSearch()
                }.also {
                    _searchData.emit(it)
                    delay(500L)
                }
        }
    }

    companion object {
        const val Debounce = 300L
    }
}