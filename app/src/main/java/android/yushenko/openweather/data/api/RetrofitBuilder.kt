package android.yushenko.openweather.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "http://api.openweathermap.org/"

    private fun configureRetrofit() : Retrofit =
            Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(configureHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

    private fun configureHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
    }

    val apiService: ApiService = configureRetrofit().create(ApiService::class.java)
}