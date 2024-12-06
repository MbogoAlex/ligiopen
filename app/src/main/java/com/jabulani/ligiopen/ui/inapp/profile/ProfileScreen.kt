package com.jabulani.ligiopen.ui.inapp.profile

import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth

@Composable
fun ProfileScreenComposable(
    modifier: Modifier = Modifier
){
    Box(
        modifier = modifier
            .safeDrawingPadding()
    ) {
        ProfileScreen(
            username = "Sam N",
            email = "am@gmail.com"
        )
    }
}

@Composable
fun ProfileScreen(
    username: String,
    email: String,
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
        Card(
            modifier = Modifier
                .fillMaxWidth()

        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(screenWidth(x = 16.0))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.blank_profile),
                    contentDescription = null,
                    modifier = Modifier
                        .size(screenWidth(x = 96.0))
                )
                Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
                Text(
                    text = username,
                    fontSize = screenFontSize(x = 14.0).sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
                Text(
                    text = email,
                    fontSize = screenFontSize(x = 14.0).sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        Column {
            Text(
                text = "Account Info",
                fontSize = screenFontSize(x = 22.0).sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(
                        horizontal = screenWidth(x = 16.0)
                    )
            )
            Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(
                            horizontal = screenWidth(x = 8.0),
                            vertical = screenHeight(x = 8.0)
                        )
                ) {
                    Icon(
                        tint = Color.LightGray,
                        painter = painterResource(id = R.drawable.person),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                    Text(
                        text = username,
                        fontSize = screenFontSize(x = 14.0).sp,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = "Edit username"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(
                            horizontal = screenWidth(x = 8.0),
                            vertical = screenHeight(x = 8.0)
                        )
                ) {
                    Icon(
                        tint = Color.LightGray,
                        painter = painterResource(id = R.drawable.email),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                    Text(
                        text = email,
                        fontSize = screenFontSize(x = 14.0).sp,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.edit),
                            contentDescription = "Edit email"
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
            Card(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(
                            horizontal = screenWidth(x = 8.0),
                            vertical = screenHeight(x = 16.0)
                        )
                ) {
                    Icon(
                        tint = Color.LightGray,
                        painter = painterResource(id = R.drawable.password),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 4.0)))
                    Text(
                        text = "Change password",
                        fontSize = screenFontSize(x = 14.0).sp,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Change password"
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Log out",
                    fontSize = screenFontSize(x = 14.0).sp,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreenPreview() {
    LigiopenTheme {
        ProfileScreen(
            username = "Sam N",
            email = "sam@gmail.com"
        )
    }
}