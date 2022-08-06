package android.yushenko.openweather.shared

import android.yushenko.openweather.ext.doFinally
import androidx.compose.runtime.MutableState
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope

sealed class ViewState {
    object Idle : ViewState()
    object Loading : ViewState()
}

fun CoroutineScope.initLoading(data: MutableLiveData<ViewState>) {
    data.value = ViewState.Loading
    doFinally {
        data.value = ViewState.Idle
    }
}

fun CoroutineScope.initLoading(data: MutableState<ViewState>) {
    data.value = ViewState.Loading
    doFinally {
        data.value = ViewState.Idle
    }
}