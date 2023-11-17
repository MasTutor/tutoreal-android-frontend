package com.mastutor.tutoreal.ui.screen.chooser

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ChooserScreen(
    modifier: Modifier = Modifier,
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
){
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.secondary)
        .padding(horizontal = 10.dp)){
        Column(modifier = Modifier.align(Alignment.BottomCenter)){
            Button(
                onClick = onLoginClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Login")
            }
            Button(
                onClick = onRegisterClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.primary
                ),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary)
            ) {
                Text("Register")
            }
        }
    }
}