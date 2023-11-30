package com.mastutor.tutoreal.ui.navigation.screen

sealed class Screen(val route: String){
    object Login : Screen("login")
    object RegisterForm : Screen("registerForm")
    object RegisterPicture : Screen("registerPicture")
    object Home : Screen("home")
    object Chooser : Screen("chooser")
    object Profile: Screen("profile")
    object Tutor : Screen("tutor/{tutorId}") {
        fun createRoute(tutorId: String) = "tutor/$tutorId"
    }
    object Matchmaking: Screen("matchmaking")
    object MatchmakingResult : Screen("matchmakingResult")
    object Survey: Screen("survey")
    object Search: Screen("search/{categoryIdx}"){
        fun createRoute(categoryIdx: Int) = "search/$categoryIdx"
    }
}