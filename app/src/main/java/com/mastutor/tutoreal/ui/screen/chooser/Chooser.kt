package com.mastutor.tutoreal.ui.screen.chooser

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Biotech
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChooserScreen(
    modifier: Modifier = Modifier,
    onLoginClicked: () -> Unit,
    onRegisterClicked: () -> Unit,
){
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = MaterialTheme.colorScheme.secondary)){
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier
            .fillMaxWidth()
            .align(
                Alignment.Center
            )
            .offset(y = (-100).dp)
            .padding(horizontal = 20.dp)) {
            Icon(imageVector = Icons.Filled.Biotech, contentDescription = "", tint = MaterialTheme.colorScheme.primary, modifier = Modifier
                .size(240.dp)
            )
            Text(text = "Tutoreal", style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary, fontSize = 42.sp))

        }
        Box(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topEnd = 24.dp))
            .background(color = Color.White)
            .align(Alignment.BottomCenter)
            .height(260.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp))
            .background(color = MaterialTheme.colorScheme.primary)
            .align(Alignment.BottomCenter)
            .height(240.dp))
        Column(modifier = Modifier
            .align(Alignment.BottomCenter)
            .padding(40.dp)){
            Button(
                onClick = onRegisterClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),

            ) {
                Text("Register")
            }
            Button(
                onClick = onLoginClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color.White
                ),
                border = BorderStroke(width = 1.dp, color = Color.White)
            ) {
                Text("Login")
            }
        }

    }
}