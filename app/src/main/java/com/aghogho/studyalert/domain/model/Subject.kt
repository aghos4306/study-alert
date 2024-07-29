package com.aghogho.studyalert.domain.model

import androidx.compose.ui.graphics.Color
import com.aghogho.studyalert.presentation.theme.gradient1
import com.aghogho.studyalert.presentation.theme.gradient2
import com.aghogho.studyalert.presentation.theme.gradient3
import com.aghogho.studyalert.presentation.theme.gradient4
import com.aghogho.studyalert.presentation.theme.gradient5

data class Subject(
    val name: String,
    val goalHours: Float,
    val colors: List<Color>
) {
    companion object {
        val subjectCardColors = listOf(gradient1, gradient2, gradient3, gradient4, gradient5)
    }
}
