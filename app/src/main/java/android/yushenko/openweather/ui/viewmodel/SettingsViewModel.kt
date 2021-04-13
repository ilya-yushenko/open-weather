package android.yushenko.openweather.ui.viewmodel

import android.yushenko.openweather.data.repository.WeatherRepository
import android.yushenko.openweather.ui.shared.BaseViewModel

class SettingsViewModel : BaseViewModel() {

    private val repository = WeatherRepository()

    fun getEmailUser() = repository.getEmailUser()

    fun signUserOut() = repository.signUserOut()
}