package com.jabulani.ligiopen.ui.inapp.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jabulani.ligiopen.AppViewModelFactory
import com.jabulani.ligiopen.data.network.model.match.fixture.FixtureData
import com.jabulani.ligiopen.data.network.model.match.fixture.fixtures
import com.jabulani.ligiopen.ui.nav.AppNavigation
import com.jabulani.ligiopen.ui.theme.LigiopenTheme

object HomeScreenDestination : AppNavigation {
    override val title: String = "Home screen"
    override val route: String = "home-screen"

}

@Composable
fun HomeScreenComposable(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()


    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        HomeScreen(
            fixtures = fixtures
        )

    }
}

@Composable
fun HomeScreen(
    fixtures: List<FixtureData>,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    LigiopenTheme {
        HomeScreen(
            fixtures = fixtures
        )
    }
}