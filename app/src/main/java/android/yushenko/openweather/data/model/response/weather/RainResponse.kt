package android.yushenko.openweather.data.model.response.weather


import com.google.gson.annotations.SerializedName

data class RainResponse(
    @SerializedName("1h")
    val h: Double
)