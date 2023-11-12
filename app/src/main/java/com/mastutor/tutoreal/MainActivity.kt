package com.mastutor.tutoreal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mastutor.tutoreal.data.local.QuestionData
import com.mastutor.tutoreal.ui.screen.survey.SurveyContent
import com.mastutor.tutoreal.ui.theme.TutorealTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorealTheme {

            }
        }
    }
}