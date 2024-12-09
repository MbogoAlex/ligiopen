package com.jabulani.ligiopen.ui.inapp.news

import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.nav.AppNavigation
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth
object NewsDetailsScreenDestination : AppNavigation {
    override val title: String = "News details screen"
    override val route: String = "news-details-screen"
}

@Composable
fun NewsDetailsScreenComposable(
    navigateToPreviousScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as Activity
    BackHandler(onBack = navigateToPreviousScreen)
    ElevatedCard(
        shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = modifier
                .safeDrawingPadding()
        ) {
            NewsDetailsScreen(
                navigateToPreviousScreen = navigateToPreviousScreen
            )
        }
    }
}

@Composable
fun NewsDetailsScreen(
    item: NewsItem = newsItem,
    navigateToPreviousScreen: () -> Unit,
    modifier: Modifier = Modifier
        .fillMaxSize()
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background,)
            .fillMaxSize()
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
        ) {
            Row(
//            horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(screenWidth(x = 8.0))

            ) {
                IconButton(onClick = navigateToPreviousScreen) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Previous screen"
                    )
                }
                Spacer(modifier = Modifier.width(screenWidth(x = 3.0)))
                Text(
                    text = "News",
                    fontSize = screenFontSize(x = 14.0).sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painterResource(id = R.drawable.sports_news_item),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(
                        horizontal = screenWidth(x = 16.0),
                        vertical = screenWidth(x = 16.0)
                    )
            ){
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = item.title,
                    textAlign = TextAlign.Center,
                    fontSize = screenFontSize(x = 16.0).sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "${item.author} - ",
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                    Text(
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "2024 Jun 19th",
                        fontStyle = FontStyle.Italic,
                        fontSize = screenFontSize(x = 14.0).sp
                    )
                }
                Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = item.body,
                    fontSize = screenFontSize(x = 14.0).sp
                )
                Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = item.body.take(400),
                    fontSize = screenFontSize(x = 14.0).sp
                )
                Spacer(modifier = Modifier.height(screenHeight(x = 48.0)))
                Text(
                    color = MaterialTheme.colorScheme.onBackground,
                    text = "Related Stories",
                    fontSize = screenFontSize(x = 16.0).sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
                for(i in 1..3) {
                    NewsTile(newsItem = newsItem)
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NewsDetailsScreenPreview() {
    LigiopenTheme {
        NewsDetailsScreen(
            navigateToPreviousScreen = {}
        )
    }
}