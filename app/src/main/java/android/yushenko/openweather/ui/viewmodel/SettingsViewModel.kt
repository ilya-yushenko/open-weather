package android.yushenko.openweather.ui.viewmodel

import android.yushenko.openweather.data.repository.WeatherRepository
import android.yushenko.openweather.ui.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherRepository) : BaseViewModel() {

    fun getEmailUser() = repository.getEmailUser()

    fun signUserOut() = repository.signUserOut()
}