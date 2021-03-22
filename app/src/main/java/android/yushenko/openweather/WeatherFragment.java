package android.yushenko.openweather;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.yushenko.openweather.search.Search;
import android.yushenko.openweather.weather.Weather;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {

    private TextView mCityTextView;
    private ImageView mImageView;
    private TextView mDescTextView;
    private TextView mTempTextView;
    private TextView mInfoTV;
    private TextView mDateTV;

    private SharedPreferences mSettings;


    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.weather_fragment, container, false);

        mSettings = getActivity().getSharedPreferences(Preferences.APP_PREFERENCES, Context.MODE_PRIVATE);

        mCityTextView = view.findViewById(R.id.city_tv);
        mImageView = view.findViewById(R.id.view_image);
        mDescTextView = view.findViewById(R.id.description_tv);
        mTempTextView = view.findViewById(R.id.temp_tv);

        mInfoTV = view.findViewById(R.id.info_tv);
        mDateTV = view.findViewById(R.id.date_tv);

        mCityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SearchActivity.newIntent(getActivity());
                startActivity(intent);
                Log.i("TAG", "Hello city!");
            }
        });
        update();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    private void requestWeather(double lat, double lon) {

        Call<Weather> call = NetworkService.getInstance().getJsonApi().getWeather(lat, lon);

        call.enqueue(new Callback<Weather>() {
            @Override
            public void onResponse(Call<Weather> call, Response<Weather> response) {
                Weather weather = response.body();

                Picasso.with(getActivity())
                        .load("http://openweathermap.org/img/wn/" + weather.getWeather().get(0).getIcon() + "@2x.png")
                        .resize(100, 100)
                        .into(mImageView);

                mDateTV.setText(getDate());
                mCityTextView.setText(weather.getName());
                mDescTextView.setText(weather.getWeather().get(0).getDescription());
                mTempTextView.setText(weather.getMain().getTemp().intValue() + "°");

                mInfoTV.setText(String.format("%-15s%15s %n", "Восход солнца", getTime(weather.getSys().getSunrise())));
                mInfoTV.append(String.format("%-15s%16s %n", "Заход солнца", getTime(weather.getSys().getSunset())));
                mInfoTV.append(String.format("%-15s%11d°%n", "Чувствуется как", weather.getMain().getFeelsLike().intValue()));
                mInfoTV.append(String.format("%-15s%15d %%%n", "Влажность", weather.getMain().getHumidity()));
                mInfoTV.append(String.format("%-15s%18d гПа%n", "Давление", weather.getMain().getPressure()));
                mInfoTV.append(String.format("%-15s%15d %%%n", "Облачность", weather.getClouds().getAll()));
                mInfoTV.append(String.format("%-15s%14d км%n", "Видимость", weather.getVisibility() / 1000));
                mInfoTV.append(String.format("%-15s%12.1f м/с%n", "Скорость ветра", weather.getWind().getSpeed()));
                if (weather.getWind().getGust() != null) {
                    mInfoTV.append(String.format("%-15s%14.1f м/с%n", "Порыв ветра", weather.getWind().getGust()));
                }

            }

            @Override
            public void onFailure(Call<Weather> call, Throwable t) {
                Log.i("TAG", "TT " + t);
            }
        });
    }

    private String getTime(long seconds) {
        Date date = new Date(seconds * 1000L);
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(date);
    }

    private String getDate() {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM HH:mm", Locale.getDefault());
        return dateFormat.format(date);
    }

    private void update() {
        double lat = mSettings.getFloat(Preferences.APP_PREFERENCES_LAT, 50.4333f);
        double lon = mSettings.getFloat(Preferences.APP_PREFERENCES_LON, 30.5167f);
        requestWeather(lat, lon);
    }
}