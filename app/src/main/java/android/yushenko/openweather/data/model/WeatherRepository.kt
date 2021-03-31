package android.yushenko.openweather.data

import android.content.Context
import android.yushenko.openweather.data.preferences.AppPreferences
import android.yushenko.openweather.model.WeatherOneCall
import android.yushenko.openweather.search.Search

class WeatherRepository(context: Context) {
    private val appPreferences = AppPreferences(context)
    private val remoteDataSource = WeatherRemoteDataSource()
    private val searchDataSource = SearchRemoteSource()


    fun getWeatherRepository(onWeatherRepositoryCallback: OnWeatherRepositoryCallback) {
        remoteDataSource.setData(appPreferences.getSearchPreference())
        remoteDataSource.getWeatherData(object : OnWeatherRemoteReadyCallback {
            override fun onRemoteDataReady(data: WeatherOneCall?) {
                onWeatherRepositoryCallback.onDataReady(data)
            }
        })
    }

    fun getSearchRepository(name: String, onSearchRepositoryCallback: OnSearchRepositoryCallback) {
        searchDataSource.setData(name)
        searchDataSource.getSearchData(object : OnSearchRemoteReadyCallback {
            override fun onSearchRemoteDataReady(list: List<Search>) {
                onSearchRepositoryCallback.onSearchDataReady(list)
            }

            override fun onIsFound(boolean: Boolean) {
                onSearchRepositoryCallback.onIsFound(boolean)
            }

        })
    }

    fun setSearchPreference(search: Search) {
        appPreferences.setSearchPreference(search)
    }
}

interface OnWeatherRepositoryCallback {
    fun onDataReady(data: WeatherOneCall?)
}

interface OnSearchRepositoryCallback {
    fun onSearchDataReady(searches: List<Search>)
    fun onIsFound(isFound: Boolean)
}