package android.yushenko.openweather.ui.register

import android.yushenko.openweather.data.model.authentication.UserInitial
import android.yushenko.openweather.data.repository.db.DataBaseFirebaseRepository
import android.yushenko.openweather.data.repository.oauth.OauthFirebaseRepository
import android.yushenko.openweather.shared.BaseViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val oauthRepository: OauthFirebaseRepository,
    private val dataBaseRepository: DataBaseFirebaseRepository,
) : BaseViewModel() {

    private val _liveIsCrateUser = MutableLiveData<Boolean>()
    val liveIsCrateUser: LiveData<Boolean> = _liveIsCrateUser

    fun createUser(user: UserInitial) {
        launch {
            oauthRepository.createUser(user).also {
                val isCrate = it.user != null
                if (isCrate) {
                    dataBaseRepository.userAddData(user)
                }
                _liveIsCrateUser.value = isCrate
            }
        }
    }
}