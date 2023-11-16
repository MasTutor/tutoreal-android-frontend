package com.mastutor.tutoreal.ui.navigation.screen

sealed class Screen(val route: String){
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
}