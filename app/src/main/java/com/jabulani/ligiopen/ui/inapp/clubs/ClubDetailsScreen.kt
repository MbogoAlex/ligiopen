package com.jabulani.ligiopen.ui.inapp.clubs

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.jabulani.ligiopen.AppViewModelFactory
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.data.network.model.club.ClubDetails
import com.jabulani.ligiopen.data.network.model.club.club
import com.jabulani.ligiopen.data.network.model.player.PlayerDetails
import com.jabulani.ligiopen.ui.inapp.fixtures.fixtureDetails.FixtureItemCell
import com.jabulani.ligiopen.ui.inapp.news.NewsTile
import com.jabulani.ligiopen.ui.inapp.news.newsItem
import com.jabulani.ligiopen.ui.nav.AppNavigation
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth

object ClubDetailsScreenDestination : AppNavigation {
    override val title: String = "Club details screen"
    override val route: String = "club-details-screen"
    val clubId: String = "clubId"
    val routeWithArgs: String = "$route/{$clubId}"
}

@Composable
fun ClubDetailsScreenComposable(
    navigateToFixtureDetailsScreen: () -> Unit,
    navigateToNewsDetailsScreen: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
    navigateToPlayerDetailsScreen: (playerId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as Activity
    BackHandler(onBack = navigateToPreviousScreen)

    val viewModel: ClubDetailsViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    val tabs = listOf(
        ClubScreenTabItem(
            name = "Overview",
            icon = R.drawable.info,
            tab = ClubScreenTab.INFO
        ),
        ClubScreenTabItem(
            name = "News",
            icon = R.drawable.news,
            tab = ClubScreenTab.NEWS
        ),
        ClubScreenTabItem(
            name = "Fixtures",
            icon = R.drawable.fixtures,
            tab = ClubScreenTab.FIXTURES
        ),
        ClubScreenTabItem(
            name = "Scores",
            icon = R.drawable.scores,
            tab = ClubScreenTab.SCORES
        ),
    )

    var selectedTab by rememberSaveable {
        mutableStateOf(ClubScreenTab.INFO)
    }

    ElevatedCard(
        shape = RoundedCornerShape(0.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .safeDrawingPadding()
        ) {
            ClubDetailsScreen(
                clubDetails = uiState.clubDetails,
                tabs = tabs,
                onChangeTab = {
                    selectedTab = it
                },
                selectedTab = selectedTab,
                navigateToNewsDetailsScreen = navigateToNewsDetailsScreen,
                navigateToPreviousScreen = navigateToPreviousScreen,
                navigateToFixtureDetailsScreen = navigateToFixtureDetailsScreen,
                navigateToPlayerDetailsScreen = navigateToPlayerDetailsScreen
            )
        }
    }

}

@Composable
fun ClubDetailsScreen(
    clubDetails: ClubDetails,
    tabs: List<ClubScreenTabItem>,
    onChangeTab: (tab: ClubScreenTab) -> Unit,
    selectedTab: ClubScreenTab,
    navigateToFixtureDetailsScreen: () -> Unit,
    navigateToNewsDetailsScreen: () -> Unit,
    navigateToPreviousScreen: () -> Unit,
    navigateToPlayerDetailsScreen: (playerId: String) -> Unit,
    modifier: Modifier = Modifier
) {


    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(
//                vertical = screenHeight(x = 16.0),
//                horizontal = screenWidth(x = 16.0)
            )
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(0.dp)
        ) {
            Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(screenWidth(x = 8.0))

            ) {
                IconButton(onClick = navigateToPreviousScreen) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Previous screen"
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ligiopen_icon),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(screenWidth(x = 3.0)))
                Text(
                    text = selectedTab.name,
                    fontSize = screenFontSize(x = 16.0).sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.club_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(screenWidth(x = 24.0))
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                Text(
                    text = clubDetails.name.takeIf { it.length < 15 } ?: (clubDetails.name.take(15) + "..."),
                    fontWeight = FontWeight.Bold,
                    fontSize = screenFontSize(x = 14.0).sp
                )
            }

        }
//        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
//        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        when(selectedTab) {
            ClubScreenTab.INFO -> {
                ClubOverviewScreen(
                    clubDetails = clubDetails,
                    navigateToPlayerDetailsScreen = navigateToPlayerDetailsScreen,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            ClubScreenTab.NEWS -> {
                ClubNewsScreen(
                    navigateToNewsDetailsScreen = navigateToNewsDetailsScreen,
                    modifier = Modifier
                        .weight(1f)
                )
            }
            ClubScreenTab.FIXTURES -> {
                ClubFixturesScreen(
                    navigateToFixtureDetailsScreen = navigateToFixtureDetailsScreen,
                    modifier = Modifier
                        .padding(
                            vertical = screenHeight(x = 16.0),
                            horizontal = screenWidth(x = 16.0)
                        )
                        .weight(1f)
                )
            }
            ClubScreenTab.SCORES -> {
                ClubScoresScreen(
                    navigateToFixtureDetailsScreen = navigateToFixtureDetailsScreen,
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
        ClubDetailsScreenBottomBar(
            tabs = tabs,
            currentTab = selectedTab,
            onChangeTab = onChangeTab
        )
    }
}

@Composable
fun ClubOverviewScreen(
    clubDetails: ClubDetails,
    navigateToPlayerDetailsScreen: (playerId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val rows = listOf(1, 2, 3, 4, 5)

    val painter = rememberAsyncImagePainter(
        model = clubDetails.clubMainPhoto ?: R.drawable.no_photo
        , // Fallback to local drawable if URL is null
        placeholder = painterResource(id = R.drawable.loading),
        error = painterResource(id = R.drawable.broken_image)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
        )
        Column(
            modifier = Modifier
                .padding(
                    vertical = screenHeight(x = 16.0),
                    horizontal = screenWidth(x = 16.0)
                )
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = clubDetails.name,
                fontSize = screenFontSize(x = 16.0).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = clubDetails.description,
                fontSize = screenFontSize(x = 14.0).sp
            )
            Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = "Players",
                fontSize = screenFontSize(x = 16.0).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
            clubDetails.players.forEach { player ->
                PlayerCell(
                    playerDetails = player,
                    modifier = Modifier
                        .padding(
                            vertical = screenHeight(x = 8.0)
                        )
                        .clickable {
                            navigateToPlayerDetailsScreen(player.playerId.toString())
                        }
                )
            }
        }
    }
}

@Composable
fun PlayerCell(
    playerDetails: PlayerDetails,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        model = playerDetails.mainPhoto ?: R.drawable.no_photo
        , // Fallback to local drawable if URL is null
        placeholder = painterResource(id = R.drawable.loading),
        error = painterResource(id = R.drawable.broken_image)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(screenWidth(x = 72.0))
        )
//                Spacer(modifier = Modifier.weight(1f))
        Column(
//                    horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "Name: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = screenFontSize(x = 14.0).sp
                )
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = playerDetails.username,
                    fontSize = screenFontSize(x = 14.0).sp
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "Age: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = screenFontSize(x = 14.0).sp
                )
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "${playerDetails.age} years",
                    fontSize = screenFontSize(x = 14.0).sp
                )
            }
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = playerDetails.playerPosition.name.lowercase().replaceFirstChar { it.uppercase() },
                fontSize = screenFontSize(x = 14.0).sp
            )
        }
    }
}


@Composable
fun ClubNewsScreen(
    navigateToNewsDetailsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
//                vertical = screenHeight(x = 16.0),
//                horizontal = screenWidth(x = 16.0)
            )
    ) {
        LazyColumn {
            items(10) {index ->
                Column(
                    modifier = Modifier
                        .clickable {
                            navigateToNewsDetailsScreen()
                        }
                ) {
                    NewsTile(
                        newsItem = newsItem,
                        modifier = Modifier
//                            .padding(
//                                top = screenHeight(x = 8.0)
//                            )
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun ClubFixturesScreen(
    navigateToFixtureDetailsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(10) {
            Column(
                modifier = Modifier
                    .clickable {
                        navigateToFixtureDetailsScreen()
                    }
            ) {
                FixtureItemCell(
                    navigateToFixtureDetailsScreen = navigateToFixtureDetailsScreen,
                    modifier = Modifier
                        .padding(
                            start = screenWidth(x = 4.0),
                            end = screenWidth(x = 4.0),
//                            top = screenHeight(x = 8.0),
                            bottom = screenHeight(x = 8.0)
                        )
                        .clickable {
                            navigateToFixtureDetailsScreen()
                        }
                )
                Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
            }
        }
    }
}

@Composable
fun ClubScoresScreen(
    navigateToFixtureDetailsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
                horizontal = screenWidth(x = 20.0),
                vertical = screenHeight(x = 10.0)
            )
    ) {
        LazyColumn {
            items(10) {
                Text(text = "Sasa")
                Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
            }
        }
    }
}

@Composable
fun ClubDonationsScreen() {

}

@Composable
fun ClubDetailsScreenBottomBar(
    tabs: List<ClubScreenTabItem>,
    currentTab: ClubScreenTab,
    onChangeTab: (tab: ClubScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar {
        for(tab in tabs) {
            NavigationBarItem(
                label = {
                    Text(
                        text = tab.name,
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                },
                selected = currentTab == tab.tab,
                onClick = {
                    onChangeTab(tab.tab)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = tab.icon),
                        contentDescription = tab.name,
                        modifier = Modifier
                            .size(screenWidth(x = 20.0))
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClubDetailsScreenPreview() {

    val tabs = listOf(
        ClubScreenTabItem(
            name = "Overview",
            icon = R.drawable.info,
            tab = ClubScreenTab.INFO
        ),
        ClubScreenTabItem(
            name = "News",
            icon = R.drawable.news,
            tab = ClubScreenTab.NEWS
        ),
        ClubScreenTabItem(
            name = "Fixtures",
            icon = R.drawable.fixtures,
            tab = ClubScreenTab.FIXTURES
        ),
        ClubScreenTabItem(
            name = "Scores",
            icon = R.drawable.scores,
            tab = ClubScreenTab.FIXTURES
        ),
    )

    var selectedTab by rememberSaveable {
        mutableStateOf(ClubScreenTab.INFO)
    }

    LigiopenTheme {
        ClubDetailsScreen(
            clubDetails = club,
            tabs = tabs,
            onChangeTab = {
                selectedTab = it
            },
            selectedTab = selectedTab,
            navigateToFixtureDetailsScreen = { /*TODO*/ },
            navigateToNewsDetailsScreen = { /*TODO*/ },
            navigateToPreviousScreen = { /*TODO*/ },
            navigateToPlayerDetailsScreen = {}
        )
    }
}