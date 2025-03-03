package com.jabulani.ligiopen.ui.inapp.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.jabulani.ligiopen.AppViewModelFactory
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.data.network.model.match.fixture.FixtureData
import com.jabulani.ligiopen.data.network.model.match.fixture.MatchStatus
import com.jabulani.ligiopen.data.network.model.match.fixture.fixtures
import com.jabulani.ligiopen.ui.inapp.news.NewsTile
import com.jabulani.ligiopen.ui.inapp.news.news
import com.jabulani.ligiopen.ui.inapp.news.newsItem
import com.jabulani.ligiopen.ui.nav.AppNavigation
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.reusables.composables.AutoVideoPlayer
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth

object HomeScreenDestination : AppNavigation {
    override val title: String = "Home screen"
    override val route: String = "home-screen"

}

@Composable
fun HomeScreenComposable(
    fixtures: List<FixtureData>,
    navigateToPostMatchScreen: (postMatchId: String, fixtureId: String, locationId: String) -> Unit,
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
            fixtures = fixtures,
            navigateToPostMatchScreen = navigateToPostMatchScreen
        )

    }
}

@Composable
fun HomeScreen(
    fixtures: List<FixtureData>,
    navigateToPostMatchScreen: (postMatchId: String, fixtureId: String, locationId: String) -> Unit,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = screenHeight(x = 16.0),
                horizontal = screenWidth(x = 16.0)
            )
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Matches",
                fontSize = screenFontSize(x = 18.0).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { /*TODO*/ }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "All matches",
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                    Spacer(modifier = Modifier.width(screenWidth(4.0)))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "All matches",
                        modifier = Modifier
//                            .size(screenWidth(x = 48.0))
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
        ) {
            fixtures.forEach { fixture ->
                FixtureCell(
                    fixtureData = fixture,
                    navigateToPostMatchScreen = navigateToPostMatchScreen,
                    modifier = Modifier
                        .width(screenWidth * 0.7f)
                )
                Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
            }
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Watch",
                fontSize = screenFontSize(x = 18.0).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { /*TODO*/ }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "All videos",
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                    Spacer(modifier = Modifier.width(screenWidth(4.0)))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "All videos",
                        modifier = Modifier
//                            .size(screenWidth(x = 48.0))
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier
                .fillMaxWidth() // Ensure the card fills the width
                .aspectRatio(16 / 9f) // Maintain a good aspect ratio
        ) {
            VideoScreen(
                link = "https://drive.google.com/uc?export=download&id=1jDrbU6Xl-Gnmg0jGWvduwvm7ihUq9Zq9",
                modifier = Modifier
                    .fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "CECAFA: Kenya Goal",
                fontWeight = FontWeight.Bold,
                fontSize = screenFontSize(x = 16.0).sp
            )
            Spacer(modifier = Modifier.weight(1f))
            TextButton(onClick = { /*TODO*/ }) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Watch",
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                    Icon(
                        painter = painterResource(id = R.drawable.play),
                        contentDescription = "Watch video"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        Text(
            text = "News",
            fontSize = screenFontSize(x = 18.0).sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        NewsTile(
            newsItem = newsItem,
            fullScreen = true,
            modifier = Modifier
        )
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        news.take(4).forEach { newsItem ->
            NewsTile(
                newsItem = newsItem,
                modifier = Modifier
            )
            Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        }

    }
}

@Composable
fun VideoScreen(
    link: String,
    modifier: Modifier = Modifier
) {
    Box() {
        AutoVideoPlayer(
            videoUri = link
        )
    }
}

@Composable
fun FixtureCell(
    fixtureData: FixtureData,
    navigateToPostMatchScreen: (postMatchId: String, fixtureId: String, locationId: String) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .border(
                width = screenWidth(x = 1.0),
                shape = RoundedCornerShape(screenWidth(x = 8.0)),
                color = Color.LightGray
            )
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    navigateToPostMatchScreen(
                        fixtureData.postMatchAnalysisId.toString(),
                        fixtureData.matchFixtureId.toString(),
                        fixtureData.matchLocationId.toString()
                    )
                }
                .padding(screenWidth(x = 16.0))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                    .weight(1f)
                ) {
                    Row {
                        AsyncImage(
                            model = fixtureData.homeClub.clubLogo.link,
                            contentDescription = fixtureData.homeClub.name,
                            modifier = Modifier
                                .size(screenWidth(x = 24.0))
                                .clip(RoundedCornerShape(screenWidth(x = 8.0))),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                        Column() {
                            Text(
                                text = fixtureData.homeClub.clubAbbreviation ?: "${fixtureData.homeClub.name.take(3)} FC",
                                fontSize = screenFontSize(x = 16.0).sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                            Text(
                                text = "HOME",
                                fontSize = screenFontSize(x = 10.0).sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "VS",
                        fontSize = screenFontSize(x = 16.0).sp,
                        fontWeight = FontWeight.Bold
                    )
                    if(fixtureData.matchStatus != MatchStatus.CANCELLED && fixtureData.matchStatus != MatchStatus.PENDING) {
                        Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = fixtureData.homeClubScore!!.toString(),
                                fontSize = screenFontSize(x = 14.0).sp
                            )
                            Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                            Text(
                                text = ":",
                                fontSize = screenFontSize(x = 14.0).sp
                            )
                            Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                            Text(
                                text = fixtureData.awayClubScore!!.toString(),
                                fontSize = screenFontSize(x = 14.0).sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))

                Box(
                    modifier = Modifier
                    .weight(1f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = fixtureData.awayClub.clubAbbreviation ?: "${fixtureData.awayClub.name.take(3)} FC",
                                fontSize = screenFontSize(x = 16.0).sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                            Text(
                                text = "AWAY",
                                fontSize = screenFontSize(x = 10.0).sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                        AsyncImage(
                            model = fixtureData.awayClub.clubLogo.link,
                            contentDescription = fixtureData.awayClub.name,
                            modifier = Modifier
                                .size(screenWidth(x = 24.0))
                                .clip(RoundedCornerShape(screenWidth(x = 8.0))),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }


            Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(screenWidth(x = 4.0)),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val matchStatusColor = when (fixtureData.matchStatus) {
                    MatchStatus.PENDING -> Color(0xFF9E9E9E) // Gray - Neutral for pending matches
                    MatchStatus.COMPLETED -> Color(0xFF4CAF50) // Green - Indicates match completion
                    MatchStatus.CANCELLED -> Color(0xFFF44336) // Red - Signifies cancellation
                    MatchStatus.FIRST_HALF -> Color(0xFFFFC107) // Amber - Active but early stage
                    MatchStatus.HALF_TIME -> Color(0xFFFF9800) // Orange - Signals break time
                    MatchStatus.SECOND_HALF -> Color(0xFFFFC107) // Amber - Ongoing second half
                    MatchStatus.EXTRA_TIME_FIRST_HALF -> Color(0xFFFF5722) // Deep Orange - More intensity
                    MatchStatus.EXTRA_TIME_HALF_TIME -> Color(0xFFFF9800) // Orange - Half-time in extra time
                    MatchStatus.EXTRA_TIME_SECOND_HALF -> Color(0xFFFF5722) // Deep Orange - More intensity
                    MatchStatus.PENALTY_SHOOTOUT -> Color(0xFF673AB7) // Purple - Indicates a high-stakes moment
                }


                val matchStatusIcon = when (fixtureData.matchStatus) {
                    MatchStatus.PENDING -> R.drawable.clock // Clock icon
                    MatchStatus.COMPLETED -> R.drawable.check_mark // Checkmark icon
                    MatchStatus.CANCELLED -> R.drawable.close // Cross icon
                    MatchStatus.FIRST_HALF -> R.drawable.ball
                    MatchStatus.HALF_TIME -> R.drawable.half_time
                    MatchStatus.SECOND_HALF -> R.drawable.ball
                    MatchStatus.EXTRA_TIME_FIRST_HALF -> R.drawable.ball
                    MatchStatus.EXTRA_TIME_HALF_TIME -> R.drawable.half_time
                    MatchStatus.EXTRA_TIME_SECOND_HALF -> R.drawable.ball
                    MatchStatus.PENALTY_SHOOTOUT -> R.drawable.ball
                }

                Icon(
                    painter = painterResource(id = matchStatusIcon),
                    contentDescription = fixtureData.matchStatus.name,
                    tint = matchStatusColor,
                    modifier = Modifier.size(screenWidth(x = 16.0))
                )

                Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                Text(
                    text = fixtureData.matchStatus.name,
                    color = matchStatusColor,
                    fontSize = screenFontSize(x = 14.0).sp,
                    fontWeight = FontWeight.Bold
                )

            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    LigiopenTheme {
        HomeScreen(
            fixtures = fixtures,
            navigateToPostMatchScreen = { _, _, _ -> }
        )
    }
}