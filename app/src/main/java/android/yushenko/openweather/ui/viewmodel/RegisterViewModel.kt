package android.yushenko.openweather.ui.viewmodel

import android.util.Log
import android.yushenko.openweather.data.model.authentication.User
import android.yushenko.openweather.data.repository.WeatherRepository
import android.yushenko.openweather.ui.shared.BaseViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.launch

class RegisterViewModel : BaseViewModel() {

    var liveCrateUserData = MutableLiveData<Boolean>()
    private val repository = WeatherRepository()

    fun createUser(user: User) = launch {
        val inf = repository.createUser(user)
        Log.i("TAG", "Crated: $inf")
        liveCrateUserData.postValue(inf)
    }
}