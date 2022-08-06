package android.yushenko.openweather.ui.home

import android.yushenko.openweather.model.CurrentWeather
import android.yushenko.openweather.model.DailyWeather
import android.yushenko.openweather.model.HourlyWeather

data class HomeState(
    val currentWeather: CurrentWeather? = null,
    val hourlyWeather: List<HourlyWeather> = emptyList(),
    val dailyWeather: List<DailyWeather> = emptyList(),
    val isRefreshing: Boolean = false
)