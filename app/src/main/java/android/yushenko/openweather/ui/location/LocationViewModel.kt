package android.yushenko.openweather.ui.location

import android.content.Context
import android.util.Log
import android.yushenko.openweather.shared.BaseViewModel
import android.yushenko.openweather.shared.initLoading
import android.yushenko.openweather.ui.location.util.LocationManager
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    @ApplicationContext val context: Context,
    private val locationManager: LocationManager
) : BaseViewModel() {

    private val _state = mutableStateOf(LocationState())
    val state: State<LocationState> = _state

    //  private val locationLiveData = LocationLiveData(context)
//    fun getLocationLiveData() = locationLiveData
//    fun startLocationUpdates() {
//        locationLiveData.startLocationUpdates()
//    }

    fun getLocation() {
        initLoading(loadingState)
        _state.value = state.value.copy(
            status = LocationStatus.Determinate
        )
        locationManager.getLocation {
            Log.i("TAG", "request Permision")
            _state.value = state.value.copy(
                status = LocationStatus.RequestPermission
            )
        }.onEach {
            Log.i("TAG", "Location: $it")
            _state.value = state.value.copy(
                locationData = it,
                status = LocationStatus.Ready
            )
        }.launchIn(this)
    }

    init {
//        launch {
//            _state.value = state.value.copy(
//                status = LocationStatus.Determinate
//            )
//            delay(3000)
//            _state.value = state.value.copy(
//                status = LocationStatus.Ready
//            )
//        }
    }

}