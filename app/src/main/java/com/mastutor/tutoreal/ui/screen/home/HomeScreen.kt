package com.mastutor.tutoreal.ui.screen.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mastutor.tutoreal.ui.components.MatchmakingCardComponent

@Composable
fun HomeScreen(){
    
}

@Composable
fun HomeContent(modifier: Modifier = Modifier){
    Column(modifier = modifier.fillMaxSize()) {
        MatchmakingCardComponent(height = 240)
    }
}