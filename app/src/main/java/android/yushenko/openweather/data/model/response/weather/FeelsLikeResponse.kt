package android.yushenko.openweather.data.model.response.weather


import com.google.gson.annotations.SerializedName

data class FeelsLikeResponse(
    @SerializedName("day")
    val day: Double,
    @SerializedName("eve")
    val eve: Double,
    @SerializedName("morn")
    val morn: Double,
    @SerializedName("night")
    val night: Double
)