package com.jabulani.ligiopen.ui.auth.registration

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
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jabulani.ligiopen.AppViewModelFactory
import com.jabulani.ligiopen.R
import com.jabulani.ligiopen.ui.nav.AppNavigation
import com.jabulani.ligiopen.ui.theme.LigiopenTheme
import com.jabulani.ligiopen.utils.reusables.composables.PasswordFieldComposable
import com.jabulani.ligiopen.utils.reusables.composables.TextFieldComposable
import com.jabulani.ligiopen.utils.screenFontSize
import com.jabulani.ligiopen.utils.screenHeight
import com.jabulani.ligiopen.utils.screenWidth

object RegistrationScreenDestination : AppNavigation {
    override val title: String = "Registration screen"
    override val route: String = "registration-screen"

}

@Composable
fun RegistrationScreenComposable(
    navigateToLoginScreen: () -> Unit,
    navigateToLoginScreenWithArgs: (email: String, password: String) -> Unit,
    modifier: Modifier = Modifier
) {

    val context = LocalContext.current

    val viewModel: RegistrationViewModel = viewModel(factory = AppViewModelFactory.Factory)
    val uiState by viewModel.uiState.collectAsState()

    if(uiState.registrationStatus == RegistrationStatus.SUCCESS) {
        navigateToLoginScreenWithArgs(uiState.email, uiState.password)
        viewModel.resetStatus()
    } else if(uiState.registrationStatus == RegistrationStatus.FAIL) {
        Toast.makeText(context, uiState.resultMessage, Toast.LENGTH_LONG).show()
        viewModel.resetStatus()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .safeDrawingPadding()
        ) {
            RegistrationScreen(
                username = uiState.username,
                email = uiState.email,
                password = uiState.password,
                confirmPassword = uiState.confirmPassword,
                isButtonEnabled = uiState.isButtonEnabled,
                onChangeUsername = {
                    viewModel.updateUsername(it)
                },
                onChangeEmail = {
                    viewModel.updateEmail(it)
                },
                onChangePassword = {
                    viewModel.updatePassword(it)
                },
                onChangeConfirmPassword = {
                    viewModel.updateConfirmPassword(it)
                },
                onRegisterUser = {
                    if(uiState.password.length < 6) {
                        Toast.makeText(context, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                    } else if(uiState.password != uiState.confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.registerUser()
                    }
                },
                registrationStatus = uiState.registrationStatus,
                navigateToLoginScreen = navigateToLoginScreen
            )
        }
    }
}

@Composable
fun RegistrationScreen(
    username: String,
    onChangeUsername: (name: String) -> Unit,
    email: String,
    onChangeEmail: (email: String) -> Unit,
    password: String,
    onChangePassword: (password: String) -> Unit,
    confirmPassword: String,
    onChangeConfirmPassword: (confirmPassword: String) -> Unit,
    isButtonEnabled: Boolean,
    onRegisterUser: () -> Unit,
    navigateToLoginScreen: () -> Unit,
    registrationStatus: RegistrationStatus,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
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
                text = "Welcome to Ligi Open!",
                fontSize = screenFontSize(x = 16.0).sp,
                fontWeight = FontWeight.Bold
            )
        }
        Spacer(modifier = Modifier.height(screenHeight(x = 8.0)))
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            text = "Are you new here? Please create an account to continue:",
            fontSize = screenFontSize(x = 14.0).sp,
//            textAlign = TextAlign.Center,
            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
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
            label = "Name",
            value = username,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Text
            ),
            onValueChange = onChangeUsername,
            modifier = Modifier
                .fillMaxWidth()
        )
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
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Password
            ),
            onValueChange = onChangePassword,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        PasswordFieldComposable(
            label = "Confirm password",
            value = confirmPassword,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            onValueChange = onChangeConfirmPassword,
            modifier = Modifier
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        TextButton(onClick = { /*TODO*/ }) {
            Text(
                text = "Terms of Service and Private Policy",
                fontSize = screenFontSize(x = 14.0).sp
            )
        }
        Text(
            color = MaterialTheme.colorScheme.onBackground,
            text = "By clicking Sign Up, you agree to our Terms of Service and Privacy Policy.",
            fontSize = screenFontSize(x = 14.0).sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(screenHeight(x = 16.0)))
        Button(
            enabled = isButtonEnabled,
            onClick = onRegisterUser,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if(registrationStatus == RegistrationStatus.LOADING) {
                Text(
                    text = "Signing up...",
                    fontSize = screenFontSize(x = 14.0).sp,
                )
            } else {
                Text(
                    text = "Sign Up",
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
                text = "Already have an account? ",
                fontSize = screenFontSize(x = 14.0).sp
            )
            Text(
                text = "Sign In",
                color = MaterialTheme.colorScheme.primary,
                fontSize = screenFontSize(x = 14.0).sp,
                modifier = Modifier
                    .clickable {
                        navigateToLoginScreen()
                    }
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegistrationScreenPreview() {
    LigiopenTheme {
        RegistrationScreen(
            username = "",
            onChangeUsername = {},
            email = "",
            onChangeEmail = {},
            password = "",
            onChangePassword = {},
            confirmPassword = "",
            onChangeConfirmPassword = {},
            isButtonEnabled = false,
            onRegisterUser = { /*TODO*/ },
            registrationStatus = RegistrationStatus.INITIAL,
            navigateToLoginScreen = { /*TODO*/ })
    }
}