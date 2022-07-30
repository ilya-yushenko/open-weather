package android.yushenko.openweather.ui.settings

import android.yushenko.openweather.data.model.authentication.UserInitial
import android.yushenko.openweather.data.model.response.settings.Units
import android.yushenko.openweather.data.repository.db.DataBaseFirebaseRepository
import android.yushenko.openweather.data.repository.oauth.OauthFirebaseRepository
import android.yushenko.openweather.data.repository.settings.SettingsRepository
import android.yushenko.openweather.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dataBaseRepository: DataBaseFirebaseRepository,
    private val oauthRepository: OauthFirebaseRepository,
    private val settingsRepository: SettingsRepository,
) : BaseViewModel() {

    private val _currentUser = MutableSharedFlow<UserInitial>()
    val currentUser: SharedFlow<UserInitial> = _currentUser

    private val _currentUnit = MutableStateFlow(Units.Metric)
    val currentUnit: StateFlow<Units> = _currentUnit

    init {
        launch {
            dataBaseRepository.getUserData()?.let {
                _currentUser.emit(it)
            }
            settingsRepository.getLiveCurrentUnit().onEach {
                _currentUnit.emit(it)
            }.launchIn(this)
        }
    }

    fun signUserOut() {
        oauthRepository.signOut()
    }

    fun setCurrentUnit(unit: Units) {
        settingsRepository.setCurrentUnit(unit)
    }
}