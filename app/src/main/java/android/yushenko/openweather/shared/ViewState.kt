package android.yushenko.openweather.shared

import android.yushenko.openweather.ext.doFinally
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