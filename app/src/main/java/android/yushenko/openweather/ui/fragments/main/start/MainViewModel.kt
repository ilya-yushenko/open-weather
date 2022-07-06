package android.yushenko.openweather.ui.fragments.main.start

import android.yushenko.openweather.data.model.search.Search
import android.yushenko.openweather.data.repository.*
import android.yushenko.openweather.ui.shared.BaseViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
        private val weatherRepository: WeatherRepository,
) : BaseViewModel() {

    init {
//        weatherRepository.getChannelProcessingHistoryData().asFlow().onEach {
//            liveHistorySearchData.value = it
//        }.launchIn(this)


        weatherRepository.getChannelLoginOk().asFlow().onEach {
            loadHistorySearch()
        }.launchIn(this)

        if (isUserSignIn()) loadHistorySearch()
        //loadHistorySearch()
    }

    val liveHistorySearchData = MutableLiveData<List<Search>>()

    fun isUserSignIn(): Boolean = weatherRepository.isUserSignIn()

    private fun loadHistorySearch() = weatherRepository.getHistorySearches()

    fun initLiveData() = weatherRepository.getLiveHistory()
}
