package android.yushenko.openweather.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import android.yushenko.openweather.data.DataLab;
import android.yushenko.openweather.ui.daily.DailyFragment;
import android.yushenko.openweather.ui.hourly.HourlyFragment;
import android.yushenko.openweather.data.network.NetworkService;
import android.yushenko.openweather.data.Preferences;
import android.yushenko.openweather.R;
import android.yushenko.openweather.ui.search.SearchActivity;
import android.yushenko.openweather.model.WeatherOneCall;

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
    private TextView mInfoTitleTV;
    private TextView mInfoDataTV;
    private TextView mDateTV;

    private SwipeRefreshLayout mRefreshLayout;

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

        mInfoTitleTV = view.findViewById(R.id.info_title_tv);
        mInfoDataTV = view.findViewById(R.id.info_data_tv);
        mDateTV = view.findViewById(R.id.date_tv);

        mCityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = SearchActivity.newIntent(getActivity());
                startActivity(intent);
                Log.i("TAG", "Hello city!");
            }
        });

        mRefreshLayout = view.findViewById(R.id.swipe_refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
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
                mRefreshLayout.setRefreshing(false);
                WeatherOneCall weather = response.body();
                DataLab.get().addHourlyList(weather.getHourly());
                DataLab.get().addDailyList(weather.getDaily());
                setHourlyFragment();
                setDailyFragment();

                Picasso.with(getActivity())
                        .load("http://openweathermap.org/img/wn/" + weather.getCurrent().getWeather().get(0).getIcon() + "@2x.png")
                        .resize(100, 100)
                        .into(mImageView);

                mDateTV.setText(getDate());
                mCityTextView.setText(name);

                mDescTextView.setText(weather.getCurrent().getWeather().get(0).getDescription());
                mTempTextView.setText(weather.getCurrent().getTemp().intValue() + "°");

                mInfoTitleTV.setText("Восход солнца\n");
                mInfoDataTV.setText(getTime(weather.getCurrent().getSunrise()) + "\n");

                mInfoTitleTV.append("Заход солнца\n");
                mInfoDataTV.append(getTime(weather.getCurrent().getSunset()) + "\n");

                mInfoTitleTV.append("Чувствуется как\n");
                mInfoDataTV.append(weather.getCurrent().getFeelsLike().intValue() + "°\n");

                mInfoTitleTV.append("Влажность\n");
                mInfoDataTV.append(weather.getCurrent().getHumidity() + " %\n");

                mInfoTitleTV.append("Давление\n");
                mInfoDataTV.append(weather.getCurrent().getPressure() + " гПа\n" );

                mInfoTitleTV.append("Облачность\n");
                mInfoDataTV.append(weather.getCurrent().getClouds() + " %\n");

                mInfoTitleTV.append("Видимость\n");
                mInfoDataTV.append(weather.getCurrent().getVisibility() / 1000 +" км\n");

                mInfoTitleTV.append("Уф-индекс\n");
                mInfoDataTV.append(weather.getCurrent().getUvi().intValue() + "\n");

                mInfoTitleTV.append("Скорость ветра\n");
                mInfoDataTV.append(weather.getCurrent().getWindSpeed() + " м/с\n");
                if (weather.getCurrent().getWindGust() != null) {
                    mInfoTitleTV.append("Порыв ветра\n");
                    mInfoDataTV.append(weather.getCurrent().getWindGust() + " м/с\n");
                }
            }

            @Override
            public void onFailure(Call<WeatherOneCall> call, Throwable t) {
                Log.i("TAG", "TT " + t);
            }
        });
    }

    private void setHourlyFragment() {
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

    private void setDailyFragment() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.daily_container);
        if (fragment == null) {
            fragment = DailyFragment.newInstance();
            fm.beginTransaction()
                    .add(R.id.daily_container, fragment).commit();
        } else {
            fragment = DailyFragment.newInstance();
            fm.beginTransaction()
                    .replace(R.id.daily_container, fragment).commit();
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