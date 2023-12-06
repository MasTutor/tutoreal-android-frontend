package com.mastutor.tutoreal

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.ui.screen.booking.BookingScreen
import com.mastutor.tutoreal.ui.screen.chooser.ChooserScreen
import com.mastutor.tutoreal.ui.screen.home.HomeScreen
import com.mastutor.tutoreal.ui.screen.login.LoginScreen
import com.mastutor.tutoreal.ui.screen.matchmaking.MatchmakingOnboardingScreen
import com.mastutor.tutoreal.ui.screen.profile.ProfileScreen
import com.mastutor.tutoreal.ui.screen.register.RegisterPictureScreen
import com.mastutor.tutoreal.ui.screen.register.RegisterScreen
import com.mastutor.tutoreal.ui.screen.schedule.ScheduleScreen
import com.mastutor.tutoreal.ui.screen.search.SearchScreen
import com.mastutor.tutoreal.ui.screen.survey.SurveyScreen
import com.mastutor.tutoreal.ui.screen.tutor.TutorScreen
import com.mastutor.tutoreal.viewmodel.AuthViewModel
import com.mastutor.tutoreal.viewmodel.TutorViewModel

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
            top = if(currentRoute == Screen.Home.route || currentRoute == Screen.Matchmaking.route || currentRoute == Screen.Search.route) 0.dp else innerPadding.calculateTopPadding(),
            bottom = if(currentRoute == Screen.Chooser.route || currentRoute == Screen.Matchmaking.route) 0.dp else innerPadding.calculateBottomPadding()

        )){
            composable(Screen.Chooser.route){
                ChooserScreen(onLoginClicked = { navHostController.navigate(Screen.Login.route)}, onRegisterClicked = {navHostController.navigate("register")}, navHostController = navHostController)
            }
            composable(Screen.Login.route){
                LoginScreen(navHostController = navHostController)
            }
            registerGraph(navHostController)
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
            composable(Screen.Schedule.route){
                ScheduleScreen()
            }
            composable(Screen.Survey.route){
                SurveyScreen()
            }
            composable(route = Screen.Search.route, arguments = listOf(navArgument("categoryIdx"){type = NavType.IntType}),){
                val categoryIdx = it.arguments?.getInt("categoryIdx") ?: 0
                SearchScreen(
                    categoryIdx = categoryIdx,
                    onBackClicked = {
                        navHostController.navigateUp()
                    },
                    moveToTutorDetail = { id ->
                        navHostController.navigate(Screen.Tutor.createRoute(id))
                    }
                )
            }
            tutorGraph(navHostController)
        }
    }
}

fun NavGraphBuilder.registerGraph(navController: NavHostController) {
    navigation(startDestination = Screen.RegisterForm.route, route = "register") {
        composable(Screen.RegisterForm.route) {backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("register")
            }
            val viewModel = hiltViewModel<AuthViewModel>(parentEntry)
            RegisterScreen(viewModel = viewModel, navHostController = navController)
        }
        composable(Screen.RegisterPicture.route) {backStackEntry ->
            val parentEntry = remember(backStackEntry) {
                navController.getBackStackEntry("register")
            }
            val viewModel = hiltViewModel<AuthViewModel>(parentEntry)
            RegisterPictureScreen(viewModel = viewModel, navHostController = navController)
        }
    }
}

fun NavGraphBuilder.tutorGraph(navController: NavHostController) {
    navigation(startDestination = Screen.Tutor.route, route = "tutor") {
        composable(
            route = Screen.Tutor.route,
            arguments = listOf(navArgument("tutorId") { type = NavType.StringType }),
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry("tutor")
            }
            val viewModel = hiltViewModel<TutorViewModel>(parentEntry)
            val id = it.arguments?.getString("tutorId") ?: ""
            TutorScreen(
                id = id,
                navHostController = navController,
                moveToBookScreen = {
                    navController.navigate(Screen.Book.route)
                },
                viewModel = viewModel
            )
        }

        composable(
            route = Screen.Book.route
        ) {
            val parentEntry = remember(it) {
                navController.getBackStackEntry("tutor")
            }
            val viewModel = hiltViewModel<TutorViewModel>(parentEntry)
            BookingScreen(modifier = Modifier,
                viewModel = viewModel,
                navHostController = navController
            )
        }

    }
}