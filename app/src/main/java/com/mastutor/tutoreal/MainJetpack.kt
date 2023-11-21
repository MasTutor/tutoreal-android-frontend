package com.mastutor.tutoreal

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.ui.screen.chooser.ChooserScreen
import com.mastutor.tutoreal.ui.screen.home.HomeScreen
import com.mastutor.tutoreal.ui.screen.login.LoginScreen
import com.mastutor.tutoreal.ui.screen.matchmaking.MatchmakingOnboardingScreen
import com.mastutor.tutoreal.ui.screen.profile.ProfileScreen
import com.mastutor.tutoreal.ui.screen.register.RegisterScreen
import com.mastutor.tutoreal.ui.screen.survey.SurveyScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainJetpack(
    modifier: Modifier = Modifier,
    navHostController: NavHostController = rememberNavController()
){
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold {innerPadding ->
        NavHost(navController = navHostController, startDestination = Screen.Chooser.route, modifier = Modifier.padding(
            //for edge to edge
            top = if(currentRoute == Screen.Home.route || currentRoute == Screen.Matchmaking.route) 0.dp else innerPadding.calculateTopPadding(),
            bottom = if(currentRoute == Screen.Chooser.route || currentRoute == Screen.Matchmaking.route) 0.dp else innerPadding.calculateBottomPadding()

        )){
            composable(Screen.Chooser.route){
                ChooserScreen(onLoginClicked = { navHostController.navigate(Screen.Login.route)}, onRegisterClicked = {navHostController.navigate(Screen.Register.route)}, navHostController = navHostController)
            }
            composable(Screen.Login.route){
                LoginScreen(navHostController = navHostController)
            }
            composable(Screen.Register.route){
                RegisterScreen()
            }
            composable(Screen.Home.route){
                HomeScreen(navHostController = navHostController)
            }
            composable(Screen.Profile.route){
                ProfileScreen(navHostController = navHostController)
            }
            composable(Screen.Matchmaking.route){
                MatchmakingOnboardingScreen(
                    onBackClicked = { /*TODO*/ },
                    onNextClicked = { navHostController.navigate(Screen.Survey.route) },
                    navHostController = navHostController
                )
            }
            composable(Screen.Survey.route){
                SurveyScreen()
            }
        }

    }
}