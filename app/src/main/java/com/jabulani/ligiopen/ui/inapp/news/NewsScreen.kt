package com.jabulani.ligiopen.ui.inapp.news

import android.app.Activity
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth

val newsItem = NewsItem(
    title = "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s",
    body = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)",
    author = "Mike Kevin",
    dateTime = "3:40 PM, July 15th, 2024",
    titleImage = R.drawable.sports_news_item
)

@Composable
fun NewsScreenComposable(
    navigateToNewsDetailsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as Activity
    BackHandler(onBack = {
        activity.finish()
    })

    Box(
        modifier = modifier
    ) {
        NewsScreen(
            navigateToNewsDetailsScreen = navigateToNewsDetailsScreen
        )
    }
}

@Composable
fun NewsScreen(
    navigateToNewsDetailsScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(
//                vertical = screenHeight(x = 16.0),
                horizontal = screenWidth(x = 16.0)
            )
    ) {
        LazyColumn {
            items(10) {index ->
                Column(
                    modifier = Modifier
                        .clickable {
                            navigateToNewsDetailsScreen()
                        }
                ) {
                    NewsTile(
                        newsItem = newsItem,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                    HorizontalDivider()
                    Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
                }
            }
        }
    }
}

@Composable
fun NewsTile(
    newsItem: NewsItem,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(
                start = screenWidth(x = 8.0),
                end = screenWidth(x = 8.0)
            )
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = R.drawable.sports_news_item),
            contentDescription = null,
            modifier = Modifier
                .size(screenWidth(x = 150.0))
        )
        Spacer(modifier = Modifier.width(screenWidth(x = 12.0)))
        Column {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = newsItem.title,
                fontSize = screenFontSize(x = 14.0).sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(screenHeight(x = 4.0)))
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = newsItem.dateTime,
                fontSize = screenFontSize(x = 12.0).sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsScreenPreview() {
    LigiopenTheme {
        NewsScreen(
            navigateToNewsDetailsScreen = {}
        )
    }
}