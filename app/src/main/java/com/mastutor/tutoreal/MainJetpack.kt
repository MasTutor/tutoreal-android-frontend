package com.mastutor.tutoreal

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.ui.screen.chooser.ChooserScreen
import com.mastutor.tutoreal.ui.screen.login.LoginScreen
import com.mastutor.tutoreal.ui.screen.register.RegisterScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainJetpack(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
){
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold {innerPadding ->
        NavHost(navController = navHostController, startDestination = Screen.Chooser.route, modifier = Modifier.padding(innerPadding)){
            composable(Screen.Chooser.route){
                ChooserScreen(onLoginClicked = { navHostController.navigate(Screen.Login.route)}, onRegisterClicked = {navHostController.navigate(Screen.Register.route)})
            }
            composable(Screen.Login.route){
                LoginScreen()
            }
            composable(Screen.Register.route){
                RegisterScreen()
            }
        }

    }
}