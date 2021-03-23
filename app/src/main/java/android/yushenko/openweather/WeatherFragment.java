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
import android.yushenko.openweather.weatheronecall.WeatherOneCall;

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
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }


    private void requestWeatherOneCall(String name, double lat, double lon) {
        Call<WeatherOneCall> call = NetworkService.getInstance().getJsonApi().getWeatherOneCall(lat, lon);
        call.enqueue(new Callback<WeatherOneCall>() {
            @Override
            public void onResponse(Call<WeatherOneCall> call, Response<WeatherOneCall> response) {
                WeatherOneCall weather = response.body();
                DataLab.get().addHourlyList(weather.getHourly());
                setFragment();

                Picasso.with(getActivity())
                        .load("http://openweathermap.org/img/wn/" + weather.getCurrent().getWeather().get(0).getIcon() + "@2x.png")
                        .resize(100, 100)
                        .into(mImageView);

                mDateTV.setText(getDate());
                mCityTextView.setText(name);

                mDescTextView.setText(weather.getCurrent().getWeather().get(0).getDescription());
                mTempTextView.setText(weather.getCurrent().getTemp().intValue() + "°");

                mInfoTV.setText(String.format("%-15s%15s %n", "Восход солнца", getTime(weather.getCurrent().getSunrise())));
                mInfoTV.append(String.format("%-15s%16s %n", "Заход солнца", getTime(weather.getCurrent().getSunset())));
                mInfoTV.append(String.format("%-15s%11d°%n", "Чувствуется как", weather.getCurrent().getFeelsLike().intValue()));
                mInfoTV.append(String.format("%-15s%15d %%%n", "Влажность", weather.getCurrent().getHumidity()));
                mInfoTV.append(String.format("%-15s%18d гПа%n", "Давление", weather.getCurrent().getPressure()));
                mInfoTV.append(String.format("%-15s%14d %%%n", "Облачность", weather.getCurrent().getClouds()));
                mInfoTV.append(String.format("%-15s%14d км%n", "Видимость", weather.getCurrent().getVisibility() / 1000));
                mInfoTV.append(String.format("%-15s%15d%n", "Уф-индекс", weather.getCurrent().getUvi().intValue()));
                mInfoTV.append(String.format("%-15s%12.1f м/с%n", "Скорость ветра", weather.getCurrent().getWindSpeed()));
                if (weather.getCurrent().getWindGust() != null) {
                    mInfoTV.append(String.format("%-15s%14.1f м/с%n", "Порыв ветра", weather.getCurrent().getWindGust()));
                }
            }

            @Override
            public void onFailure(Call<WeatherOneCall> call, Throwable t) {
                Log.i("TAG", "TT " + t);
            }
        });
    }

    private void setFragment() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.hourly_container);
        if (fragment == null) {
            fragment = HourlyFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.hourly_container, fragment).commit();
        } else {
            fragment = HourlyFragment.newInstance();
            fm.beginTransaction()
                    .replace(R.id.hourly_container, fragment).commit();
        }
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
        String name = mSettings.getString(Preferences.APP_PREFERENCES_NAME, "Киев");
        double lat = mSettings.getFloat(Preferences.APP_PREFERENCES_LAT, 50.4333f);
        double lon = mSettings.getFloat(Preferences.APP_PREFERENCES_LON, 30.5167f);
        requestWeatherOneCall(name, lat, lon);
    }
}