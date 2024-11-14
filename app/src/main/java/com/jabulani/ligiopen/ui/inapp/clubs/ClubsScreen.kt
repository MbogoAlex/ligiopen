package com.jabulani.ligiopen.ui.inapp.clubs

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth

@Composable
fun ClubsScreenComposable(
    navigateToClubDetailsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .safeDrawingPadding()
    ) {
        ClubsScreen(
            navigateToClubDetailsScreen = navigateToClubDetailsScreen
        )
    }
}

@Composable
fun ClubsScreen(
    navigateToClubDetailsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
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
                ClubItemTile(
                    modifier = Modifier
                        .padding(
                            top = screenHeight(x = 8.0),
                            bottom = screenHeight(x = 8.0)
                        )
                        .clickable {
                            navigateToClubDetailsScreen()
                        }
                        .fillMaxWidth()
                )
                HorizontalDivider()
            }
        }
    }
}

@Composable
fun ClubItemTile(
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.club_logo),
            contentDescription = null,
            modifier = Modifier
                .size(screenWidth(x = 44.0))
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.size(screenWidth(x = 16.0)))
        Text(
            text = "OveralClub FC",
            fontSize = screenFontSize(x = 14.0).sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClubsScreenPreview() {
    LigiopenTheme {
        ClubsScreen(
            navigateToClubDetailsScreen = {}
        )
    }
}