package android.yushenko.openweather.data.model.response.settings

enum class Units(val prefix: String) {
    Kelvin("standard"),
    Metric("metric"),
    Imperial("imperial")
}