package android.yushenko.openweather.ext

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

val Fragment.viewLifecycleScope: CoroutineScope
    get() = viewLifecycleOwner.lifecycleScope

fun <T> Flow<T>.subscribe(f: Fragment, action: suspend (T) -> Unit) {
    this.onEach(action).launchIn(f.viewLifecycleScope)
}

fun <T> Flow<T>.subscribe(scope: CoroutineScope, action: suspend (T) -> Unit) {
    this.onEach(action).launchIn(scope)
}

fun <T> CoroutineScope.observe(flow: Flow<T>, action: suspend (T) -> Unit) {
    flow.onEach(action).launchIn(this)
}

fun <T> Fragment.observe(flow: Flow<T>, action: suspend (T) -> Unit) {
    flow.onEach(action).launchIn(this.viewLifecycleScope)
}

fun Fragment.hideKeyboard() {
    (requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow(requireView().windowToken, 0)
}