package com.mastutor.tutoreal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.mastutor.tutoreal.ui.screen.home.HomeContent
import com.mastutor.tutoreal.ui.screen.login.LoginScreen
import com.mastutor.tutoreal.ui.screen.matchmaking.MatchmakingOnboardingScreen
import com.mastutor.tutoreal.ui.screen.register.RegisterScreen
import com.mastutor.tutoreal.ui.theme.TutorealTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorealTheme {
                MainJetpack(modifier = Modifier.fillMaxSize())
            }
        }
    }
}