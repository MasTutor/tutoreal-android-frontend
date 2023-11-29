package com.mastutor.tutoreal.ui.screen.failure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FailureScreen(
    modifier: Modifier = Modifier,
    onRefreshClicked: () -> Unit,
    logoutExist: Boolean = false,
    onLogoutClicked: () -> Unit = {},
){
    Column(
        modifier = modifier.fillMaxSize().padding(horizontal = 10.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "There's something wrong, please check your internet", textAlign = TextAlign.Center)
        Row {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = onRefreshClicked,
                modifier = Modifier
                    .padding(bottom = 20.dp, end = 10.dp), shape = RoundedCornerShape(8.dp)
            ) {
                Text("Refresh")
            }
            if(logoutExist) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                    onClick = onLogoutClicked,
                    modifier = Modifier
                        .padding(bottom = 20.dp), shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Logout")
                }
            }
        }
    }
}