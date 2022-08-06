package android.yushenko.openweather.shared

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.idapgroup.lifecycle.ktx.SingleLiveEvent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    val job = SupervisorJob()
    private val dispatcher = Dispatchers.Main

    override val coroutineContext: CoroutineContext
        get() = dispatcher + job + errorHandler

    val errorEvent = SingleLiveEvent<AppException>()

    private val errorHandler = CoroutineExceptionHandler { _, error ->
        error.printStackTrace()
        val value = if (error is AppException) error else error.toAppException()
        errorEvent.setValue(value)
    }

    override fun onCleared() {
        job.cancel()
        super.onCleared()
    }

    val viewState = MutableLiveData<ViewState>(ViewState.Idle)
    val loadingState = mutableStateOf<ViewState>(ViewState.Idle)
}