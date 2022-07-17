package android.yushenko.openweather.ui.login

import android.yushenko.openweather.data.model.authentication.UserInitial
import android.yushenko.openweather.data.repository.oauth.OauthFirebaseRepository
import android.yushenko.openweather.shared.BaseViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val oauthRepository: OauthFirebaseRepository,
) : BaseViewModel() {

    private val _liveIsSignIn = MutableLiveData<Boolean>()
    val liveIsSignIn: LiveData<Boolean> = _liveIsSignIn

    fun signInUser(email: String, password: String) {
        launch {
            oauthRepository.signIn(
                UserInitial(
                    name = "",
                    locatoin = "",
                    email = email,
                    password = password
                )
            ).also {
                _liveIsSignIn.value = it.user != null
            }
        }
    }

}