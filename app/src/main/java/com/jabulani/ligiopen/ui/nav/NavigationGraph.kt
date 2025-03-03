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
import com.jabulani.ligiopen.ui.inapp.clubs.PlayerDetailsScreenComposable
import com.jabulani.ligiopen.ui.inapp.clubs.PlayerDetailsScreenDestination
import com.jabulani.ligiopen.ui.inapp.fixtures.fixtureDetails.FixtureDetailsScreenComposable
import com.jabulani.ligiopen.ui.inapp.fixtures.fixtureDetails.FixtureDetailsScreenDestination
import com.jabulani.ligiopen.ui.inapp.fixtures.fixtureDetails.HighlightsScreenComposable
import com.jabulani.ligiopen.ui.inapp.fixtures.fixtureDetails.HighlightsScreenDestination
import com.jabulani.ligiopen.ui.inapp.home.HomeScreenComposable
import com.jabulani.ligiopen.ui.inapp.home.HomeScreenDestination
import com.jabulani.ligiopen.ui.inapp.home.MainScreenComposable
import com.jabulani.ligiopen.ui.inapp.home.MainScreenDestination
import com.jabulani.ligiopen.ui.inapp.news.NewsDetailsScreenComposable
import com.jabulani.ligiopen.ui.inapp.news.NewsDetailsScreenDestination
import com.jabulani.ligiopen.ui.start.SplashScreenComposable
import com.jabulani.ligiopen.ui.start.SplashScreenDestination

@Composable
fun NavigationGraph(
    navController: NavHostController,
    onSwitchTheme: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = SplashScreenDestination.route,
    ) {
        composable(SplashScreenDestination.route) {
            SplashScreenComposable(
                navigateToMainScreen = {
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
                navigateToMainScreen = {
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
                navigateToMainScreen = {
                    navController.navigate(MainScreenDestination.route)
                },
                navigateToRegistrationScreen = {
                    navController.navigate(RegistrationScreenDestination.route)
                }
            )
        }
        composable(MainScreenDestination.route) {
            MainScreenComposable(
                onSwitchTheme = onSwitchTheme,
                navigateToNewsDetailsScreen = {
                    navController.navigate(NewsDetailsScreenDestination.route)
                },
                navigateToClubDetailsScreen = {
                    navController.navigate("${ClubDetailsScreenDestination.route}/${it}")
                },
                navigateToFixtureDetailsScreen = {
                    navController.navigate(FixtureDetailsScreenDestination.route)
                },
                navigateToHighlightsScreen = {
                    navController.navigate(HighlightsScreenDestination.route)
                },
                navigateToLoginScreenWithArgs = {email, password ->
                    navController.navigate("${LoginScreenDestination.route}/$email/$password")
                },
                navigateToPostMatchScreen = {postMatchId, fixtureId, locationId ->
                    navController.navigate("${HighlightsScreenDestination.route}/${postMatchId}/${fixtureId}/${locationId}")
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
                },
                navigateToPlayerDetailsScreen = {
                    navController.navigate("${PlayerDetailsScreenDestination.route}/${it}")
                },
                navigateToLoginScreenWithArgs = {email, password ->
                    navController.navigate("${LoginScreenDestination.route}/$email/$password")
                },
                navigateToPostMatchScreen = {postMatchId, fixtureId, locationId ->
                    navController.navigate("${HighlightsScreenDestination.route}/${postMatchId}/${fixtureId}/${locationId}")
                }
            )
        }
        composable(
            ClubDetailsScreenDestination.routeWithArgs,
            arguments = listOf(
                navArgument(ClubDetailsScreenDestination.clubId) {
                    type = NavType.StringType
                }
            )
        ) {
            ClubDetailsScreenComposable(
                navigateToNewsDetailsScreen = {
                    navController.navigate(NewsDetailsScreenDestination.route)
                },
                navigateToFixtureDetailsScreen = {
                    navController.navigate(FixtureDetailsScreenDestination.route)
                },
                navigateToPreviousScreen = {
                    navController.navigateUp()
                },
                navigateToPlayerDetailsScreen = {
                    navController.navigate("${PlayerDetailsScreenDestination.route}/${it}")
                },
                navigateToLoginScreenWithArgs = {email, password ->
                    navController.navigate("${LoginScreenDestination.route}/$email/$password")
                },
                navigateToPostMatchScreen = {postMatchId, fixtureId, locationId ->
                    navController.navigate("${HighlightsScreenDestination.route}/${postMatchId}/${fixtureId}/${locationId}")
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

        composable(
            PlayerDetailsScreenDestination.routeWithPlayerId,
            arguments = listOf(
                navArgument(PlayerDetailsScreenDestination.playerId) {
                    type = NavType.StringType
                }
            )
        ) {
            PlayerDetailsScreenComposable(
                navigateToPreviousScreen = { navController.navigateUp() }
            )
        }
        composable(
            HighlightsScreenDestination.routeWithPostMatchIdAndFixtureIdAndLocationId,
            arguments = listOf(
                navArgument(HighlightsScreenDestination.postMatchId) {
                    type = NavType.StringType
                },
                navArgument(HighlightsScreenDestination.fixtureId) {
                    type = NavType.StringType
                },
                navArgument(HighlightsScreenDestination.locationId) {
                    type = NavType.StringType
                }
            )
        ) {
            HighlightsScreenComposable(
                navigateToPreviousScreen = { navController.navigateUp() }
            )
        }
    }
}