package com.jabulani.ligiopen.ui.inapp.news

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jabulani.ligiopen.AppViewModelFactory
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.data.network.model.news.NewsDto
import com.jabulani.ligiopen.data.network.model.news.news
import com.jabulani.ligiopen.ui.inapp.clubs.LoadingStatus
import com.jabulani.ligiopen.ui.inapp.home.HomeScreenTab
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsScreenComposable(
    addTopPadding: Boolean = true,
    clubId: Int? = null,
    navigateToNewsDetailsScreen: (newsId: String) -> Unit,
    modifier: Modifier = Modifier
) {

    val viewModel: NewsViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    val lifecycleOwner = LocalLifecycleOwner.current
    val lifecycleState by lifecycleOwner.lifecycle.currentStateFlow.collectAsState()

    LaunchedEffect(lifecycleState) {
        when(lifecycleState) {
            Lifecycle.State.DESTROYED -> {}
            Lifecycle.State.INITIALIZED -> {
                viewModel.setClubId(clubId = clubId)
            }
            Lifecycle.State.CREATED -> {

            }
            Lifecycle.State.STARTED -> {

            }
            Lifecycle.State.RESUMED -> {
                viewModel.getInitialData()
            }
        }
    }

    Box(
        modifier = modifier
    ) {
        NewsScreen(
            news = uiState.news,
            addTopPadding = addTopPadding,
            loadingStatus = uiState.loadingStatus,
            navigateToNewsDetailsScreen = navigateToNewsDetailsScreen,
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsScreen(
    news: List<NewsDto>,
    addTopPadding: Boolean,
    loadingStatus: LoadingStatus,
    navigateToNewsDetailsScreen: (newsId: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column {
        if(addTopPadding) {
            Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = screenWidth(x = 8.0)
                    )

            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ligiopen_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(screenWidth(x = 56.0))
                )
                Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                Text(
                    text = HomeScreenTab.NEWS.name.lowercase().replaceFirstChar { first -> first.uppercase() },
                    fontSize = screenFontSize(x = 26.0).sp,
                    fontWeight = FontWeight.W900
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    enabled = false,
                    onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.more),
                        contentDescription = "More"
                    )
                }
            }
        }

        Column(
            modifier = if(addTopPadding) modifier
                .fillMaxSize()
                .padding(
                    vertical = screenHeight(x = 16.0),
                    horizontal = screenWidth(x = 16.0)
                ) else modifier
                .fillMaxSize()
                .padding(
//                vertical = screenHeight(x = 16.0),
                    horizontal = screenWidth(x = 16.0)
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
                if(news.isEmpty()) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Text(
                            text = "No news found",
                            fontSize = screenFontSize(x = 14.0).sp
                        )
                    }
                } else {
                    LazyColumn {
                        item {
                            NewsTile(
                                news = news[0],
                                fullScreen = true,
                                modifier = Modifier
                                    .clickable(
                                        onClick = { navigateToNewsDetailsScreen(news[0].id.toString()) }
                                    )
                            )
                            Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
                        }
                        items(news) { singleNews ->
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        navigateToNewsDetailsScreen(singleNews.id.toString())
                                    }
                            ) {
                                NewsTile(
                                    news = singleNews,
                                    modifier = Modifier
                                )
                                Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
                            }
                        }

                    }
                }
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewsTile(
    news: NewsDto,
    fullScreen: Boolean = false,
    modifier: Modifier = Modifier
) {
    if(fullScreen) {
        Column(modifier = modifier) {
            Card {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(news.coverPhoto.link)
                        .crossfade(true)
                        .build(),
//                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.loading_img),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Club logo",
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = news.title,
                fontSize = screenFontSize(x = 18.0).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = LocalDateTime.now().toString(),
                fontSize = screenFontSize(x = 12.0).sp,
                fontStyle = FontStyle.Italic
            )
        }
    } else {
        Row(
            verticalAlignment = Alignment.Top,
            modifier = modifier
                .padding(
                    start = screenWidth(x = 8.0),
                    end = screenWidth(x = 8.0)
                )
                .fillMaxWidth()
        ) {
            Card(
                modifier = Modifier
                    .width(screenWidth(x = 140.0))
                    .height(screenHeight(x = 80.0))
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(news.coverPhoto.link)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(id = R.drawable.loading_img),
                    error = painterResource(id = R.drawable.loading_img),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Club logo",
                    modifier = Modifier
                        .fillMaxWidth()

                )
            }
            Spacer(modifier = Modifier.width(screenWidth(x = 12.0)))
            Column {
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = news.title,
                    maxLines = 2,
                    fontSize = screenFontSize(x = 14.0).sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = LocalDateTime.now().toString(),
                    fontSize = screenFontSize(x = 12.0).sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsScreenPreview() {
    LigiopenTheme {
        NewsScreen(
            news = news,
            addTopPadding = false,
            loadingStatus = LoadingStatus.INITIAL,
            navigateToNewsDetailsScreen = {},
        )
    }
}