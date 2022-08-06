package android.yushenko.openweather.ui.home

import android.yushenko.openweather.data.model.response.location.LocationResponse
import android.yushenko.openweather.data.repository.weather.WeatherApiRepository
import android.yushenko.openweather.model.toCurrentWeather
import android.yushenko.openweather.model.toDailyWeather
import android.yushenko.openweather.model.toHourlyWeather
import android.yushenko.openweather.shared.BaseViewModel
import android.yushenko.openweather.shared.initLoading
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherApiRepository: WeatherApiRepository,
) : BaseViewModel() {

    private val _state = mutableStateOf(HomeState())
    val state: State<HomeState> = _state

    init {
        loadWeather()
    }

    fun loadWeather() {
        launch {
            initLoading(loadingState)
            weatherApiRepository.getWeatherData(
                LocationResponse(
                    city = "Los Angeles",
                    lat = 34.0201613,
                    lon = -118.6919205
                )
            ).also { weather ->
                weather.current.toCurrentWeather("Los Angeles", "USA").also {
                    _state.value = state.value.copy(
                        currentWeather = it
                    )
                }
                weather.hourly.take(25).map {
                    it.toHourlyWeather()
                }.toMutableList().also {
                    it.removeFirst()
                    _state.value = state.value.copy(
                        hourlyWeather = it
                    )
                }
                weather.daily.map {
                    it.toDailyWeather()
                }.toMutableList().also {
                    it.removeFirst()
                    _state.value = state.value.copy(
                        dailyWeather = it
                    )
                }
            }
        }
    }
}