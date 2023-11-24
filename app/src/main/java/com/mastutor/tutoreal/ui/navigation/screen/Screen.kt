package com.mastutor.tutoreal.ui.navigation.screen

sealed class Screen(val route: String){
    object Login : Screen("login")
    object Register : Screen("register")
    object Home : Screen("home")
    object Chooser : Screen("chooser")
    object Profile: Screen("profile")
    object Matchmaking: Screen("matchmaking")
    object Survey: Screen("survey")
    object Search: Screen("search/{categoryIdx}"){
        fun createRoute(categoryIdx: Int) = "search/$categoryIdx"
    }
}