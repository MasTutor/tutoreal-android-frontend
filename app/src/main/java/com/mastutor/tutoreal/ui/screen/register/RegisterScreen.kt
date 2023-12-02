package com.mastutor.tutoreal.ui.screen.register

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
    val male by viewModel.male

    val genders = listOf("Male", "Female")
    val (selectedGender, onGenderSelected) = remember {
        mutableStateOf(genders[0])
    }

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
                navHostController.navigate(Screen.RegisterPicture.route)
            }
        },
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
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp, top = 20.dp)
    ) {
        TextField(
            value = fullName,
            onValueChange = onFullNameChanged,
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
                    text = "Full name",
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
                            unselectedColor = MaterialTheme.colorScheme.primary,
                            selectedColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    Text(
                        text = gender,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Normal,
                            fontSize = 16.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
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
                .padding(bottom = 10.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        TextField(
            value = confirmPassword,
            onValueChange = onConfirmPasswordChanged,
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
                    text = "Confirm password",
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
            onClick = onNextClicked, modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary
            ),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary)
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
            onNextClicked = {},)
    }
}
