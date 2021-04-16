package android.yushenko.openweather.ui.viewmodel

import android.util.Log
import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.data.repository.WeatherRepository
import android.yushenko.openweather.ui.shared.BaseViewModel
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: WeatherRepository) : BaseViewModel() {

    var liveSignInUser = MutableLiveData<Boolean>()

    fun signInUser(user: User) = launch {
        val inf = repository.signInUser(user)
        Log.i("TAG", "Login: $inf")
        liveSignInUser.postValue(inf)
    }


}