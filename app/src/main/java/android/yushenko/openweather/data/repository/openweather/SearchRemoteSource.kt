package android.yushenko.openweather.data.repository.openweather

import android.yushenko.openweather.data.api.ApiHelper
import android.yushenko.openweather.data.model.search.Search
import javax.inject.Inject

class SearchRemoteSource @Inject constructor(private  val api: ApiHelper) {
    suspend fun getSearchData(name: String): List<Search> = api.getSearchData(name)
}

