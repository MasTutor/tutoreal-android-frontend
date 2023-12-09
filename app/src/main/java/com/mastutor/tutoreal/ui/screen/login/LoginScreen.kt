package com.mastutor.tutoreal.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.ui.theme.TutorealTheme
import com.mastutor.tutoreal.util.AuthUiState
import com.mastutor.tutoreal.util.isEmailValid
import com.mastutor.tutoreal.viewmodel.AuthViewModel

//Stateful
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val email by viewModel.emailLogin
    val password by viewModel.passwordLogin
    val userExist by viewModel.userExist
    val emailError by viewModel.emailErrorLogin
    val passwordError by viewModel.passwordErrorLogin
    val showPassword by viewModel.showPasswordLogin

    SideEffect {
        viewModel.tryUserExist()
    }

    LaunchedEffect(userExist) {
        if (userExist) {
            navHostController.navigate(Screen.Home.route) {
                popUpTo(0)
            }
        }
    }

    viewModel.loginResponse.collectAsState(initial = AuthUiState.Idle).value.let { uiState ->
        when (uiState) {
            is AuthUiState.Idle -> {
                LoginContent(
                    email = email,
                    password = password,
                    onEmailChanged =
                    {
                        viewModel.changeEmailLogin(it)
                        viewModel.changeEmailErrorLogin(email.isEmpty() || !isEmailValid(email))
                    },
                    onPasswordChanged =
                    {
                        viewModel.changePasswordLogin(it)
                        viewModel.changePasswordErrorLogin(password.isEmpty())
                    },
                    onLoginClicked = {
                        viewModel.changeEmailErrorLogin(email.isEmpty() || !isEmailValid(email))
                        viewModel.changePasswordErrorLogin(password.isEmpty())
                        if (!emailError && !passwordError) {
                            viewModel.login()
                        }
                    },
                    showPasswordChanged = viewModel::changeShowPasswordLogin,
                    emailError = emailError,
                    passwordError = passwordError,
                    showPassword = showPassword,
                    onBackClicked = {
                        navHostController.navigateUp()
                    }

                )
            }

            is AuthUiState.Load -> {
                LoginContent(
                    email = email,
                    password = password,
                    onEmailChanged = { },
                    onPasswordChanged = { },
                    onLoginClicked = {},
                    modifier = modifier.alpha(0.3f),
                    showPasswordChanged = {},
                    onBackClicked = {}
                )
                Column(
                    modifier = modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(text = "Loading")
                    CircularProgressIndicator(color = Color.Black)
                }
            }

            is AuthUiState.Success -> {
                LoginContent(
                    email = email,
                    password = password,
                    onEmailChanged =
                    {
                        viewModel.changeEmailLogin(it)
                        viewModel.changeEmailErrorLogin(email.isEmpty() || !isEmailValid(email))
                    },
                    onPasswordChanged =
                    {
                        viewModel.changePasswordLogin(it)
                        viewModel.changePasswordErrorLogin(password.isEmpty())
                    },
                    onLoginClicked = {
                        viewModel.changeEmailErrorLogin(email.isEmpty() || !isEmailValid(email))
                        viewModel.changePasswordErrorLogin(password.isEmpty())
                        if (!emailError && !passwordError) {
                            viewModel.login()
                        }
                    },
                    showPasswordChanged = viewModel::changeShowPasswordLogin,
                    emailError = emailError,
                    passwordError = passwordError,
                    showPassword = showPassword,
                    onBackClicked = {
                        navHostController.navigateUp()
                    }

                )
                LaunchedEffect(key1 = true) {
                    Toast.makeText(context, "Login Berhasil", Toast.LENGTH_SHORT).show()
                }
            }

            is AuthUiState.Failure -> {
                LoginContent(
                    email = email,
                    password = password,
                    onEmailChanged =
                    {
                        viewModel.changeEmailLogin(it)
                        viewModel.changeEmailErrorLogin(email.isEmpty() || !isEmailValid(email))
                    },
                    onPasswordChanged =
                    {
                        viewModel.changePasswordLogin(it)
                        viewModel.changePasswordErrorLogin(password.isEmpty())
                    },
                    onLoginClicked = {
                        viewModel.changeEmailErrorLogin(email.isEmpty() || !isEmailValid(email))
                        viewModel.changePasswordErrorLogin(password.isEmpty())
                        if (!emailError && !passwordError) {
                            viewModel.login()
                        }
                    },
                    showPasswordChanged = viewModel::changeShowPasswordLogin,
                    emailError = emailError,
                    passwordError = passwordError,
                    showPassword = showPassword,
                    onBackClicked = {
                        navHostController.navigateUp()
                    }

                )
                LaunchedEffect(key1 = true) {
                    Toast.makeText(
                        context,
                        "Gagal cek input, atau cek koneksi internet",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}

//Stateless

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginContent(
    email: String,
    password: String,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onLoginClicked: () -> Unit,
    modifier: Modifier = Modifier,
    emailError: Boolean = false,
    passwordError: Boolean = false,
    showPassword: Boolean = false,
    showPasswordChanged: (Boolean) -> Unit,
    onBackClicked: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
                .background(color = MaterialTheme.colorScheme.tertiary),
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 4.dp, top = 40.dp, bottom = 30.dp)
                    .clickable { onBackClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Arrow Back",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color.Black
                )
                Text(
                    text = "Kembali",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
                )
            }
            Text(
                text = "Selamat Datang!",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = Color.Black,
                    fontSize = 32.sp
                ),
                modifier = Modifier
                    .padding(start = 8.dp)

            )
            Text(
                text = "Senang bertemu kembali",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.Black),
                modifier = Modifier
                    .padding(start = 8.dp)

            )

        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 10.dp, end = 10.dp, top = 20.dp)
        ) {

            TextField(
                label = {
                    if (emailError) {
                        Text(
                            text = "Error: Email Kosong atau bukan email",
                            color = MaterialTheme.colorScheme.error
                        )
                    } else {
                        Text(
                            text = "Email",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        )
                    }
                },
                value = email,
                onValueChange = onEmailChanged,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    disabledLabelColor = Color.White,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black
                ),
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)

            )
            TextField(
                label = {
                    if (passwordError) {
                        Text(
                            text = "Error: Password kosong",
                            color = MaterialTheme.colorScheme.error
                        )
                    } else {
                        Text(
                            text = "Password",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        )
                    }
                },
                value = password,
                onValueChange = onPasswordChanged,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.White,
                    disabledLabelColor = Color.White,
                    cursorColor = Color.Black,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    textColor = Color.Black
                ),
                textStyle = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                visualTransformation =
                if (!showPassword) {
                    PasswordVisualTransformation()
                } else {
                    VisualTransformation.None
                },
                trailingIcon = {
                    Icon(
                        imageVector = if (!showPassword) {
                            Icons.Filled.VisibilityOff
                        } else {
                            Icons.Filled.Visibility
                        },
                        contentDescription = "Eye",
                        modifier = Modifier.clickable {
                            showPasswordChanged(!showPassword)
                        }
                    )
                }
            )
            Button(
                onClick = onLoginClicked,
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
            ) {
                Text("Login")
            }
        }
    }

}

@Preview(
    device = "id:pixel_5",
    showSystemUi = true,
    backgroundColor = 0xFFE8F0F9,
    showBackground = true
)
@Composable
fun LoginContentPreview() {
    TutorealTheme {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        LoginContent(
            email = email,
            password = password,
            onEmailChanged = { text -> email = text },
            onPasswordChanged = { text -> password = text },
            onLoginClicked = {},
            showPasswordChanged = {},
            onBackClicked = {})
    }
}