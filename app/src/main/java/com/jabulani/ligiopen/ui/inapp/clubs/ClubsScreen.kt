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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
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

    val viewModel: ClubsViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .safeDrawingPadding()
    ) {
        ClubsScreen(
            clubs = uiState.clubs,
            loadingStatus = uiState.loadingStatus,
            navigateToClubDetailsScreen = navigateToClubDetailsScreen
        )
    }
}

@Composable
fun ClubsScreen(
    clubs: List<ClubDetails>,
    navigateToClubDetailsScreen: (clubId: String) -> Unit,
    loadingStatus: LoadingStatus,
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
        if(loadingStatus == LoadingStatus.LOADING) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(clubs) { club ->
                    Column(
                        modifier = Modifier
                            .clickable {
                                navigateToClubDetailsScreen(club.clubId.toString())
                            }
                            .padding(screenHeight(x = 8.0))
                            .fillMaxWidth()
                    ) {
                        ClubItemTile(
                            clubName = club.name,
                            clubLogo = club.clubLogo.link,
                            clubPhoto = club.clubMainPhoto?.link,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun ClubItemTile(
    clubName: String,
    clubLogo: String?, // URL or null
    clubPhoto: String?,
    modifier: Modifier = Modifier
) {
    Card {
        Column(
            modifier = modifier
        ) {
            val painter = rememberAsyncImagePainter(
                model = clubLogo ?: R.drawable.club_logo, // Fallback to local drawable if URL is null
                placeholder = painterResource(id = R.drawable.club_logo),
                error = painterResource(id = R.drawable.club_logo)
            )

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(clubPhoto)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop,
                contentDescription = "Club main photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = screenHeight(x = 180.0))

            )
            Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(
                        vertical = screenHeight(x = 8.0),
                        horizontal = screenWidth(x = 8.0)
                    )
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(clubLogo)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.ic_broken_image),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Club main photo",
                    modifier = Modifier
                        .size(screenWidth(x = 24.0))
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.size(screenWidth(x = 8.0)))

                Text(
                    text = clubName,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = screenFontSize(x = 16.0).sp,
                    fontWeight = FontWeight.Bold
                )
            }

        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ClubsScreenPreview() {
    LigiopenTheme {
        ClubsScreen(
            clubs = emptyList(),
            loadingStatus = LoadingStatus.INITIAL,
            navigateToClubDetailsScreen = {}
        )
    }
}