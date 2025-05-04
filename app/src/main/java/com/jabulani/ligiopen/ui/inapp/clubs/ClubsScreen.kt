package com.jabulani.ligiopen.ui.inapp.clubs

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.jabulani.ligiopen.AppViewModelFactory
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.data.network.model.club.ClubDetails
import com.jabulani.ligiopen.ui.inapp.home.HomeScreenTab
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.ui.theme.backgroundLight
import com.jabulani.ligiopen.ui.theme.onPrimaryLight
import com.jabulani.ligiopen.ui.theme.onSurfaceLight
import com.jabulani.ligiopen.ui.theme.onTertiaryLight
import com.jabulani.ligiopen.ui.theme.primaryLight
import com.jabulani.ligiopen.ui.theme.surfaceContainerHighLight
import com.jabulani.ligiopen.ui.theme.surfaceLight
import com.jabulani.ligiopen.ui.theme.surfaceVariantLight
import com.jabulani.ligiopen.ui.theme.tertiaryLight
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
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    // Using theme colors
    val primaryColor = primaryLight
    val surfaceColor = surfaceContainerHighLight
    val onSurfaceColor = onSurfaceLight
    val accentColor = tertiaryLight

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with logo and title
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            primaryColor.copy(alpha = 0.8f),
                            primaryColor.copy(alpha = 0.4f)
                        )
                    )
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.ligiopen_icon),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(2.dp, accentColor, RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = HomeScreenTab.CLUBS.name
                    .lowercase()
                    .replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.headlineMedium,
                color = onPrimaryLight,
                fontWeight = FontWeight.Black,
                letterSpacing = 1.sp,
                modifier = Modifier
                    .shadow(4.dp, shape = RoundedCornerShape(4.dp))
            )

            Spacer(modifier = Modifier.weight(1f))

            IconButton(
                onClick = { /* Filter or sort action */ },
                modifier = Modifier
                    .size(40.dp)
                    .background(accentColor, CircleShape)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = "Filter",
                    tint = onTertiaryLight
                )
            }
        }

        // Main content area
        when (loadingStatus) {
            LoadingStatus.LOADING -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(64.dp),
                        strokeWidth = 4.dp,
                        color = accentColor
                    )
                }
            }
            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(clubs) { club ->
                        ClubCard(
                            club = club,
                            onClick = { navigateToClubDetailsScreen(club.clubId.toString()) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ClubCard(
    club: ClubDetails,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(16.dp)
    val primaryColor = primaryLight
    val accentColor = tertiaryLight
    val backgroundColor = backgroundLight
    val surfaceColor = surfaceLight
    val onSurfaceColor = onSurfaceLight
    val surfaceVariantColor = surfaceVariantLight

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = shape,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp,
            pressedElevation = 4.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = surfaceContainerHighLight
        )
    ) {
        Column {
            // Club banner image with overlay
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.2f)
            ) {
                // Club photo
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(club.clubMainPhoto?.link)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Club banner",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    primaryLight.copy(alpha = 0.7f)
                                ),
                                startY = 0.6f
                            )
                        )
                )

                // Club logo badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .size(48.dp)
                        .background(
                            color = surfaceContainerHighLight,
                            shape = CircleShape
                        )
                        .border(
                            width = 2.dp,
                            color = accentColor,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(club.clubLogo.link)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Club logo",
                        modifier = Modifier.size(36.dp)
                    )
                }
            }

            // Club info
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = club.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = onSurfaceColor,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Additional club info could go here
                // For example: location, founded year, etc.
            }

            // Glowing accent at bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(accentColor, primaryColor)
                        )
                    )
            )
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