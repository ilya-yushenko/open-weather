package android.yushenko.openweather.data.model.response.search

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("country")
    val country: String,
    @SerializedName("name")
    val city: String?,
    @SerializedName("lat")
    val lat: Float,
    @SerializedName("lon")
    val lon: Float,
)