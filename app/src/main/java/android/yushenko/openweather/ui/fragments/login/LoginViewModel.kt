package android.yushenko.openweather.ui.fragments.login

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
class LoginViewModel @Inject constructor(private val repository: WeatherRepository) : BaseViewModel() {

    init {
        repository.getChannelLoginOk().asFlow().onEach {
            liveSignInUser.value = it
        }.launchIn(this)
    }

    var liveSignInUser = MutableLiveData<Boolean>()

    fun signInUser(user: User) = repository.signInUser(user)

}