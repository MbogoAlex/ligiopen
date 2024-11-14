package com.jabulani.ligiopen.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.jabulani.ligiopen.ui.auth.login.LoginScreenComposable
import com.jabulani.ligiopen.ui.auth.login.LoginScreenDestination
import com.jabulani.ligiopen.ui.auth.registration.RegistrationScreen
import com.jabulani.ligiopen.ui.auth.registration.RegistrationScreenComposable
import com.jabulani.ligiopen.ui.auth.registration.RegistrationScreenDestination
import com.jabulani.ligiopen.ui.inapp.home.HomeScreenComposable
import com.jabulani.ligiopen.ui.inapp.home.HomeScreenDestination
import com.jabulani.ligiopen.ui.start.SplashScreenComposable
import com.jabulani.ligiopen.ui.start.SplashScreenDestination

@Composable
fun NavigationGraph(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination.route,
    ) {
        composable(SplashScreenDestination.route) {
            SplashScreenComposable(
                navigateToRegistrationScreen = {
                    navController.navigate(RegistrationScreenDestination.route)
                }
            )
        }
        composable(RegistrationScreenDestination.route) {
            RegistrationScreenComposable(
                navigateToLoginScreen = {
                    navController.navigate(LoginScreenDestination.route)
                }
            )
        }
        composable(LoginScreenDestination.route) {
            LoginScreenComposable(
                navigateToHomeScreen = {
                    navController.navigate(HomeScreenDestination.route)
                },
                navigateToRegistrationScreen = {
                    navController.navigate(RegistrationScreenDestination.route)
                }
            )
        }
        composable(HomeScreenDestination.route) {
            HomeScreenComposable()
        }
    }
}