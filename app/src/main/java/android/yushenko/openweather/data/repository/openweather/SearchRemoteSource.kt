package android.yushenko.openweather.data.repository.openweather

import android.yushenko.openweather.data.api.ApiHelper
import android.yushenko.openweather.data.model.search.Search
import retrofit2.Response
import java.lang.Exception

class SearchRemoteSource {

    suspend fun getSearchData(name: String): List<Search> = ApiHelper.getSearchData(name)
}

