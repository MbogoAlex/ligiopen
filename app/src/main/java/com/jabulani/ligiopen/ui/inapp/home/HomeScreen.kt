package com.jabulani.ligiopen.ui.inapp.home

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.inapp.clubs.ClubsScreenComposable
import com.jabulani.ligiopen.ui.inapp.fixtures.FixturesScreenComposable
import com.jabulani.ligiopen.ui.inapp.news.NewsScreenComposable
import com.jabulani.ligiopen.ui.inapp.scores.ScoresScreenComposable
import com.jabulani.ligiopen.ui.nav.AppNavigation
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenWidth

object HomeScreenDestination : AppNavigation {
    override val title: String = "Home screen"
    override val route: String = "home-screen"

}

@Composable
fun HomeScreenComposable(
    navigateToNewsDetailsScreen: () -> Unit,
    navigateToClubDetailsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as Activity
    BackHandler(onBack = {
        activity.finish()
    })
    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        HomeScreen(
            navigateToNewsDetailsScreen = navigateToNewsDetailsScreen,
            navigateToClubDetailsScreen = navigateToClubDetailsScreen
        )
    }
}

@Composable
fun HomeScreen(
    navigateToNewsDetailsScreen: () -> Unit,
    navigateToClubDetailsScreen: () -> Unit,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    val tabs = listOf(
        HomeScreenTabItem(
            name = "News",
            icon = R.drawable.news,
            tab = HomeScreenTab.NEWS
        ),
        HomeScreenTabItem(
            name = "Scores",
            icon = R.drawable.scores,
            tab = HomeScreenTab.SCORES
        ),
        HomeScreenTabItem(
            name = "Clubs",
            icon = R.drawable.football_club,
            tab = HomeScreenTab.CLUBS
        ),
        HomeScreenTabItem(
            name = "Fixtures",
            icon = R.drawable.fixtures,
            tab = HomeScreenTab.FIXTURES
        ),
    )

    var currentTab by rememberSaveable {
        mutableStateOf(HomeScreenTab.NEWS)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(0),
        ) {
            Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(screenWidth(x = 8.0))

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ligiopen_icon),
                    contentDescription = null,
                )
                Spacer(modifier = Modifier.width(screenWidth(x = 3.0)))
                Text(
                    text = "Ligi Open / ${currentTab.name}",
                    fontSize = screenFontSize(x = 14.0).sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "My account"
                    )
                }
            }
        }

        when(currentTab) {
            HomeScreenTab.NEWS -> NewsScreenComposable(
                navigateToNewsDetailsScreen = navigateToNewsDetailsScreen,
                modifier = Modifier
                    .weight(1f)
            )
            HomeScreenTab.SCORES -> ScoresScreenComposable(
                modifier = Modifier
                    .weight(1f)
            )
            HomeScreenTab.CLUBS -> ClubsScreenComposable(
                navigateToClubDetailsScreen = navigateToClubDetailsScreen,
                modifier = Modifier
                    .weight(1f)
            )
            HomeScreenTab.FIXTURES -> {
                FixturesScreenComposable(
                    modifier = Modifier
                        .weight(1f)
                )
            }
            HomeScreenTab.FINANCING -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Text(text = "Financing")
                }
            }
        }
        HomeBottomNavBar(
            selectedTab = currentTab,
            tabs = tabs,
            onChangeTab = {
                currentTab = it
            }
        )
    }
}

@Composable
fun HomeBottomNavBar(
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
                selected = tab.name == selectedTab.name,
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

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    LigiopenTheme {
        HomeScreen(
            navigateToNewsDetailsScreen = {},
            navigateToClubDetailsScreen = {}
        )
    }
}