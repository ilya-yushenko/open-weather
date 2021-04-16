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
class RegisterViewModel @Inject constructor(private val repository: WeatherRepository) : BaseViewModel() {

    var liveCrateUserData = MutableLiveData<Boolean>()

    fun createUser(user: User) = launch {
        val inf = repository.createUser(user)
        Log.i("TAG", "Crated: $inf")
        liveCrateUserData.postValue(inf)
    }
}