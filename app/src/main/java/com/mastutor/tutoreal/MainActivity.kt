package com.mastutor.tutoreal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mastutor.tutoreal.ui.screen.home.HomeContent
import com.mastutor.tutoreal.ui.screen.matchmaking.MatchmakingOnboardingScreen
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