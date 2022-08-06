package android.yushenko.openweather.ui.main.pager

import android.yushenko.openweather.data.model.response.location.toLocationResponse
import android.yushenko.openweather.data.repository.db.DataBaseFirebaseRepository
import android.yushenko.openweather.data.repository.weather.WeatherApiRepository
import android.yushenko.openweather.model.*
import android.yushenko.openweather.shared.BaseViewModel
import android.yushenko.openweather.shared.emptyString
import android.yushenko.openweather.shared.initLoading
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val weatherApiRepository: WeatherApiRepository,
    private val dataBaseRepository: DataBaseFirebaseRepository,
) : BaseViewModel() {

    private val _currentWeather = MutableSharedFlow<CurrentWeather>()
    val currentWeather: SharedFlow<CurrentWeather> = _currentWeather

    private val _hourlyWeather = MutableSharedFlow<List<HourlyWeather>>()
    val hourlyWeather: SharedFlow<List<HourlyWeather>> = _hourlyWeather

    private val _dailyWeather = MutableSharedFlow<List<DailyWeather>>()
    val dailyWeather: SharedFlow<List<DailyWeather>> = _dailyWeather


    fun loadWeather(model: Location) {
        launch {
            initLoading(viewState)
            weatherApiRepository.getWeatherData(
                model = model.toLocationResponse()
            ).also { weather ->
                weather.current.toCurrentWeather(model.city, emptyString()).also {
                    _currentWeather.emit(it)
                }
                weather.hourly.take(25).map {
                    it.toHourlyWeather()
                }.also {
                    _hourlyWeather.emit(it)
                }
                weather.daily.map {
                    it.toDailyWeather()
                }.also {
                    _dailyWeather.emit(it)
                }
            }
        }
    }

    fun deleteItemHistory(model: Location) {
        dataBaseRepository.deleteItemHistory(
            uuid = model.uuid
        )
    }
}