package android.yushenko.openweather.data.model.response.weather


import com.google.gson.annotations.SerializedName

data class WeatherDataResponse(
    @SerializedName("current")
    val current: CurrentResponse,
    @SerializedName("daily")
    val daily: List<DailyResponse>,
    @SerializedName("hourly")
    val hourly: List<HourlyResponse>,
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double,
    @SerializedName("timezone")
    val timezone: String,
    @SerializedName("timezone_offset")
    val timezoneOffset: Int
)