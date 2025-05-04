package com.jabulani.ligiopen.ui.inapp.home

import YouTubePlayer
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jabulani.ligiopen.AppViewModelFactory
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.data.network.model.match.fixture.FixtureData
import com.jabulani.ligiopen.data.network.model.match.fixture.MatchStatus
import com.jabulani.ligiopen.data.network.model.match.fixture.fixtures
import com.jabulani.ligiopen.data.network.model.news.NewsDto
import com.jabulani.ligiopen.data.network.model.news.news
import com.jabulani.ligiopen.ui.inapp.news.NewsTile
import com.jabulani.ligiopen.ui.inapp.videos.SingleVideoCard
import com.jabulani.ligiopen.ui.inapp.videos.config.YoutubeVideo
import com.jabulani.ligiopen.ui.inapp.videos.config.youtubeVideo
import com.jabulani.ligiopen.ui.inapp.videos.config.youtubeVideos

import com.jabulani.ligiopen.ui.nav.AppNavigation
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.ui.theme.errorLight
import com.jabulani.ligiopen.ui.theme.onErrorLight
import com.jabulani.ligiopen.ui.theme.onPrimaryLight
import com.jabulani.ligiopen.ui.theme.onSurfaceLight
import com.jabulani.ligiopen.ui.theme.onSurfaceVariantLight
import com.jabulani.ligiopen.ui.theme.onTertiaryLight
import com.jabulani.ligiopen.ui.theme.primaryLight
import com.jabulani.ligiopen.ui.theme.surfaceContainerHighLight
import com.jabulani.ligiopen.ui.theme.surfaceVariantLight
import com.jabulani.ligiopen.ui.theme.tertiaryLight
import com.jabulani.ligiopen.ui.utils.formatIsoDateTime
import com.jabulani.ligiopen.ui.utils.video.FullscreenVideoScreen
import com.jabulani.ligiopen.utils.reusables.composables.AutoVideoPlayer
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth
import java.time.LocalDateTime

object HomeScreenDestination : AppNavigation {
    override val title: String = "Home screen"
    override val route: String = "home-screen"

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenComposable(
    fixtures: List<FixtureData>,
    news: List<NewsDto>,
    navigateToPostMatchScreen: (postMatchId: String, fixtureId: String, locationId: String) -> Unit,
    navigateToAllVideosScreen: () -> Unit,
    navigateToSingleVideoScreen: (videoId: String, videoTitle: String, videoDate: String) -> Unit,
    navigateToNewsDetailsScreen: (newsId: String) -> Unit,
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
            news = news,
            navigateToPostMatchScreen = navigateToPostMatchScreen,
            navigateToSingleVideoScreen = navigateToSingleVideoScreen,
            navigateToAllVideosScreen = navigateToAllVideosScreen,
            navigateToNewsDetailsScreen = navigateToNewsDetailsScreen
        )

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreen(
    fixtures: List<FixtureData>,
    news: List<NewsDto>,
    navigateToPostMatchScreen: (postMatchId: String, fixtureId: String, locationId: String) -> Unit,
    navigateToAllVideosScreen: () -> Unit,
    navigateToSingleVideoScreen: (videoId: String, videoTitle: String, videoDate: String) -> Unit,
    navigateToNewsDetailsScreen: (newsId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    // Theme colors
    val primaryColor = primaryLight
    val surfaceColor = surfaceContainerHighLight
    val onSurfaceColor = onSurfaceLight
    val accentColor = tertiaryLight

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header with game-style gradient
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
                text = HomeScreenTab.HOME.name
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

            // Notification bell with badge
            Box {
                IconButton(
                    onClick = { /* Handle notifications */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(accentColor, CircleShape)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.notification),
                        contentDescription = "Notifications",
                        tint = onTertiaryLight
                    )
                }

                // Notification badge
//                Box(
//                    modifier = Modifier
//                        .align(Alignment.TopEnd)
//                        .size(16.dp)
//                        .background(errorLight, CircleShape)
//                        .border(2.dp, surfaceColor, CircleShape)
//                ) {
//                    Text(
//                        text = "3",
//                        color = onErrorLight,
//                        fontSize = 10.sp,
//                        fontWeight = FontWeight.Bold,
//                        modifier = Modifier.align(Alignment.Center)
//                    )
//                }
            }
        }

        // Main content area
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 8.dp)
        ) {
            // Upcoming Matches section
            SectionHeader(
                title = "Upcoming Matches",
                actionText = "View All",
                onActionClick = { /* TODO */ },
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (fixtures.isEmpty()) {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(3) { index ->
                        // Shimmer placeholder card
                        Card(
                            modifier = Modifier
                                .width(280.dp)
                                .height(180.dp),
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = surfaceVariantLight
                            ),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
//                                    .shimmerEffect()
                                    .padding(16.dp)
                            ) {
                                // Placeholder status
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(24.dp)
                                        .background(
                                            color = surfaceContainerHighLight,
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                )

                                Spacer(modifier = Modifier.height(12.dp))

                                // Placeholder teams and score
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // Home team placeholder
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(48.dp)
                                                .clip(CircleShape)
                                                .background(surfaceContainerHighLight)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Box(
                                            modifier = Modifier
                                                .width(60.dp)
                                                .height(16.dp)
                                                .background(surfaceContainerHighLight)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Box(
                                            modifier = Modifier
                                                .width(40.dp)
                                                .height(12.dp)
                                                .background(surfaceContainerHighLight)
                                        )
                                    }

                                    // Score placeholder
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(0.8f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .width(80.dp)
                                                .height(24.dp)
                                                .background(surfaceContainerHighLight)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Box(
                                            modifier = Modifier
                                                .width(120.dp)
                                                .height(14.dp)
                                                .background(surfaceContainerHighLight)
                                        )
                                    }

                                    // Away team placeholder
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(48.dp)
                                                .clip(CircleShape)
                                                .background(surfaceContainerHighLight)
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Box(
                                            modifier = Modifier
                                                .width(60.dp)
                                                .height(16.dp)
                                                .background(surfaceContainerHighLight)
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Box(
                                            modifier = Modifier
                                                .width(40.dp)
                                                .height(12.dp)
                                                .background(surfaceContainerHighLight)
                                        )
                                    }
                                }

                                // Glowing accent placeholder
                                Spacer(modifier = Modifier.weight(1f))
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(2.dp)
                                        .background(surfaceContainerHighLight)
                                )
                            }
                        }
                    }
                }
            } else {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp)
                ) {
                    items(fixtures) { fixture ->
                        FixtureCard(
                            fixtureData = fixture,
                            onClick = {
                                navigateToPostMatchScreen(
                                    fixture.postMatchAnalysisId.toString(),
                                    fixture.matchFixtureId.toString(),
                                    fixture.matchLocationId.toString()
                                )
                            },
                            modifier = Modifier.width(280.dp)
                        )
                    }
                }
            }



            Spacer(modifier = Modifier.height(24.dp))

            // Featured Video section
            SectionHeader(
                title = "Featured Video",
                actionText = "Watch",
                onActionClick = {
                    navigateToSingleVideoScreen(
                        youtubeVideo.videoId,
                        youtubeVideo.videoTitle,
                        youtubeVideo.videoDate
                    )
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Box {
                    YouTubePlayer(
                        youtubeVideoId = "mUki-_cLdsQ",
                        lifecycleOwner = LocalLifecycleOwner.current,
                        shouldMute = true,
                        shouldAutoPlay = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f/9f)
                    )

//                    // Play button overlay
//                    Box(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .background(Color.Black.copy(alpha = 0.3f))
//                    ) {
//                        Icon(
//                            painter = painterResource(id = R.drawable.play),
//                            contentDescription = "Play",
//                            tint = Color.White,
//                            modifier = Modifier
//                                .size(64.dp)
//                                .align(Alignment.Center)
//                        )
//                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = youtubeVideo.videoTitle,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = youtubeVideo.videoDate,
                color = onSurfaceVariantLight,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Video Highlights section
            SectionHeader(
                title = "Video Highlights",
                actionText = "View All",
                onActionClick = navigateToAllVideosScreen
            )

            Spacer(modifier = Modifier.height(8.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(youtubeVideos.take(5)) { video ->
                    VideoHighlightCard(
                        video = video,
                        onClick = {
                            navigateToSingleVideoScreen(
                                video.videoId,
                                video.videoTitle,
                                video.videoDate
                            )
                        },
                        modifier = Modifier.width(240.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Latest News section
            SectionHeader(
                title = "Latest News",
                actionText = "View All",
                onActionClick = { /* TODO */ }
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (news.isNotEmpty()) {
                // Featured news item
                NewsTile(
                    news = news[0],
                    isFeatured = true,
                    onClick = { navigateToNewsDetailsScreen(news[0].id.toString()) },
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Other news items
                news.drop(1).take(3).forEach { newsItem ->
                    NewsTile(
                        news = newsItem,
                        isFeatured = false,
                        onClick = { navigateToNewsDetailsScreen(newsItem.id.toString()) }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
//                LazyColumn(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalArrangement = Arrangement.spacedBy(16.dp),
//                    contentPadding = PaddingValues(horizontal = 8.dp)
//                ) {
//                    items(news.drop(1).take(3)) { newsItem ->
//                        NewsTile(
//                            news = newsItem,
//                            isFeatured = false,
//                            onClick = { navigateToNewsDetailsScreen(newsItem.id.toString()) }
//                        )
//                    }
//                }
            } else {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    Text(
                        text = "No news available",
                        color = onSurfaceVariantLight
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SectionHeader(
    title: String,
    actionText: String,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelMedium,
            color = primaryLight,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            onClick = onActionClick,
            colors = ButtonDefaults.textButtonColors(
                contentColor = primaryLight
            )
        ) {
            Text(text = actionText)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun VideoHighlightCard(
    video: YoutubeVideo,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryColor = primaryLight
    val surfaceColor = surfaceContainerHighLight
    val onSurfaceColor = onSurfaceLight
    val accentColor = tertiaryLight

    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            // Thumbnail container
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f/9f)
            ) {
                // Video thumbnail
                AsyncImage(
                    model = "https://img.youtube.com/vi/${video.videoId}/0.jpg",
                    contentDescription = video.videoTitle,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data("https://img.youtube.com/vi/${video.videoId}/maxresdefault.jpg") // YouTube thumbnail URL
//                        .crossfade(true)
//                        .build(),
//                    contentDescription = video.videoTitle,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.fillMaxSize()
//                )

                // Dark overlay for better play button visibility
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.2f))
                )

                // Play button overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.play2),
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center)
                            .shadow(4.dp)
                    )
                }

                // Duration badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.7f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = "2:45", // You might want to get actual duration from your data
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Video info
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                Text(
                    text = video.videoTitle,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.clock),
                        contentDescription = "Duration",
                        tint = onSurfaceVariantLight,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = video.videoDate,
                        color = onSurfaceVariantLight,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        painter = painterResource(id = R.drawable.play_views),
                        contentDescription = "Views",
                        tint = onSurfaceVariantLight,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "1.2K", // Example view count
                        color = onSurfaceVariantLight,
                        fontSize = 12.sp
                    )
                }
            }

            // Glowing accent at bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                primaryColor.copy(alpha = 0.3f),
                                primaryColor,
                                primaryColor.copy(alpha = 0.3f)
                            )
                        )
                    )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun FixtureCard(
    fixtureData: FixtureData,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryColor = primaryLight
    val surfaceColor = surfaceContainerHighLight
    val onSurfaceColor = onSurfaceLight
    val accentColor = tertiaryLight
    val matchStatusColor = when (fixtureData.matchStatus) {
        MatchStatus.PENDING -> Color(0xFF9E9E9E)
        MatchStatus.COMPLETED -> Color(0xFF4CAF50)
        MatchStatus.CANCELLED -> errorLight
        MatchStatus.FIRST_HALF -> Color(0xFFFFC107)
        MatchStatus.HALF_TIME -> Color(0xFFFF9800)
        MatchStatus.SECOND_HALF -> Color(0xFFFFC107)
        MatchStatus.EXTRA_TIME_FIRST_HALF -> Color(0xFFFF5722)
        MatchStatus.EXTRA_TIME_HALF_TIME -> Color(0xFFFF9800)
        MatchStatus.EXTRA_TIME_SECOND_HALF -> Color(0xFFFF5722)
        MatchStatus.PENALTY_SHOOTOUT -> Color(0xFF673AB7)
    }

    Card(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = surfaceContainerHighLight
        ),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Match status
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = matchStatusColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(4.dp)
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(
                        when (fixtureData.matchStatus) {
                            MatchStatus.PENDING -> R.drawable.clock
                            MatchStatus.COMPLETED -> R.drawable.check_mark
                            MatchStatus.CANCELLED -> R.drawable.close
                            else -> R.drawable.ball
                        }
                    ),
                    contentDescription = fixtureData.matchStatus.name,
                    tint = matchStatusColor,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = fixtureData.matchStatus.name
                        .lowercase()
                        .replace("_", " ")
                        .replaceFirstChar { it.uppercase() },
                    color = matchStatusColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Teams and score
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Home team
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    AsyncImage(
                        model = fixtureData.homeClub.clubLogo.link,
                        contentDescription = fixtureData.homeClub.name,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, primaryColor, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = fixtureData.homeClub.clubAbbreviation ?:
                        fixtureData.homeClub.name.take(3).uppercase(),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "HOME",
                        color = primaryColor.copy(alpha = 0.7f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Score or VS
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(0.8f)
                ) {
                    if (fixtureData.matchStatus == MatchStatus.COMPLETED ||
                        fixtureData.matchStatus == MatchStatus.FIRST_HALF ||
                        fixtureData.matchStatus == MatchStatus.SECOND_HALF ||
                        fixtureData.matchStatus == MatchStatus.EXTRA_TIME_FIRST_HALF ||
                        fixtureData.matchStatus == MatchStatus.EXTRA_TIME_SECOND_HALF ||
                        fixtureData.matchStatus == MatchStatus.PENALTY_SHOOTOUT) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = fixtureData.homeClubScore?.toString() ?: "0",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = " : ",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Black
                            )
                            Text(
                                text = fixtureData.awayClubScore?.toString() ?: "0",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Black
                            )
                        }
                    } else {
                        Text(
                            text = "VS",
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Black,
                            color = primaryColor
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Match time
                    Text(
                        text = formatIsoDateTime(LocalDateTime.parse(fixtureData.matchDateTime)),
                        color = onSurfaceVariantLight,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }

                // Away team
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    AsyncImage(
                        model = fixtureData.awayClub.clubLogo.link,
                        contentDescription = fixtureData.awayClub.name,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .border(2.dp, primaryColor, CircleShape)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = fixtureData.awayClub.clubAbbreviation ?:
                        fixtureData.awayClub.name.take(3).uppercase(),
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "AWAY",
                        color = primaryColor.copy(alpha = 0.7f),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Glowing accent at bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                primaryColor.copy(alpha = 0.3f),
                                primaryColor,
                                primaryColor.copy(alpha = 0.3f)
                            )
                        )
                    )
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    LigiopenTheme {
        HomeScreen(
            fixtures = fixtures,
            news = news,
            navigateToSingleVideoScreen = {_,_,_ ->},
            navigateToAllVideosScreen = {},
            navigateToPostMatchScreen = { _, _, _ -> },
            navigateToNewsDetailsScreen = {}
        )
    }
}