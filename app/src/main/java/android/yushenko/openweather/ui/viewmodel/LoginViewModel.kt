package android.yushenko.openweather.ui.viewmodel

import android.util.Log
import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.data.repository.WeatherRepository
import android.yushenko.openweather.ui.shared.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

    var liveSignInUser = MutableLiveData<Boolean>()
    private val repository = WeatherRepository()

    fun signInUser(user: User) = launch {
        val inf = repository.signInUser(user)
        Log.i("TAG", "Login: $inf")
        liveSignInUser.postValue(inf)
    }


}