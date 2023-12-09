package com.mastutor.tutoreal.ui.screen.splash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navHostController: NavHostController,
    modifier: Modifier = Modifier
) {
    val userExist by viewModel.userExist

    LaunchedEffect(key1 = true) {
        viewModel.tryUserExist()
        delay(300L)
        if (userExist) {
            navHostController.navigate(Screen.Home.route) {
                popUpTo(0)
            }
        } else {
            navHostController.navigate(Screen.Chooser.route) {
                popUpTo(0)
            }
        }
    }
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Loading")
        CircularProgressIndicator(color = Color.Black)
    }


}