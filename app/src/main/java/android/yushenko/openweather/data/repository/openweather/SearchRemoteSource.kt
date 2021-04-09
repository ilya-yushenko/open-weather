package android.yushenko.openweather.data

import android.util.Log
import android.yushenko.openweather.data.api.ApiHelper
import android.yushenko.openweather.data.model.search.Search
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SearchRemoteSource {
//    private var nameCity: String? = null
//
//    fun setData(name: String) {
//        nameCity = name
//    }

    suspend fun getSearchData(name: String): List<Search>? {
        val response: Response<List<Search>> = ApiHelper.weatherApi.getSearch(name)
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


//    fun getSearchDataK(onSearchRemoteReadyCallback: OnSearchRemoteReadyCallback) {
//        ApiHelper.weatherApi.getSearch(nameCity).enqueue(object : Callback<List<Search>> {
//            override fun onResponse(call: Call<List<Search>>, response: Response<List<Search>>) {
//                if (response.isSuccessful) {
//                    try {
//                        val search = response.body()
//                        if (search!![0].localNames?.ru != null) {
//                            onSearchRemoteReadyCallback.onIsFound(true)
//                            onSearchRemoteReadyCallback.onSearchRemoteDataReady(search)
//                        } else {
//                            onSearchRemoteReadyCallback.onIsFound(false)
//                        }
//                    } catch (e: Exception) {
//                        e.printStackTrace()
//                        onSearchRemoteReadyCallback.onIsFound(false)
//                    }
//                } else {
//                    onSearchRemoteReadyCallback.onIsFound(false)
//                }
//            }
//
//            override fun onFailure(call: Call<List<Search>>, t: Throwable) {
//                Log.i("TAG", "T: $t")
//            }
//        })
//    }
}

//interface OnSearchRemoteReadyCallback {
//    fun onSearchRemoteDataReady(list: List<Search>)
//    fun onIsFound(boolean: Boolean)
//}
