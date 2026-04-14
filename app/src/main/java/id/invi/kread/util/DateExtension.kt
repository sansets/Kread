package id.invi.kread.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Date.formatToDisplay(): String {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return formatter.format(this)
}

fun Date.formatToServer(): String {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return formatter.format(this)
}

fun String.toDate(): Date? {
    val formatter = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return try {
        formatter.parse(this)
    } catch (_: Exception) {
        null
    }
}

fun String.serverToDate(): Date? {
    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return try {
        formatter.parse(this)
    } catch (_: Exception) {
        null
    }
}

fun String.formatTime(): String {
    val inputFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    val outputFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())
    return try {
        val date = inputFormatter.parse(this)
        if (date != null) outputFormatter.format(date) else this
    } catch (_: Exception) {
        this
    }
}