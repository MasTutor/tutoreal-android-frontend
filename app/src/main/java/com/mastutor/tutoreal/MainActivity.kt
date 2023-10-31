package com.mastutor.tutoreal

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastutor.tutoreal.ui.components.MatchTutorComponent
import com.mastutor.tutoreal.ui.components.TutorComponent
import com.mastutor.tutoreal.ui.theme.TutorealTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorealTheme {
                Column(modifier = Modifier.background(color = MaterialTheme.colorScheme.secondary)) {
                    TutorComponent(
                        photoUrl = "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg",
                        name = "Jim Burton",
                        job = "Con Artist",
                        price = 200000,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .padding(20.dp)
                    )
                    MatchTutorComponent(
                        photoUrl = "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg",
                        name = "Jim Burton",
                        job = "Con Artist",
                        price = 200000,
                        percentage = 98.0,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}