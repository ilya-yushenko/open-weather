package android.yushenko.openweather;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkService {
    private static final String BASE_URL = "http://api.openweathermap.org/";

    private static NetworkService sInstance;
    private Retrofit mRetrofit;

    public static NetworkService getInstance() {
        if (sInstance == null) {
            sInstance = new NetworkService();
        }
        return sInstance;
    }

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Log.i("TAG", "URL: " + mRetrofit.baseUrl());

    }

    public JsonPlaceHolderApi getJsonApi() {
        return mRetrofit.create(JsonPlaceHolderApi.class);
    }
}
