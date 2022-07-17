package android.yushenko.openweather.ext

import android.widget.EditText
import androidx.core.widget.addTextChangedListener

fun EditText.changeObserve(query: (String) -> Unit) {
    addTextChangedListener {
        val q = if (!it.isNullOrEmpty()) it.toString() else ""
        query(q)
    }
}