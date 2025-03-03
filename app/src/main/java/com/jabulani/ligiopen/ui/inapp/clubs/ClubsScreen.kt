package com.jabulani.ligiopen.ui.inapp.clubs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.jabulani.ligiopen.AppViewModelFactory
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.data.network.model.club.ClubDetails
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth

@Composable
fun ClubsScreenComposable(
    switchToHomeTab: () -> Unit,
    navigateToClubDetailsScreen: (clubId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = switchToHomeTab)

    val viewModel: ClubsViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .safeDrawingPadding()
    ) {
        ClubsScreen(
            clubs = uiState.clubs,
            navigateToClubDetailsScreen = navigateToClubDetailsScreen
        )
    }
}

@Composable
fun ClubsScreen(
    clubs: List<ClubDetails>,
    navigateToClubDetailsScreen: (clubId: String) -> Unit,
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
            items(clubs) {club ->
                Column(
                    modifier = Modifier
                        .clickable {
                            navigateToClubDetailsScreen(club.clubId.toString())
                        }
                ) {
                    ClubItemTile(
                        clubName = club.name,
                        clubLogo = club.clubLogo.link,
                        modifier = Modifier
                            .padding(
                                top = screenHeight(x = 8.0),
                                bottom = screenHeight(x = 8.0)
                            )
                            .fillMaxWidth()
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun ClubItemTile(
    clubName: String,
    clubLogo: String?, // URL or null
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        val painter = rememberAsyncImagePainter(
            model = clubLogo ?: R.drawable.club_logo, // Fallback to local drawable if URL is null
            placeholder = painterResource(id = R.drawable.club_logo),
            error = painterResource(id = R.drawable.club_logo)
        )

        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(screenWidth(x = 44.0))
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.size(screenWidth(x = 16.0)))

        Text(
            text = clubName,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = screenFontSize(x = 14.0).sp
        )
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClubsScreenPreview() {
    LigiopenTheme {
        ClubsScreen(
            clubs = emptyList(),
            navigateToClubDetailsScreen = {}
        )
    }
}