package android.yushenko.openweather.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Search(
        @SerializedName("name")
        @Expose
        var name: String? = null,

        @SerializedName("local_names")
        @Expose
        var localNames: LocalNames? = null,

        @SerializedName("lat")
        @Expose
        var lat: Float = 0f,

        @SerializedName("lon")
        @Expose
        var lon: Float = 0f,

        @SerializedName("country")
        @Expose
        var country: String? = null,
)