package com.jabulani.ligiopen.ui.inapp.clubs

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth

@Composable
fun PlayerDetailsScreenComposable(
    modifier: Modifier = Modifier
) {

}

@Composable
fun PlayerDetailsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(0.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(onClick = {}) {
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
        }
//        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        Image(
            painter = painterResource(id = R.drawable.soccer_player_2),
            contentScale = ContentScale.Fit,
            contentDescription = "Player's picture",
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight(x = 350.0))
        )
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
                        text = "10",
                        fontSize = screenFontSize(x = 26.0).sp,
                        color = Color.Green,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 16.0)))
                    Text(
                        text = "John Doe",
                        fontSize = screenFontSize(x = 20.0).sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.club2),
                        contentDescription = null,
                        modifier = Modifier
                            .size(screenWidth(x = 48.0))
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                    Text(
                        text = "FC NYC",
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
                        text = "31",
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
                        text = "Forward",
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
                        text = "160",
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
                        text = "85",
                        fontSize = screenFontSize(x = 16.0).sp,
                        fontWeight = FontWeight.W600
                    )
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
        PlayerDetailsScreen()
    }
}