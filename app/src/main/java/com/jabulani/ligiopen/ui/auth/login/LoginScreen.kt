package com.jabulani.ligiopen.ui.auth.login

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jabulani.ligiopen.AppViewModelFactory
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.auth.registration.RegistrationScreenDestination
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
    val email: String = "email"
    val password: String = "password"
    val routeWithArgs: String = "$route/{$email}/{$password}"
}
@Composable
fun LoginScreenComposable(
    navigateToHomeScreen: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    val activity = LocalContext.current as Activity
    val context = LocalContext.current

    val viewModel: LoginViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    if(uiState.loginStatus == LoginStatus.SUCCESS) {
        navigateToHomeScreen()
        viewModel.resetStatus()
    } else if(uiState.loginStatus == LoginStatus.FAIL) {
        Toast.makeText(context, uiState.loginMessage, Toast.LENGTH_LONG).show()
        viewModel.resetStatus()
    }

    BackHandler(onBack = {
        activity.finish()
    })

    Box(
        modifier = Modifier
            .safeDrawingPadding()
    ) {
        LoginScreen(
            email = uiState.email,
            password = uiState.password,
            isButtonEnabled = uiState.isButtonEnabled,
            onChangeEmail = {
                viewModel.updateEmail(it)
            },
            onChangePassword = {
                viewModel.updatePassword(it)
            },
            onLoginUser = viewModel::loginUser,
            navigateToRegistrationScreen = navigateToRegistrationScreen,
            loginStatus = uiState.loginStatus
        )
    }
}

@Composable
fun LoginScreen(
    email: String,
    onChangeEmail: (email: String) -> Unit,
    password: String,
    onChangePassword: (password: String) -> Unit,
    isButtonEnabled: Boolean,
    onLoginUser: () -> Unit,
    navigateToRegistrationScreen: () -> Unit,
    loginStatus: LoginStatus,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background,)
            .fillMaxSize()
            .padding(
                horizontal = screenWidth(x = 16.0),
                vertical = screenWidth(x = 16.0)
            )
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                tint = MaterialTheme.colorScheme.onBackground,
                painter = painterResource(id = R.drawable.ligiopen_icon),
                contentDescription = null
            )
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                text = "Welcome back!",
                fontSize = screenFontSize(x = 16.0).sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 32.0)))
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            text = "Sign in with",
            fontSize = screenFontSize(x = 14.0).sp,
            textAlign = TextAlign.Start,
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
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "Facebook",
                        fontSize = screenFontSize(x = 14.0).sp
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
                        color = MaterialTheme.colorScheme.onBackground,
                        text = "Google",
                        fontSize = screenFontSize(x = 14.0).sp
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
                color = MaterialTheme.colorScheme.onBackground,
                text = "Or",
                fontSize = screenFontSize(x = 14.0).sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))

        TextFieldComposable(
            label = "Email",
            value = email,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            ),
            onValueChange = onChangeEmail,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        PasswordFieldComposable(
            label = "Password",
            value = password,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            onValueChange = onChangePassword,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        TextButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
//                .align(Alignment.End)
        ) {
            Text(
                text = "Forgot Password?",
                fontSize = screenFontSize(x = 14.0).sp
            )
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        Button(
            enabled = isButtonEnabled,
            onClick = onLoginUser,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if(loginStatus == LoginStatus.LOADING) {
                Text(
                    text = "Logging in...",
                    fontSize = screenFontSize(x = 14.0).sp,
                )
            } else {
                Text(
                    text = "Log In",
                    fontSize = screenFontSize(x = 14.0).sp,
                )
            }
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = screenFontSize(x = 14.0).sp,
                text = "Don't have an account? "
            )
            Text(
                text = "Sign Up",
                fontSize = screenFontSize(x = 14.0).sp,
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
            email = "",
            password = "",
            onChangeEmail = {},
            onChangePassword = {},
            isButtonEnabled = false,
            loginStatus = LoginStatus.INITIAL,
            onLoginUser = {},
            navigateToRegistrationScreen = {}
        )
    }
}