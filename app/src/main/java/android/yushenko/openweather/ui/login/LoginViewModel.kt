package android.yushenko.openweather.ui.login

import android.yushenko.openweather.data.model.authentication.UserInitial
import android.yushenko.openweather.data.repository.oauth.OauthFirebaseRepository
import android.yushenko.openweather.shared.BaseViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val oauthRepository: OauthFirebaseRepository,
) : BaseViewModel() {

    private val _isSignIn = MutableStateFlow(false)
    val isSignIn: StateFlow<Boolean> = _isSignIn

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
                _isSignIn.value = it.user != null
            }
        }
    }

}