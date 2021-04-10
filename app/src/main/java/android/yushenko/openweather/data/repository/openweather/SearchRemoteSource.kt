package android.yushenko.openweather.data.repository.openweather

import android.yushenko.openweather.data.api.ApiHelper
import android.yushenko.openweather.data.model.search.Search
import retrofit2.Response
import java.lang.Exception

class SearchRemoteSource {
    suspend fun getSearchData(name: String): List<Search>? {
        val response: Response<List<Search>> = ApiHelper.getSearchData(name)
        return if (response.isSuccessful) {
            try {
                val searches = response.body()
                if (searches!![0].localNames?.ru != null) {
                    searches
                } else {
                    null
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        } else {
            null
        }
    }
}

