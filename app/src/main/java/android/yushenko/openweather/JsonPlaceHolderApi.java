package android.yushenko.openweather;

import android.yushenko.openweather.weather.Weather;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceHolderApi {
    @GET("data/2.5/weather?&appid=5703a864f0e20a81fe6913ef3dc0e03b&lang=RU&mode=json&units=metric")
    Call<Weather> getWeather(@Query("lat") String lat, @Query("lon") String lon);
}
