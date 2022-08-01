package android.yushenko.openweather.data.model.search

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LocalNames(
        @SerializedName("ar")
        @Expose
        var ar: String? = null,

        @SerializedName("ascii")
        @Expose
        var ascii: String? = null,

        @SerializedName("az")
        @Expose
        var az: String? = null,

        @SerializedName("bg")
        @Expose
        var bg: String? = null,

        @SerializedName("ca")
        @Expose
        var ca: String? = null,

        @SerializedName("da")
        @Expose
        var da: String? = null,

        @SerializedName("de")
        @Expose
        var de: String? = null,

        @SerializedName("en")
        @Expose
        var en: String? = null,

        @SerializedName("eu")
        @Expose
        var eu: String? = null,

        @SerializedName("fa")
        @Expose
        var fa: String? = null,

        @SerializedName("feature_name")
        @Expose
        var featureName: String? = null,

        @SerializedName("fi")
        @Expose
        var fi: String? = null,

        @SerializedName("fr")
        @Expose
        var fr: String? = null,

        @SerializedName("he")
        @Expose
        var he: String? = null,

        @SerializedName("hr")
        @Expose
        var hr: String? = null,

        @SerializedName("hu")
        @Expose
        var hu: String? = null,

        @SerializedName("it")
        @Expose
        var it: String? = null,

        @SerializedName("ja")
        @Expose
        var ja: String? = null,

        @SerializedName("la")
        @Expose
        var la: String? = null,

        @SerializedName("lt")
        @Expose
        var lt: String? = null,

        @SerializedName("nl")
        @Expose
        var nl: String? = null,

        @SerializedName("no")
        @Expose
        var no: String? = null,

        @SerializedName("pl")
        @Expose
        var pl: String? = null,

        @SerializedName("ro")
        @Expose
        var ro: String? = null,

        @SerializedName("ru")
        @Expose
        var ru: String? = null,

        @SerializedName("sk")
        @Expose
        var sk: String? = null,

        @SerializedName("sr")
        @Expose
        var sr: String? = null,

        @SerializedName("tr")
        @Expose
        var tr: String? = null,
)