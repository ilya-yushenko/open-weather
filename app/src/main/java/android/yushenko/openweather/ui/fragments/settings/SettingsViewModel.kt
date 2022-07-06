package android.yushenko.openweather.ui.fragments.settings

import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.data.repository.WeatherRepository
import android.yushenko.openweather.ui.shared.BaseViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
        private val repository: WeatherRepository,
) : BaseViewModel() {

    val liveUserUserData = MutableLiveData<User>()

    init {
        launch {
            repository.getUserData().collect { liveUserUserData.value = it }
        }
    }

    fun signUserOut() = repository.signUserOut()
}