package com.mastutor.tutoreal.ui.screen.register

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.RemoveRedEye
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.ui.theme.TutorealTheme
import com.mastutor.tutoreal.util.isEmailValid
import com.mastutor.tutoreal.viewmodel.AuthViewModel

//Stateful
@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: AuthViewModel
) {
    val context = LocalContext.current
    val email by viewModel.emailRegister
    val fullName by viewModel.fullNameRegister
    val password by viewModel.passwordRegister
    val passwordConfirm by viewModel.passwordConfirm
    val fullNameError by viewModel.fullNameError
    val emailError by viewModel.emailError
    val passwordError by viewModel.passwordError
    val confirmPasswordError by viewModel.confirmPasswordError
    val showPassword by viewModel.showPassword
    val showConfirmPassword by viewModel.showConfirmPassword

    val genders = listOf("Male", "Female")
    val (selectedGender, onGenderSelected) = remember {
        mutableStateOf(genders[0])
    }

    RegisterContent(
        fullName = fullName,
        email = email,
        password = password,
        confirmPassword = passwordConfirm,
        onFullNameChanged =
        {
            viewModel.changeFullNameRegister(it)
            viewModel.changeFullNameError(fullName.isEmpty())
        },
        onEmailChanged =
        {
            viewModel.changeEmailRegister(it)
            viewModel.changeEmailError(email.isEmpty() || !isEmailValid(email)
            )
        },
        onPasswordChanged = {
            viewModel.changePasswordRegister(it)
            viewModel.changePasswordError(password.isEmpty())
        },
        onConfirmPasswordChanged = {
            viewModel.changePasswordConfirm(it)
            viewModel.changeConfirmPasswordError(passwordConfirm.isEmpty() || passwordConfirm != password)
        },
        genders = genders,
        selectedGender = selectedGender,
        onGenderSelected =
        {gender ->
            onGenderSelected(gender)
            if(gender == "Male") viewModel.changeGender(true) else viewModel.changeGender(false)
        },
        onNextClicked =
        {
            if(!fullNameError && !emailError && !passwordError && !confirmPasswordError){
                navHostController.navigate(Screen.RegisterPicture.route)
            }
        },
        fullNameError = fullNameError,
        passwordError = passwordError,
        emailError = emailError,
        confirmPasswordError = confirmPasswordError,
        showPassword = showPassword,
        showConfirmPassword = showConfirmPassword,
        showPasswordChanged = viewModel::changeShowPassword,
        showConfirmPasswordChanged = viewModel::changeShowConfirmPassword
    )

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
    fullNameError: Boolean = false,
    emailError: Boolean = false,
    passwordError: Boolean = false,
    confirmPasswordError: Boolean = false,
    showPassword: Boolean = false,
    showConfirmPassword: Boolean = false,
    showPasswordChanged: (Boolean) -> Unit,
    showConfirmPasswordChanged: (Boolean) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 20.dp)
    ) {
        TextField(
            label = {
                if(fullNameError) {
                    Text(text = "Error: Fullname Kosong", color = MaterialTheme.colorScheme.error)
                }
                else{
                    Text(
                        text = "Full name",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    )
                }
            },
            value = fullName,
            onValueChange = onFullNameChanged,
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
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
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
                        modifier = Modifier.padding(end = 4.dp),
                        colors = RadioButtonDefaults.colors(
                            unselectedColor = Color.Black,
                            selectedColor = Color.Black
                        )
                    )
                    Text(
                        text = gender,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        color = Color.Black
                    )
                }
            }
        }
        TextField(
            label = {
                if(emailError) {
                    Text(text = "Error: Email Kosong atau bukan email", color = MaterialTheme.colorScheme.error)
                }
                else{
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
                if(passwordError) {
                    Text(text = "Error: Password kosong", color = MaterialTheme.colorScheme.error)
                }
                else{
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
            if(!showPassword){PasswordVisualTransformation()}else {
                VisualTransformation.None},
            trailingIcon = {
                Icon(
                    imageVector = if(!showPassword){Icons.Filled.Remove} else {Icons.Filled.RemoveRedEye}, contentDescription = "Eye",
                    modifier = Modifier.clickable {
                        showPasswordChanged(!showPassword)
                    }
                )
            }
        )
        TextField(
            label = {
                if(confirmPasswordError) {
                    Text(text = "Error: Confirm password kosong atau salah", color = MaterialTheme.colorScheme.error)
                }
                else{
                    Text(
                        text = "Confirm password",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                    )
                }
            },
            value = confirmPassword,
            onValueChange = onConfirmPasswordChanged,
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
                .padding(bottom = 20.dp),
            visualTransformation =
            if(!showConfirmPassword){PasswordVisualTransformation()}else {
                VisualTransformation.None},
            trailingIcon = {
                Icon(
                    imageVector = if(!showConfirmPassword){Icons.Filled.Remove} else {Icons.Filled.RemoveRedEye}, contentDescription = "Eye",
                    modifier = Modifier.clickable {
                        showConfirmPasswordChanged(!showConfirmPassword)
                    }
                )
            }
        )
        Button(
            onClick = onNextClicked, modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ),
        ) {
            Text("Next")
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
            onNextClicked = {},
            showConfirmPasswordChanged = {},
            showPasswordChanged = {}
            )
    }
}
