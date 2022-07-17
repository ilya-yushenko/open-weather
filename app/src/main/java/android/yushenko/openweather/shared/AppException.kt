package android.yushenko.openweather.shared

import android.yushenko.openweather.R
import androidx.annotation.StringRes
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException

sealed class AppException(message: String? = null) : IOException(message) {
    class Id(@StringRes val id: Int) : AppException()
    object NoInternet : AppException()
    object Unexpected : AppException()
}

fun Throwable.toAppException(): AppException =
    when (this) {
        is UnknownHostException, is ConnectException -> AppException.NoInternet
        is AppException -> this
        else -> AppException.Id(R.string.unknown_exception)
    }