package android.yushenko.openweather.ext

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job

fun CoroutineScope.doFinally(action: (it: Throwable?) -> Unit) {
    coroutineContext[Job]?.invokeOnCompletion {
        action(it)
    }
}