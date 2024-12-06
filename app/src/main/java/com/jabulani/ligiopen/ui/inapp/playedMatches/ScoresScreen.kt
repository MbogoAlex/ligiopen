package com.jabulani.ligiopen.ui.inapp.playedMatches

import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth

@Composable
fun ScoresScreenComposable(
    switchToHomeTab: () -> Unit,
    navigateToHighlightsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = switchToHomeTab)
    Box(
        modifier = modifier
    ) {
        ScoresScreen(
            navigateToHighlightsScreen = navigateToHighlightsScreen
        )
    }
}

@Composable
fun ScoresScreen(
    navigateToHighlightsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background,)
            .fillMaxSize()
            .padding(
                horizontal = screenWidth(x = 16.0),
                vertical = screenHeight(x = 16.0)
            )
    ) {
        LazyColumn {
            items(10) {
                ScoreItemCell(
                    modifier = Modifier
                        .padding(
                            start = screenWidth(x = 4.0),
                            end = screenWidth(x = 4.0),
                            top = screenHeight(x = 8.0),
                            bottom = screenHeight(x = 8.0)
                        )
                        .clickable {
                            navigateToHighlightsScreen()
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
fun ScoreItemCell(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
        ) {
            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "Aug. 20, 2024 11:40am",
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        tint = MaterialTheme.colorScheme.onBackground,
                        painter = painterResource(id = R.drawable.location),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 3.0)))
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "Kisumu stadium",
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                }
                Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onBackground,
                        painter = painterResource(id = R.drawable.football_club),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 3.0)))
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "OveralClub FC",
                        fontWeight = FontWeight.Bold,
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "1",
                        fontWeight = FontWeight.Bold,
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                }
                Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Icon(
                        tint = MaterialTheme.colorScheme.onBackground,
                        painter = painterResource(id = R.drawable.football_club),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 3.0)))
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "Obunga FC",
                        fontWeight = FontWeight.Bold,
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "0",
                        fontWeight = FontWeight.Bold,
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScoresScreenPreview() {
    LigiopenTheme {
        ScoresScreen(
            navigateToHighlightsScreen = {}
        )
    }
}