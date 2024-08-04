package com.aghogho.studyalert.domain.model

data class Session(
    val sessionId: Int,
    val sessionSubjectId: Int,
    val relatedToSubject: String,
    val date: Long,
    val duration: Long
)
