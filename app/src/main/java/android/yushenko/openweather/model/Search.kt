package android.yushenko.openweather.model

import android.yushenko.openweather.data.model.response.search.SearchResponse

data class Search(
    val country: String,
    val city: String,
    val lat: Float,
    val lon: Float,
)

fun SearchResponse.toSearch() = Search(
    country = country,
    city = city ?: "",
    lat = lat,
    lon = lon,
)