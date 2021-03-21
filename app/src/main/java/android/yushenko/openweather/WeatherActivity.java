package android.yushenko.openweather;

import androidx.fragment.app.Fragment;

public class WeatherActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return WeatherFragment.newInstance();
    }
}
