package com.aghogho.studyalert.util

import androidx.compose.ui.graphics.Color
import com.aghogho.studyalert.presentation.theme.Green
import com.aghogho.studyalert.presentation.theme.Orange
import com.aghogho.studyalert.presentation.theme.Red
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

enum class Priority(val title: String, val color: Color, val value: Int) {
    LOW(title = "Low", color = Green, value = 0),
    MEDIUM(title = "Medium", color = Orange, value = 1),
    HIGH(title = "High", color = Red, value = 2);

    companion object {
        fun fromInt(value: Int) = entries.firstOrNull {
            it.value == value
        } ?: MEDIUM
    }
}

fun Long?.changeMillisToDateString(): String {
    return if (this != null) {
        val date = Instant.ofEpochMilli(this)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        date.format(DateTimeFormatter.ofPattern("dd MMM yyyy"))
    } else {
        "Invalid Date"
    }
}
