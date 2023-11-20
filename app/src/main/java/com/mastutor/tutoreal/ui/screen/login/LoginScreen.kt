package com.mastutor.tutoreal.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
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

    SideEffect {
        viewModel.tryUserExist()
    }

    LaunchedEffect(userExist){
        if(userExist){
            navHostController.navigate(Screen.Home.route){
                popUpTo(0)
            }
        }
    }

    viewModel.loginResponse.collectAsState(initial = AuthUiState.Idle).value.let {uiState ->
        when(uiState){
            is AuthUiState.Idle -> {
                LoginContent(
                    email = email,
                    password = password,
                    onEmailChanged = viewModel::changeEmailLogin,
                    onPasswordChanged = viewModel::changePasswordLogin,
                    onLoginClicked = {
                        if(email.isEmpty() || !isEmailValid(email)){
                            Toast.makeText(context, "Email kosong atau tidak valid", Toast.LENGTH_SHORT).show()
                        }
                        else if(password.isEmpty()){
                            Toast.makeText(context, "Password kosong", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            viewModel.login()
                        }
                    }

                )
            }
            is AuthUiState.Load -> {
                LoginContent(
                    email = email,
                    password = password,
                    onEmailChanged = {  },
                    onPasswordChanged = { },
                    onLoginClicked = {},
                    modifier = modifier.alpha(0.3f),

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
                    onEmailChanged = viewModel::changeEmailLogin,
                    onPasswordChanged = viewModel::changePasswordLogin,
                    onLoginClicked = {
                        if(email.isEmpty() || !isEmailValid(email)){
                            Toast.makeText(context, "Email kosong atau tidak valid", Toast.LENGTH_SHORT).show()
                        }
                        else if(password.isEmpty()){
                            Toast.makeText(context, "Password kosong", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            viewModel.login()
                        }
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
                    onEmailChanged = viewModel::changeEmailLogin,
                    onPasswordChanged = viewModel::changePasswordLogin,
                    onLoginClicked = {
                        if(email.isEmpty() || !isEmailValid(email)){
                            Toast.makeText(context, "Email kosong atau tidak valid", Toast.LENGTH_SHORT).show()
                        }
                        else if(password.isEmpty()){
                            Toast.makeText(context, "Password kosong", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            viewModel.login()
                        }
                    }

                )
                LaunchedEffect(key1 = true) {
                    Toast.makeText(context, "Gagal cek input, atau cek koneksi internet", Toast.LENGTH_SHORT).show()
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
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 20.dp)
    ) {
        TextField(
            value = email,
            onValueChange = onEmailChanged,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledLabelColor = MaterialTheme.colorScheme.primary,
                cursorColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = Color.White
            ),
            placeholder = {
                Text(
                    text = "Email",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                )
            },
            textStyle = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)

        )
        TextField(
            value = password,
            onValueChange = onPasswordChanged,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.primary,
                disabledLabelColor = MaterialTheme.colorScheme.primary,
                cursorColor = Color.LightGray,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                textColor = Color.White
            ),
            placeholder = {
                Text(
                    text = "Password",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                )
            },
            textStyle = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Button(
            onClick = onLoginClicked,
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary)
        ) {
            Text("Login")
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
            onLoginClicked = {})
    }
}