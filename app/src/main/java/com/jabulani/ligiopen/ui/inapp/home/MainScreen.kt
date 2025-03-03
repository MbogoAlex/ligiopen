package com.jabulani.ligiopen.ui.inapp.home

import android.widget.Toast
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jabulani.ligiopen.AppViewModelFactory
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.inapp.clubs.ClubsScreenComposable
import com.jabulani.ligiopen.ui.inapp.fixtures.FixturesScreenComposable
import com.jabulani.ligiopen.ui.inapp.news.NewsScreenComposable
import com.jabulani.ligiopen.ui.inapp.profile.ProfileScreenComposable
import com.jabulani.ligiopen.ui.inapp.standings.StandingsScreenComposable
import com.jabulani.ligiopen.ui.nav.AppNavigation
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenWidth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object MainScreenDestination : AppNavigation {
    override val title: String = "Home screen"
    override val route: String = "home-screen"

}

@Composable
fun MainScreenComposable(
    onSwitchTheme: () -> Unit,
    navigateToNewsDetailsScreen: () -> Unit,
    navigateToClubDetailsScreen: (clubId: String) -> Unit,
    navigateToFixtureDetailsScreen: () -> Unit,
    navigateToHighlightsScreen: () -> Unit,
    navigateToLoginScreenWithArgs: (email: String, password: String) -> Unit,
    navigateToPostMatchScreen: (postMatchId: String, fixtureId: String, locationId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    val scope = rememberCoroutineScope()

    val tabs = listOf(
        HomeScreenTabItem(
            name = "Home",
            icon = R.drawable.home,
            tab = HomeScreenTab.HOME
        ),
        HomeScreenTabItem(
            name = "Matches",
            icon = R.drawable.matches,
            tab = HomeScreenTab.MATCHES
        ),
        HomeScreenTabItem(
            name = "Clubs",
            icon = R.drawable.football_club,
            tab = HomeScreenTab.CLUBS
        ),
        HomeScreenTabItem(
            name = "News",
            icon = R.drawable.news,
            tab = HomeScreenTab.NEWS
        ),
        HomeScreenTabItem(
            name = "Standings",
            icon = R.drawable.standings,
            tab = HomeScreenTab.STANDINGS
        ),
    )

    var currentTab by rememberSaveable {
        mutableStateOf(HomeScreenTab.HOME)
    }

    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        MainScreen(
            darkMode = uiState.userAccount.darkMode,
            onSwitchTheme = {
                scope.launch {
                    onSwitchTheme()
                    delay(1000)
                    drawerState.close()
                    Toast.makeText(context, "Theme switched", Toast.LENGTH_SHORT).show()
                }
            },
            username = uiState.userAccount.username,
            scope = scope,
            drawerState = drawerState,
            currentTab = currentTab,
            onChangeTab = {
                currentTab = it
            },
            tabs = tabs,
            navigateToNewsDetailsScreen = navigateToNewsDetailsScreen,
            navigateToClubDetailsScreen = navigateToClubDetailsScreen,
            navigateToFixtureDetailsScreen = navigateToFixtureDetailsScreen,
            navigateToHighlightsScreen = navigateToHighlightsScreen,
            navigateToLoginScreenWithArgs = navigateToLoginScreenWithArgs,
            navigateToPostMatchScreen = navigateToPostMatchScreen
        )

    }
}

@Composable
fun MainScreen(
    username: String,
    darkMode: Boolean,
    onSwitchTheme: () -> Unit,
    scope: CoroutineScope?,
    drawerState: DrawerState?,
    currentTab: HomeScreenTab,
    onChangeTab: (tab: HomeScreenTab) -> Unit,
    tabs: List<HomeScreenTabItem>,
    navigateToNewsDetailsScreen: () -> Unit,
    navigateToClubDetailsScreen: (clubId: String) -> Unit,
    navigateToFixtureDetailsScreen: () -> Unit,
    navigateToHighlightsScreen: () -> Unit,
    navigateToLoginScreenWithArgs: (email: String, password: String) -> Unit,
    navigateToPostMatchScreen: (postMatchId: String, fixtureId: String, locationId: String) -> Unit,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {

    Scaffold(
        bottomBar = {
            NavigationBar {
                for(tab in tabs) {
                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(id = tab.icon),
                                contentDescription = tab.name
                            )
                        },
                        label = {
                            Text(text = tab.name)
                        },
                        selected = currentTab == tab.tab,
                        onClick = {
                            onChangeTab(tab.tab)
                        }
                    )
                }
            }
        },
        modifier = Modifier
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = screenWidth(x = 8.0)
                        )

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ligiopen_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth(x = 56.0))
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                    Text(
                        text = currentTab.name.lowercase().replaceFirstChar { first -> first.uppercase() },
                        fontSize = screenFontSize(x = 26.0).sp,
                        fontWeight = FontWeight.W900
                    )
                    Spacer(modifier = Modifier.weight(1f))
//                    Text(
//                        text = username.take(8),
//                        fontSize = screenFontSize(x = 14.0).sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                    Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                    IconButton(onClick = {
                        onChangeTab(HomeScreenTab.PROFILE)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.more),
                            contentDescription = "More"
                        )
                    }
                }

                when(currentTab) {
                    HomeScreenTab.NEWS -> NewsScreenComposable(
                        navigateToNewsDetailsScreen = navigateToNewsDetailsScreen,
                        modifier = Modifier

                    )
                    HomeScreenTab.SCORES -> {}
                    HomeScreenTab.CLUBS -> ClubsScreenComposable(
                        switchToHomeTab = {
                            onChangeTab(HomeScreenTab.NEWS)
                        },
                        navigateToClubDetailsScreen = navigateToClubDetailsScreen,
                        modifier = Modifier

                    )
                    HomeScreenTab.MATCHES -> {
                        FixturesScreenComposable(
                            navigateToPostMatchScreen = navigateToPostMatchScreen,
                            navigateToLoginScreenWithArgs = navigateToLoginScreenWithArgs,
                            modifier = Modifier
                        )
                    }
                    HomeScreenTab.FINANCING -> {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .fillMaxWidth()

                        ) {
                            Text(text = "Financing")
                        }
                    }
                    HomeScreenTab.PROFILE -> {
                        ProfileScreenComposable(
                            navigateToHomeScreen = {
                                onChangeTab(HomeScreenTab.NEWS)
                            },
                            navigateToLoginScreenWithArgs = navigateToLoginScreenWithArgs,
                            modifier = Modifier

                        )
                    }

                    HomeScreenTab.HOME -> {
                        HomeScreenComposable()
                    }
                    HomeScreenTab.STANDINGS -> {
                        StandingsScreenComposable()
                    }
                }
            }
        }
    }

}

@Composable
fun MainBottomNavBar(
    selectedTab: HomeScreenTab,
    tabs: List<HomeScreenTabItem>,
    onChangeTab: (tab: HomeScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    NavigationBar {
        for(tab in tabs) {
            NavigationBarItem(
                label = {
                    Text(
                        text = tab.name,
                        fontSize = screenFontSize(x = 10.0).sp
                    )
                },
                selected = tab.tab == selectedTab,
                onClick = {
                    onChangeTab(tab.tab)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = tab.icon),
                        contentDescription = tab.name
                    )
                }
            )
        }
    }
}

@Composable
fun ThemeSwitcher(
    darkTheme: Boolean = false,
    size: Dp = screenWidth(x = 150.0),
    iconSize: Dp = size / 3,
    padding: Dp = screenWidth(x = 10.0),
    borderWidth: Dp = screenWidth(x = 1.0),
    parentShape: Shape = CircleShape,
    toggleShape: Shape = CircleShape,
    animationSpec: AnimationSpec<Dp> = tween(durationMillis = 300),
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val offset by animateDpAsState(
        targetValue = if (darkTheme) 0.dp else size,
        animationSpec = animationSpec
    )

    Box(modifier = modifier
        .width(size * 2)
        .height(size)
        .clip(shape = parentShape)
        .clickable { onClick() }
        .background(MaterialTheme.colorScheme.secondaryContainer)
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = toggleShape)
                .background(MaterialTheme.colorScheme.primary)
        ) {}
        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    shape = parentShape
                )
        ) {
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    tint = if (darkTheme) MaterialTheme.colorScheme.secondaryContainer
                    else MaterialTheme.colorScheme.primary,
                    painter = painterResource(id = R.drawable.dark_mode),
                    contentDescription = "Theme icon",
                    modifier = Modifier.size(iconSize),
                )
            }
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    tint = if (darkTheme) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.secondaryContainer,
                    painter = painterResource(id = R.drawable.light_mode),
                    contentDescription = "Theme icon",
                    modifier = Modifier.size(iconSize),
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    LigiopenTheme {
        MainScreen(
            darkMode = false,
            onSwitchTheme = {},
            username = "Sam N",
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
            currentTab = HomeScreenTab.NEWS,
            onChangeTab = {},
            tabs = listOf(),
            scope = null,
            navigateToNewsDetailsScreen = {},
            navigateToClubDetailsScreen = {},
            navigateToFixtureDetailsScreen = {},
            navigateToHighlightsScreen = {},
            navigateToLoginScreenWithArgs = {email, password ->},
            navigateToPostMatchScreen = {postMatchId, fixtureId, locationId ->}
        )
    }
}