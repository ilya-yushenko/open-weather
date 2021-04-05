package android.yushenko.openweather.model.weather

import com.google.gson.annotations.SerializedName

data class WeatherOneCall(
        var nameLocale: String? = null,

        @SerializedName("lat")
        var lat: Double? = null,

        @SerializedName("lon")
        var lon: Double? = null,

        @SerializedName("timezone")
        var timezone: String? = null,

        @SerializedName("timezone_offset")
        var timezoneOffset: Int? = null,

        @SerializedName("current")
        var current: Current? = null,

        @SerializedName("hourly")
        var hourly: List<Hourly>? = null,

        @SerializedName("daily")
        var daily: List<Daily>? = null,
)