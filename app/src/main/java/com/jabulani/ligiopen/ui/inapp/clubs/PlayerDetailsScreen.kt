package com.jabulani.ligiopen.ui.inapp.clubs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.jabulani.ligiopen.AppViewModelFactory
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.data.network.model.club.ClubDetails
import com.jabulani.ligiopen.data.network.model.club.club
import com.jabulani.ligiopen.data.network.model.player.PlayerDetails
import com.jabulani.ligiopen.data.network.model.player.player
import com.jabulani.ligiopen.ui.nav.AppNavigation
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth

object PlayerDetailsScreenDestination: AppNavigation {
    override val title: String = "Player details screen"
    override val route: String = "player-details-screen"
    val playerId: String = "playerId"
    val routeWithPlayerId: String = "$route/{$playerId}"
}

@Composable
fun PlayerDetailsScreenComposable(
    navigateToPreviousScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: PlayerDetailsViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .safeDrawingPadding()
            .background(MaterialTheme.colorScheme.background)
    ) {
        PlayerDetailsScreen(
            playerDetails = uiState.playerDetails,
            clubDetails = uiState.clubDetails,
            navigateToPreviousScreen = navigateToPreviousScreen
        )
    }

}

@Composable
fun PlayerDetailsScreen(
    playerDetails: PlayerDetails,
    clubDetails: ClubDetails,
    navigateToPreviousScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val painter = rememberAsyncImagePainter(
        model = playerDetails.mainPhoto?.link ?: R.drawable.no_photo
        , // Fallback to local drawable if URL is null
        placeholder = painterResource(id = R.drawable.loading),
        error = painterResource(id = R.drawable.broken_image)
    )
    val painter2 = rememberAsyncImagePainter(
        model = clubDetails.clubLogo.link
        , // Fallback to local drawable if URL is null
        placeholder = painterResource(id = R.drawable.loading),
        error = painterResource(id = R.drawable.broken_image)
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            IconButton(onClick = navigateToPreviousScreen) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous screen"
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Player Profile",
                fontSize = screenFontSize(x = 14.0).sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
        }
//        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
        ) {
            Image(
                painter = painter,
                contentScale = ContentScale.Crop,
                contentDescription = "Player's picture",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight(x = 350.0))
            )
            Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
            ElevatedCard(
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .padding(
                        horizontal = screenWidth(x = 16.0)
                    )
                    .fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            vertical = screenHeight(x = 16.0),
                            horizontal = screenWidth(x = 16.0)
                        )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = playerDetails.number.toString(),
                            fontSize = screenFontSize(x = 26.0).sp,
                            color = Color.Green,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.width(screenWidth(x = 16.0)))
                        Text(
                            text = playerDetails.username,
                            fontSize = screenFontSize(x = 20.0).sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painter2,
                            contentDescription = null,
                            modifier = Modifier
                                .size(screenWidth(x = 48.0))
                        )
                        Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                        Text(
                            text = clubDetails.name,
                            fontSize = screenFontSize(x = 16.0).sp,
                            fontWeight = FontWeight.W600
                        )
                    }
                    Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Age: ",
                            fontSize = screenFontSize(x = 16.0).sp,
                            fontWeight = FontWeight.W600
                        )
                        Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                        Text(
                            text = playerDetails.age.toString(),
                            fontSize = screenFontSize(x = 16.0).sp,
                            fontWeight = FontWeight.W600
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Position: ",
                            fontSize = screenFontSize(x = 16.0).sp,
                            fontWeight = FontWeight.W600
                        )
                        Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                        Text(
                            text = playerDetails.playerPosition.name.lowercase().replace("_", " ").replaceFirstChar { it.uppercase() },
                            fontSize = screenFontSize(x = 16.0).sp,
                            fontWeight = FontWeight.W600
                        )
                    }
                    Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Height: ",
                            fontSize = screenFontSize(x = 16.0).sp,
                            fontWeight = FontWeight.W600
                        )
                        Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                        Text(
                            text = playerDetails.height.toString(),
                            fontSize = screenFontSize(x = 16.0).sp,
                            fontWeight = FontWeight.W600
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "Country: ",
                            fontSize = screenFontSize(x = 16.0).sp,
                            fontWeight = FontWeight.W600
                        )
                        Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                        Image(
                            painter = painterResource(id = R.drawable.kenya_flag),
                            contentDescription = null,
                            modifier = Modifier
                                .size(screenWidth(x = 48.0))
                        )
                    }
                    Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Weight: ",
                            fontSize = screenFontSize(x = 16.0).sp,
                            fontWeight = FontWeight.W600
                        )
                        Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                        Text(
                            text = playerDetails.weight.toString(),
                            fontSize = screenFontSize(x = 16.0).sp,
                            fontWeight = FontWeight.W600
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlayerDetailsScreenPreview(
    modifier: Modifier = Modifier
) {
    LigiopenTheme {
        PlayerDetailsScreen(
            playerDetails = player,
            clubDetails = club,
            navigateToPreviousScreen = {}
        )
    }
}