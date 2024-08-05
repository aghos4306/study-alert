package com.aghogho.studyalert

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aghogho.studyalert.presentation.dashboard.DashboardScreen
import com.aghogho.studyalert.presentation.subject.SubjectScreen
import com.aghogho.studyalert.presentation.theme.StudyAlertTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StudyAlertTheme {
                //DashboardScreen()
                SubjectScreen()
            }
        }
    }
}
