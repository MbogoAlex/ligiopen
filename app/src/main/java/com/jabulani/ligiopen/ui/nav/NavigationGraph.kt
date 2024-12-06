package com.jabulani.ligiopen.ui.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.jabulani.ligiopen.ui.auth.login.LoginScreenComposable
import com.jabulani.ligiopen.ui.auth.login.LoginScreenDestination
import com.jabulani.ligiopen.ui.auth.registration.RegistrationScreenComposable
import com.jabulani.ligiopen.ui.auth.registration.RegistrationScreenDestination
import com.jabulani.ligiopen.ui.inapp.clubs.ClubDetailsScreenComposable
import com.jabulani.ligiopen.ui.inapp.clubs.ClubDetailsScreenDestination
import com.jabulani.ligiopen.ui.inapp.fixtures.FixtureDetailsScreenComposable
import com.jabulani.ligiopen.ui.inapp.fixtures.FixtureDetailsScreenDestination
import com.jabulani.ligiopen.ui.inapp.home.HomeScreenComposable
import com.jabulani.ligiopen.ui.inapp.home.HomeScreenDestination
import com.jabulani.ligiopen.ui.inapp.news.NewsDetailsScreenComposable
import com.jabulani.ligiopen.ui.inapp.news.NewsDetailsScreenDestination
import com.jabulani.ligiopen.ui.inapp.playedMatches.HighlightsScreenComposable
import com.jabulani.ligiopen.ui.inapp.playedMatches.HighlightsScreenDestination
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
                navigateToHomeScreen = {
                    navController.navigate(HomeScreenDestination.route)
                },
                navigateToLoginScreen = {
                    navController.navigate(LoginScreenDestination.route)
                }
            )
        }
        composable(RegistrationScreenDestination.route) {
            RegistrationScreenComposable(
                navigateToLoginScreen = {
                    navController.navigate(LoginScreenDestination.route)
                },
                navigateToLoginScreenWithArgs = {email, password ->
                    navController.navigate("${LoginScreenDestination.route}/$email/$password")
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
        composable(
            LoginScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(LoginScreenDestination.email) {
                    type = NavType.StringType
                },
                navArgument(LoginScreenDestination.password) {
                    type = NavType.StringType
                }

            )
        ) {
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
            HomeScreenComposable(
                navigateToNewsDetailsScreen = {
                    navController.navigate(NewsDetailsScreenDestination.route)
                },
                navigateToClubDetailsScreen = {
                    navController.navigate(ClubDetailsScreenDestination.route)
                },
                navigateToFixtureDetailsScreen = {
                    navController.navigate(FixtureDetailsScreenDestination.route)
                },
                navigateToHighlightsScreen = {
                    navController.navigate(HighlightsScreenDestination.route)
                }
            )
        }
        composable(HighlightsScreenDestination.route) {
            HighlightsScreenComposable(
                navigateToPreviousScreen = {
                    navController.navigateUp()
                }
            )
        }
        composable(NewsDetailsScreenDestination.route) {
            NewsDetailsScreenComposable(
                navigateToPreviousScreen = {
                    navController.navigateUp()
                }
            )
        }
        composable(ClubDetailsScreenDestination.route) {
            ClubDetailsScreenComposable(
                navigateToNewsDetailsScreen = {
                    navController.navigate(NewsDetailsScreenDestination.route)
                },
                navigateToFixtureDetailsScreen = {
                    navController.navigate(FixtureDetailsScreenDestination.route)
                },
                navigateToPreviousScreen = {
                    navController.navigateUp()
                }
            )
        }
        composable(FixtureDetailsScreenDestination.route) {
            FixtureDetailsScreenComposable(
                navigateToPreviousScreen = {
                    navController.navigateUp()
                }
            )
        }
    }
}