package android.yushenko.openweather.data

import android.util.Log
import android.yushenko.openweather.data.network.common.Common
import android.yushenko.openweather.model.search.Search
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SearchRemoteSource {
    private var nameCity: String? = null

    fun setData(name: String) {
        nameCity = name
    }

    fun getSearchData(onSearchRemoteReadyCallback: OnSearchRemoteReadyCallback) {
        Log.i("TAG", "Name City: " + nameCity)
        Common.weatherApi.getSearch(name_city = nameCity).enqueue(object : Callback<List<Search>> {
            override fun onResponse(call: Call<List<Search>>, response: Response<List<Search>>) {
                if (response.isSuccessful) {
                    try {
                        val search = response.body()
                        if (search!![0].localNames?.ru != null) {
                            onSearchRemoteReadyCallback.onIsFound(true)
                            onSearchRemoteReadyCallback.onSearchRemoteDataReady(search)
                        } else {
                            onSearchRemoteReadyCallback.onIsFound(false)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                        onSearchRemoteReadyCallback.onIsFound(false)
                    }
                } else {
                    onSearchRemoteReadyCallback.onIsFound(false)
                }
            }

            override fun onFailure(call: Call<List<Search>>, t: Throwable) {
                Log.i("TAG", "T: $t")
            }
        })
    }
}

interface OnSearchRemoteReadyCallback {
    fun onSearchRemoteDataReady(list: List<Search>)
    fun onIsFound(boolean: Boolean)
}
