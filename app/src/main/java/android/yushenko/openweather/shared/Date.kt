package android.yushenko.openweather.shared

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatToTime(): String {
    val date = Date(this * 1000L)
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}

fun Long.formatToDay(): String {
    val date = Date(this * 1000L)
    val dateFormat = SimpleDateFormat("EEEE", Locale.getDefault())
    return dateFormat.format(date)
}

fun Long.formatToDate(): String {
    val date = Date(this * 1000L)
    val dateFormat = SimpleDateFormat("EEEE dd MMMM HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}
