package com.jabulani.ligiopen.ui.inapp.videos

import YouTubePlayer
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jabulani.ligiopen.ui.nav.AppNavigation
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth

object SinglePlayerViewScreenDestination: AppNavigation {
    override val title: String = "Single player view"
    override val route: String = "single-player-view-screen"
    val videoId: String = "videoId"
    val videoTitle: String = "videoTitle"
    val videoDate: String = "videoDate"
    val routeWithVideoId: String = "$route/{$videoId}/{$videoTitle}/{$videoDate}"
}

@Composable
fun SinglePlayerViewScreenComposable(
    navigateToPreviousScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        SinglePlayerViewScreen(
            videoId = SinglePlayerViewScreenDestination.videoId,
            videoTitle = SinglePlayerViewScreenDestination.videoTitle,
            videoDate = SinglePlayerViewScreenDestination.videoDate,
            navigateToPreviousScreen = navigateToPreviousScreen
        )
    }
}

@Composable
fun SinglePlayerViewScreen(
    videoId: String,
    videoTitle: String,
    videoDate: String,
    navigateToPreviousScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(
                onClick = navigateToPreviousScreen
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close"
                )
            }
        }
        YouTubePlayer(
            youtubeVideoId = videoId,
            lifecycleOwner = LocalLifecycleOwner.current,
            shouldMute = false,
            shouldAutoPlay = true,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(1f)
        )
        ElevatedCard(
            shape = RoundedCornerShape(
                topStart = screenWidth(16.0),
                topEnd = screenWidth(16.0),
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            ),
            modifier = Modifier
                .fillMaxSize()
                .weight(2f)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(
                        horizontal = screenWidth(16.0),
                        vertical = screenHeight(16.0)
                    )
            ) {
                item {
                    Column {
                        Text(
                            text = videoTitle,
                            textAlign = TextAlign.Center,
                            fontSize = screenFontSize(16.0).sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.height(screenHeight(8.0)))
                        Text(
                            text = videoDate,
                            textAlign = TextAlign.Center,
                            fontSize = screenFontSize(14.0).sp,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }
                items(10, key = { index -> "video_$index" }) {
                    Spacer(modifier = Modifier.height(screenHeight(16.0)))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(screenHeight(16.0)))
                    SingleVideoCard(
                        videoId = "mUki-_cLdsQ",
                        title = " Gor Mahia FC vs Kariobangi Sharks 2-0 FKF CUP All Goals Highlights ",
                        date = "17 hours ago"
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SinglePlayerViewScreenPreview() {
    LigiopenTheme {
        SinglePlayerViewScreen(
            videoId = "mUki-_cLdsQ",
            videoTitle = " Gor Mahia FC vs Kariobangi Sharks 2-0 FKF CUP All Goals Highlights ",
            videoDate = "17 hours ago",
            navigateToPreviousScreen = {}
        )
    }
}