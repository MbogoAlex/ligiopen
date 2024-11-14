package com.jabulani.ligiopen.ui.inapp.clubs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.inapp.fixtures.FixtureItemCell
import com.jabulani.ligiopen.ui.inapp.news.NewsTile
import com.jabulani.ligiopen.ui.inapp.news.newsItem
import com.jabulani.ligiopen.ui.inapp.scores.ScoreItemCell
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth

@Composable
fun ClubDetailsScreen(
    clubName: String,
    modifier: Modifier = Modifier
) {
    val tabs = listOf(
        "Overview",
        "News",
        "Fixtures",
        "Scores",
        "Donations"
    )

    var selectedTab by rememberSaveable {
        mutableStateOf("Overview")
    }

    var selectedTabWidth by rememberSaveable {
        mutableDoubleStateOf(0.0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
//                vertical = screenHeight(x = 16.0),
//                horizontal = screenWidth(x = 16.0)
            )
    ) {
        ElevatedCard {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = screenWidth(x = 8.0)
                    )
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Previous screen"
                    )
                }
                Image(
                    painter = painterResource(id = R.drawable.club_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .size(screenWidth(x = 24.0))
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = clubName.takeIf { it.length < 15 } ?: (clubName.take(15) + "..."),
                    fontWeight = FontWeight.Bold,
                    fontSize = screenFontSize(x = 14.0).sp
                )
            }
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())

        ) {
            tabs.forEach { tab ->
                Card(
                    shape = RoundedCornerShape(0),
                    colors = CardDefaults.cardColors(
                        containerColor = if(selectedTab == tab) MaterialTheme.colorScheme.surfaceTint else Color.Transparent

                    ),
                    border = BorderStroke(
                        width = screenWidth(x = 1.0),
                        color = if(selectedTab == tab) MaterialTheme.colorScheme.surfaceTint else Color.Black
                    ),
                    onClick = {
                        selectedTab = tab
                    },
                    modifier = Modifier
                        .padding(
                            horizontal = screenWidth(x = 2.0)
                        )
                ) {
                    Text(
                        text = tab,
                        fontWeight = if(selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                        modifier = Modifier
                            .padding(
                                vertical = screenHeight(x = 8.0),
                                horizontal = screenWidth(x = 16.0)

                            )
                    )
                }
            }
        }
//        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        when(selectedTab) {
            "Overview" -> ClubOverviewScreen()
            "News" -> ClubNewsScreen()
            "Fixtures" -> ClubFixturesScreen(
                modifier = Modifier
                    .padding(
                        vertical = screenHeight(x = 16.0),
                        horizontal = screenWidth(x = 16.0)
                    )
            )
            "Scores" -> ClubScoresScreen()
            "Donations" -> ClubDonationsScreen()
        }
    }
}

@Composable
fun ClubOverviewScreen() {
    val rows = listOf(1, 2, 3, 4, 5)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Image(
            painter = painterResource(id = R.drawable.football),
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
                text = "OveralClub FC",
                fontSize = screenFontSize(x = 16.0).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
            Text(
                text = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout.",
                fontSize = screenFontSize(x = 14.0).sp
            )
            Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
            Text(
                text = "Players",
                fontSize = screenFontSize(x = 16.0).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
            rows.forEach {
                PlayerCell(
                    modifier = Modifier
                        .padding(
                            vertical = screenHeight(x = 8.0)
                        )
                )
            }
        }
    }
}

@Composable
fun PlayerCell(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.soccer_player),
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
                    text = "Name: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = screenFontSize(x = 14.0).sp
                )
                Text(
                    text = "Mark Oloo",
                    fontSize = screenFontSize(x = 14.0).sp
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Age: ",
                    fontWeight = FontWeight.Bold,
                    fontSize = screenFontSize(x = 14.0).sp
                )
                Text(
                    text = "28 years",
                    fontSize = screenFontSize(x = 14.0).sp
                )
            }
            Text(
                text = "Goal keeper",
                fontSize = screenFontSize(x = 14.0).sp
            )
        }
    }
}


@Composable
fun ClubNewsScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
//                vertical = screenHeight(x = 16.0),
//                horizontal = screenWidth(x = 16.0)
            )
    ) {
        LazyColumn {
            items(10) {index ->
                NewsTile(
                    newsItem = newsItem,
                    modifier = Modifier
                        .padding(
                            top = screenHeight(x = 16.0)
                        )
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ClubFixturesScreen(
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(10) {
            FixtureItemCell(
                modifier = Modifier
                    .padding(
                        top = screenHeight(x = 8.0),
                        bottom = screenHeight(x = 8.0)
                    )
            )
            Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
        }
    }
}

@Composable
fun ClubScoresScreen() {
    Column(
        modifier = Modifier
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
                            top = screenHeight(x = 8.0),
                            bottom = screenHeight(x = 8.0)
                        )
                )
            }
        }
    }
}

@Composable
fun ClubDonationsScreen() {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClubDetailsScreenPreview() {
    LigiopenTheme {
        ClubDetailsScreen(
            clubName = "OveralClub FC"
        )
    }
}