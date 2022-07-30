package android.yushenko.openweather.ui.register

import android.yushenko.openweather.data.model.authentication.UserInitial
import android.yushenko.openweather.data.repository.db.DataBaseFirebaseRepository
import android.yushenko.openweather.data.repository.oauth.OauthFirebaseRepository
import android.yushenko.openweather.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val oauthRepository: OauthFirebaseRepository,
    private val dataBaseRepository: DataBaseFirebaseRepository,
) : BaseViewModel() {

    private val _isCrateUser = MutableStateFlow(false)
    val isCrateUser: StateFlow<Boolean> = _isCrateUser

    fun createUser(user: UserInitial) {
        launch {
            oauthRepository.createUser(user).also {
                val isCrate = it.user != null
                if (isCrate) {
                    dataBaseRepository.userAddData(user)
                }
                _isCrateUser.value = isCrate
            }
        }
    }
}