package android.yushenko.openweather.ui;

import android.yushenko.openweather.ui.main.WeatherFragment;

import androidx.fragment.app.Fragment;

public class WeatherActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return WeatherFragment.newInstance();
    }
}
