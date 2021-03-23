package android.yushenko.openweather.data.network;

import android.yushenko.openweather.search.Search;
import android.yushenko.openweather.model.WeatherOneCall;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApi {
    @GET("data/2.5/onecall?&exclude=minutely&appid=5703a864f0e20a81fe6913ef3dc0e03b&lang=RU&units=metric")
    Call<WeatherOneCall> getWeatherOneCall(@Query("lat") double lat, @Query("lon") double lon);

    @GET("geo/1.0/direct?&limit=5&appid=5703a864f0e20a81fe6913ef3dc0e03b")
    Call<List<Search>> getSearch(@Query("q") String name_city);
}
