package com.mastutor.tutoreal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import com.mastutor.tutoreal.ui.screen.booking.BookingScreen
import com.mastutor.tutoreal.ui.screen.home.HomeContent
import com.mastutor.tutoreal.ui.screen.login.LoginScreen
import com.mastutor.tutoreal.ui.screen.matchmaking.MatchmakingOnboardingScreen
import com.mastutor.tutoreal.ui.screen.matchmaking.MatchmakingResultScreen
import com.mastutor.tutoreal.ui.screen.register.RegisterPictureScreen
import com.mastutor.tutoreal.ui.screen.register.RegisterScreen
import com.mastutor.tutoreal.ui.screen.tutor.TutorScreen
import com.mastutor.tutoreal.ui.theme.TutorealTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT, android.graphics.Color.TRANSPARENT)
        )
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            TutorealTheme {
                 MainJetpack(modifier = Modifier.fillMaxSize())
            }
        }
    }
}