package android.yushenko.openweather.ui.shared

import android.util.Log
import androidx.lifecycle.ViewModel
import com.idapgroup.lifecycle.ktx.SingleLiveEvent
import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

open class BaseViewModel : ViewModel(), CoroutineScope {

    private val dispatchers = Dispatchers.Main
    private val job = SupervisorJob()

    val errorEvent = SingleLiveEvent<String>()

    private val errorHandler = CoroutineExceptionHandler { _, throwable ->
        val error = when(throwable) {
            is UnknownHostException, is ConnectException -> "No Internet"
            else -> "unknown exception"
        }
        Log.i("TAG", error)
        errorEvent.setValue(error)
    }

    override val coroutineContext: CoroutineContext
        get() = dispatchers + job + errorHandler

    override fun onCleared() {
        coroutineContext.cancel()
        super.onCleared()
    }
}