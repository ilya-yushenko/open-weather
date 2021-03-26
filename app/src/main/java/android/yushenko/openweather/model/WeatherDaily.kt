package android.yushenko.openweather.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WeatherDaily(
        @SerializedName("id")
        @Expose
        var id: Int? = null,

        @SerializedName("main")
        @Expose
        var main: String? = null,

        @SerializedName("description")
        @Expose
        var description: String? = null,

        @SerializedName("icon")
        @Expose
        var icon: String? = null,
)