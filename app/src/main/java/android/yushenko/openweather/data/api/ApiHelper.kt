package android.yushenko.openweather.data.api

object ApiHelper {
    private val BASE_URL = "http://api.openweathermap.org/"
    val weatherApi: ApiService
        get() = RetrofitBuilder.getClient(BASE_URL).create(ApiService::class.java)
}