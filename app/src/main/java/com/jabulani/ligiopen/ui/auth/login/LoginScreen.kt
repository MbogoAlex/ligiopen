package com.jabulani.ligiopen.ui.auth.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.nav.AppNavigation
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.reusables.composables.PasswordFieldComposable
import com.jabulani.ligiopen.utils.reusables.composables.TextFieldComposable
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth
object LoginScreenDestination : AppNavigation {
    override val title: String = "Login screen"
    override val route: String = "login-screen"

}
@Composable
fun LoginScreenComposable(
    navigateToHomeScreen: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        LoginScreen(
            navigateToHomeScreen = navigateToHomeScreen,
            navigateToRegistrationScreen = navigateToRegistrationScreen
        )
    }
}

@Composable
fun LoginScreen(
    navigateToHomeScreen: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                horizontal = screenWidth(x = 16.0),
                vertical = screenWidth(x = 16.0)
            )
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Sign In",
            fontWeight = FontWeight.Bold,
            fontSize = screenFontSize(x = 22.0).sp,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        Text(
            text = "Welcome back! Please enter your details to continue",
            fontSize = screenFontSize(x = 14.0).sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Card {
                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = screenWidth(x = 16.0),
                            vertical = screenHeight(x = 8.0)
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.facebook),
                        contentDescription = "Sign up with Facebook",
                        modifier = Modifier
                            .size(screenWidth(x = 24.0))
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                    Text(
                        text = "Facebook"
                    )
                }
            }
//            Spacer(modifier = Modifier.weight(1f))
            Card {
                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = screenWidth(x = 16.0),
                            vertical = screenHeight(x = 8.0)
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = "Sign up with Google",
                        modifier = Modifier
                            .size(screenWidth(x = 24.0))
                    )
                    Spacer(modifier = Modifier.width(screenWidth(x = 8.0)))
                    Text(
                        text = "Google"
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Or",
                fontSize = screenFontSize(x = 14.0).sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))

        TextFieldComposable(
            label = "Email",
            value = "",
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        PasswordFieldComposable(
            label = "Password",
            value = "",
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .align(Alignment.End)
        ) {
            Text(
                text = "Forgot Password?",
                fontSize = screenFontSize(x = 14.0).sp
            )
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        Button(
            onClick = {
                navigateToHomeScreen()
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "Log In",
                fontSize = screenFontSize(x = 14.0).sp,
            )
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(text = "Don't have an account? ")
            Text(
                text = "Sign Up",
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .clickable {
                        navigateToRegistrationScreen()
                    }
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    LigiopenTheme {
        LoginScreen(
            navigateToHomeScreen = {},
            navigateToRegistrationScreen = {}
        )
    }
}