package android.yushenko.openweather.data.repository.openweather

import android.yushenko.openweather.data.api.ApiHelper
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRemoteSource @Inject constructor(private  val api: ApiHelper) {

    fun getSearchData(name: String) = flow {
        emit(api.getSearchData(name).filter { !it.localNames?.ru.isNullOrEmpty() })
    }
}

