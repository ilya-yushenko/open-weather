package android.yushenko.openweather.shared

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatToTime(): String {
    val date = Date(this * 1000L)
    val dateFormat = SimpleDateFormat("HH:mm", Locale.ENGLISH)
    return dateFormat.format(date)
}

fun Long.formatToDay(): String {
    val date = Date(this * 1000L)
    val dateFormat = SimpleDateFormat("EEEE", Locale.ENGLISH)
    return dateFormat.format(date)
}

fun Long.formatToDdMm(): String {
    val dateFormat = SimpleDateFormat("dd.MM", Locale.ENGLISH)
    return dateFormat.format(Date(this))

}

fun Long.formatToDate(): String {
    val date = Date(this * 1000L)
    val dateFormat = SimpleDateFormat("EEEE dd MMMM HH:mm", Locale.ENGLISH)
    return dateFormat.format(date)
}
