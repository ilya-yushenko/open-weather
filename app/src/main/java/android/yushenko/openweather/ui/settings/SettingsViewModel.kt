package android.yushenko.openweather.ui.settings

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
class SettingsViewModel @Inject constructor(
    private val dataBaseRepository: DataBaseFirebaseRepository,
    private val oauthRepository: OauthFirebaseRepository,
) : BaseViewModel() {

    private val _liveCurrentUser = MutableLiveData<UserInitial>()
    val liveCurrentUser: LiveData<UserInitial> = _liveCurrentUser

    init {
        launch {
            _liveCurrentUser.value = dataBaseRepository.getUserData()
        }
    }

    fun signUserOut() {
        oauthRepository.signOut()
    }
}