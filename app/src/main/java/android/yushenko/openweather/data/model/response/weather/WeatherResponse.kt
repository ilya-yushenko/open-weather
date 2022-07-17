package android.yushenko.openweather.data.model.response.weather


import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("description")
    val description: String,
    @SerializedName("icon")
    val icon: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("main")
    val main: String
)