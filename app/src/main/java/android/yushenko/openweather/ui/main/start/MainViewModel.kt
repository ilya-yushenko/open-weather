package android.yushenko.openweather.ui.main.start

import android.yushenko.openweather.data.repository.db.DataBaseFirebaseRepository
import android.yushenko.openweather.data.repository.oauth.OauthFirebaseRepository
import android.yushenko.openweather.model.Location
import android.yushenko.openweather.model.toLocationUi
import android.yushenko.openweather.shared.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val oauthRepository: OauthFirebaseRepository,
    private val dataBaseRepository: DataBaseFirebaseRepository,
) : BaseViewModel() {

    private val _savedList = MutableStateFlow(emptyList<Location>())
    val savedList: StateFlow<List<Location>> = _savedList

    init {
        dataBaseRepository.fetchSavedList()
        getSavedList()
    }

    private fun getSavedList() {
        dataBaseRepository.getSavedListLive().onEach { locations ->
            locations.map {
                it.toLocationUi()
            }.sortedBy {
                -it.date
            }.also {
                _savedList.emit(it)
            }
        }.launchIn(this)
    }

    fun isUserSignIn(): Boolean = oauthRepository.isSignIn()
}
