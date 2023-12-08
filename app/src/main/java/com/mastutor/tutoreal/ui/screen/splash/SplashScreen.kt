package com.mastutor.tutoreal.ui.screen.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    navHostController: NavHostController
){
    val userExist by viewModel.userExist

    LaunchedEffect(key1 = true){
        viewModel.tryUserExist()
        delay(300L)
        if (userExist){
            navHostController.navigate(Screen.Home.route){
                popUpTo(0)
            }
        }
        else{
            navHostController.navigate(Screen.Chooser.route){
                popUpTo(0)
            }
        }
    }
    Box(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.primary)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
        )
    {
        Icon(imageVector = Icons.Filled.Palette, contentDescription = null, tint = Color.White, modifier = Modifier.size(240.dp))
    }
}