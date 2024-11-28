package com.jabulani.ligiopen.ui.inapp.playedMatches

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.inapp.playedMatches.summary.MatchSummaryComposable
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenWidth

@Composable
fun HighlightsScreenComposable(
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        HighlightsScreenTabItem(
            name = "Summary",
            tab = HighlightsScreenTabs.SUMMARY,
            icon = R.drawable.ball_summary
        ),
        HighlightsScreenTabItem(
            name = "Timeline",
            tab = HighlightsScreenTabs.TIMELINE,
            icon = R.drawable.timeline
        ),
        HighlightsScreenTabItem(
            name = "Lineups",
            tab = HighlightsScreenTabs.LINEUPS,
            icon = R.drawable.lineup
        ),
        HighlightsScreenTabItem(
            name = "Stats",
            tab = HighlightsScreenTabs.STATS,
            icon = R.drawable.stats
        ),
    )
    var currentTab by rememberSaveable {
        mutableStateOf(HighlightsScreenTabs.SUMMARY)
    }
    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {

    }
}

@Composable
fun HighlightsScreen(
    tabs: List<HighlightsScreenTabItem>,
    currentTab: HighlightsScreenTabs,
    onChangeTab: (tab: HighlightsScreenTabs) -> Unit,
    modifier: Modifier = Modifier
) {
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
                IconButton(onClick = { /*TODO*/ }) {
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
                    text = currentTab.name,
                    fontSize = screenFontSize(x = 16.0).sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.club1),
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth(x = 24.0))
                    )
                    Text(
                        text = "1",
                        fontSize = screenFontSize(x = 16.0).sp
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                    Text(
                        text = ":",
                        fontSize = screenFontSize(x = 16.0).sp
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                    Text(
                        text = "0",
                        fontSize = screenFontSize(x = 16.0).sp
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                    Image(
                        painter = painterResource(id = R.drawable.club2),
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth(x = 24.0))
                    )
                }
            }
            when(currentTab) {
                HighlightsScreenTabs.SUMMARY -> {
                    MatchSummaryComposable(
                        modifier = Modifier
                            .weight(1f)
                    )
                }
                HighlightsScreenTabs.TIMELINE -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        Text(text = "Timeline")
                    }
                }
                HighlightsScreenTabs.LINEUPS -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        Text(text = "Lineups")
                    }
                }
                HighlightsScreenTabs.STATS -> {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                    ) {
                        Text(text = "Stats")
                    }
                }
            }
            HighlightScreenBottomBar(
                tabs = tabs,
                currentTab = currentTab,
                onChangeTab = onChangeTab
            )
        }
    }
}

@Composable
fun HighlightScreenBottomBar(
    tabs: List<HighlightsScreenTabItem>,
    currentTab: HighlightsScreenTabs,
    onChangeTab: (tab: HighlightsScreenTabs) -> Unit,
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
                        contentDescription = tab.name
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HighlightsScreenPreview() {
    val tabs = listOf(
        HighlightsScreenTabItem(
            name = "Summary",
            tab = HighlightsScreenTabs.SUMMARY,
            icon = R.drawable.ball_summary
        ),
        HighlightsScreenTabItem(
            name = "Timeline",
            tab = HighlightsScreenTabs.TIMELINE,
            icon = R.drawable.timeline
        ),
        HighlightsScreenTabItem(
            name = "Lineups",
            tab = HighlightsScreenTabs.LINEUPS,
            icon = R.drawable.lineup
        ),
        HighlightsScreenTabItem(
            name = "Stats",
            tab = HighlightsScreenTabs.STATS,
            icon = R.drawable.stats
        ),
    )
    var currentTab by rememberSaveable {
        mutableStateOf(HighlightsScreenTabs.SUMMARY)
    }
    LigiopenTheme {
        HighlightsScreen(
            tabs = tabs,
            currentTab = HighlightsScreenTabs.SUMMARY,
            onChangeTab = {
                currentTab = it
            }
        )
    }
}