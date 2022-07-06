package android.yushenko.openweather.ui.fragments.register

import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.data.repository.WeatherRepository
import android.yushenko.openweather.ui.shared.BaseViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: WeatherRepository) : BaseViewModel() {

    init {
        repository.getChannelRegisterOk().asFlow().onEach {
            liveCrateUserData.value = it
        }.launchIn(this)
    }

    var liveCrateUserData = MutableLiveData<Boolean>()

    fun createUser(user: User) = repository.createUser(user)
}