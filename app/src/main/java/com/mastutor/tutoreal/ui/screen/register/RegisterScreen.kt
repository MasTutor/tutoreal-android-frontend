package com.mastutor.tutoreal.ui.screen.register

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.mastutor.tutoreal.ui.theme.TutorealTheme
import com.mastutor.tutoreal.util.AuthUiState
import com.mastutor.tutoreal.util.isEmailValid
import com.mastutor.tutoreal.viewmodel.AuthViewModel

//Stateful
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val email by viewModel.emailRegister
    val fullName by viewModel.fullNameRegister
    val password by viewModel.passwordRegister
    val passwordConfirm by viewModel.passwordConfirm
    val male by viewModel.male

    val genders = listOf("Male", "Female")
    val (selectedGender, onGenderSelected) = remember {
        mutableStateOf(genders[0])
    }

    viewModel.registerResponse.collectAsState(initial = AuthUiState.Idle).value.let {uiState ->
        when(uiState){
            is AuthUiState.Idle -> {
                RegisterContent(
                    fullName = fullName,
                    email = email,
                    password = password,
                    confirmPassword = passwordConfirm,
                    onFullNameChanged = viewModel::changeFullNameRegister,
                    onEmailChanged = viewModel::changeEmailRegister,
                    onPasswordChanged = viewModel::changePasswordRegister,
                    onConfirmPasswordChanged = viewModel::changePasswordConfirm,
                    genders = genders,
                    selectedGender = selectedGender,
                    onGenderSelected =
                    {gender ->
                        onGenderSelected(gender)
                        if(gender == "Male") viewModel.changeGender(true) else viewModel.changeGender(false)
                        Toast.makeText(context, "Aslinya: $gender Lah Kok: $male", Toast.LENGTH_SHORT).show()
                    },
                    onNextClicked =
                    {
                        if(fullName.isEmpty()){
                            Toast.makeText(context, "Full name kosong", Toast.LENGTH_SHORT).show()
                        }
                        else if(email.isEmpty() || !isEmailValid(email)){
                            Toast.makeText(context, "Email kosong atau tidak valid", Toast.LENGTH_SHORT).show()
                        }
                        else if(password.isEmpty()){
                            Toast.makeText(context, "Password kosong", Toast.LENGTH_SHORT).show()
                        }
                        else if(password != passwordConfirm){
                            Toast.makeText(context, "Confirm password tidak cocok", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            viewModel.register()
                        }
                    },
                )
            }
            is AuthUiState.Load -> {
                RegisterContent(
                    fullName = fullName,
                    email = email,
                    password = password,
                    confirmPassword = passwordConfirm,
                    onFullNameChanged = {},
                    onEmailChanged ={} ,
                    onPasswordChanged = {},
                    onConfirmPasswordChanged = {},
                    genders = genders,
                    selectedGender = selectedGender,
                    onGenderSelected = {},
                    onNextClicked = {},
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
                RegisterContent(
                    fullName = fullName,
                    email = email,
                    password = password,
                    confirmPassword = passwordConfirm,
                    onFullNameChanged = viewModel::changeFullNameRegister,
                    onEmailChanged = viewModel::changeEmailRegister,
                    onPasswordChanged = viewModel::changePasswordRegister,
                    onConfirmPasswordChanged = viewModel::changePasswordConfirm,
                    genders = genders,
                    selectedGender = selectedGender,
                    onGenderSelected =
                    {gender ->
                        onGenderSelected(gender)
                        if(gender == "Male") viewModel.changeGender(true) else viewModel.changeGender(false)
                    },
                    onNextClicked = viewModel::register,
                )
                LaunchedEffect(key1 = true) {
                    Toast.makeText(context, "Register Successful", Toast.LENGTH_SHORT).show()
                }
            }
            is AuthUiState.Failure -> {
                RegisterContent(
                    fullName = fullName,
                    email = email,
                    password = password,
                    confirmPassword = passwordConfirm,
                    onFullNameChanged = viewModel::changeFullNameRegister,
                    onEmailChanged = viewModel::changeEmailRegister,
                    onPasswordChanged = viewModel::changePasswordRegister,
                    onConfirmPasswordChanged = viewModel::changePasswordConfirm,
                    genders = genders,
                    selectedGender = selectedGender,
                    onGenderSelected =
                    {gender ->
                        onGenderSelected(gender)
                        if(gender == "Male") viewModel.changeGender(true) else viewModel.changeGender(false)
                    },
                    onNextClicked = viewModel::register,
                )
                LaunchedEffect(key1 = true) {
                    Toast.makeText(context, "Failed please check if input correct, or check your internet", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}

//Stateless
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterContent(
    fullName: String,
    email: String,
    password: String,
    confirmPassword: String,
    onFullNameChanged: (String) -> Unit,
    onEmailChanged: (String) -> Unit,
    onPasswordChanged: (String) -> Unit,
    onConfirmPasswordChanged: (String) -> Unit,
    genders: List<String>,
    selectedGender: String,
    onGenderSelected: (String) -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 20.dp)
    ) {
        OutlinedTextField(
            value = fullName,
            onValueChange = onFullNameChanged,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colorScheme.primary),
            placeholder = {
                Text(
                    text = "full name",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                )
            },
            textStyle = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .scale(scaleX = 0.9F, scaleY = 0.9F)
                .fillMaxWidth()
                .padding(bottom = 10.dp)

        )
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)) {
            genders.forEach { gender ->
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .selectable(
                            selected = (gender == selectedGender),
                            onClick = { onGenderSelected(gender) })

                ) {
                    RadioButton(
                        selected = (gender == selectedGender),
                        onClick = {
                            onGenderSelected(gender)

                                  },
                        modifier = Modifier.padding(all = 8.dp)
                    )
                    Text(
                        text = gender,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        modifier = Modifier.padding(all = 8.dp)
                    )
                }
            }
        }
        OutlinedTextField(
            value = email,
            onValueChange = onEmailChanged,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colorScheme.primary),
            placeholder = {
                Text(
                    text = "email",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                )
            },
            textStyle = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .scale(scaleX = 0.9F, scaleY = 0.9F)
                .fillMaxWidth()
                .padding(bottom = 10.dp)

        )
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChanged,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colorScheme.primary),
            placeholder = {
                Text(
                    text = "password",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                )
            },
            textStyle = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .scale(scaleX = 0.9F, scaleY = 0.9F)
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChanged,
            shape = RoundedCornerShape(16.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(focusedBorderColor = MaterialTheme.colorScheme.primary),
            placeholder = {
                Text(
                    text = "confirm password",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    )
                )
            },
            textStyle = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .scale(scaleX = 0.9F, scaleY = 0.9F)
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
        Button(
            onClick = onNextClicked, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp), shape = RoundedCornerShape(16.dp)
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
fun RegisterContentPreview() {
    TutorealTheme {
        var fullName by remember {
            mutableStateOf("")
        }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }
        val genders = listOf("Male", "Female")
        val (selectedGender, onGenderSelected) = remember {
            mutableStateOf(genders[0])
        }
        RegisterContent(
            fullName = fullName,
            email = email,
            password = password,
            confirmPassword = confirmPassword,
            onFullNameChanged = { text -> fullName = text },
            onEmailChanged = { text -> email = text },
            onPasswordChanged = { text -> password = text },
            onConfirmPasswordChanged = { text -> confirmPassword = text },
            genders = genders,
            selectedGender = selectedGender,
            onGenderSelected = onGenderSelected,
            onNextClicked = {},)
    }
}
