package com.aghogho.studyalert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aghogho.studyalert.domain.model.Session
import com.aghogho.studyalert.domain.model.Subject
import com.aghogho.studyalert.domain.model.Task
import com.aghogho.studyalert.presentation.dashboard.DashboardScreen
import com.aghogho.studyalert.presentation.subject.SubjectScreen
import com.aghogho.studyalert.presentation.task.TaskScreen
import com.aghogho.studyalert.presentation.theme.StudyAlertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyAlertTheme {
                //DashboardScreen()
                //SubjectScreen()
                TaskScreen()
            }
        }
    }
}

val dummySubjects = listOf(
    Subject(subjectId = 0, name = "Algorithm", goalHours = 10f, colors = Subject.subjectCardColors[0]),
    Subject(subjectId = 0, name = "Kotlin", goalHours = 11f, colors = Subject.subjectCardColors[1]),
    Subject(subjectId = 0, name = "Java Essentials", goalHours = 8f, colors = Subject.subjectCardColors[2]),
    Subject(subjectId = 0, name = "Economics", goalHours = 5f, colors = Subject.subjectCardColors[3]),
    Subject(subjectId = 0, name = "Android Basics", goalHours = 13f, colors = Subject.subjectCardColors[4]),
)

val dummyTask = listOf(
    Task(
        taskId = 0,
        taskSubjectId = 1,
        title = "Prepare Notes",
        description = "",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "",
        isComplete = false
    ),
    Task(
        taskId = 0,
        taskSubjectId = 1,
        title = "Revise Python",
        description = "",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "",
        isComplete = true
    ),
    Task(
        taskId = 0,
        taskSubjectId = 1,
        title = "Build Android Snoop Task",
        description = "",
        dueDate = 0L,
        priority = 1,
        relatedToSubject = "",
        isComplete = false
    ),
    Task(
        taskId = 0,
        taskSubjectId = 1,
        title = "Study Kotlin for Pro",
        description = "",
        dueDate = 0L,
        priority = 0,
        relatedToSubject = "",
        isComplete = false
    ),
    Task(
        taskId = 0,
        taskSubjectId = 1,
        title = "Study Java",
        description = "",
        dueDate = 0L,
        priority = 2,
        relatedToSubject = "",
        isComplete = true
    ),
)

val dummySession = listOf(
    Session(
        relatedToSubject = "Python",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),
    Session(
        relatedToSubject = "Advanced Kotlin",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),
    Session(
        relatedToSubject = "Generics In Kotlin",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),
    Session(
        relatedToSubject = "Snoop Task",
        date = 0L,
        duration = 2,
        sessionSubjectId = 0,
        sessionId = 0
    ),
)
