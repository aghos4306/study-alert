package com.aghogho.studyalert.domain.model

data class Task(
    val taskId: Int,
    val taskSubjectId: Int,
    val title: String,
    val description: String,
    val dueDate: Long,
    val priority: Int,
    val relatedToSubject: String,
    val isComplete: Boolean
)
