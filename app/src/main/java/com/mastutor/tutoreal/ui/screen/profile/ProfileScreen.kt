package com.mastutor.tutoreal.ui.screen.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Female
import androidx.compose.material.icons.outlined.Male
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.mastutor.tutoreal.ui.components.SelectionDialog
import com.mastutor.tutoreal.ui.components.TextFieldDialog
import com.mastutor.tutoreal.ui.components.UserEditComponent
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.ui.screen.failure.FailureScreen
import com.mastutor.tutoreal.util.UiState
import com.mastutor.tutoreal.viewmodel.ProfileViewModel

//Stateful
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val userExist by viewModel.userExist

    var nameField by remember { mutableStateOf("") }
    var numberField by remember { mutableStateOf("") }
    var genderField by remember { mutableStateOf("") }

    SideEffect {
        viewModel.tryUserExist()
        viewModel.getToken()
    }

    LaunchedEffect(userExist) {
        if (!userExist) {
            navHostController.navigate(Screen.Chooser.route) {
                popUpTo(0)
            }
        }
        viewModel.getProfile()
    }

    val launcher = rememberLauncherForActivityResult(
        contract =
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            // From UX POV this is basically trolling, since we didn't ask for edit confirmation
            viewModel.editPicture(uri)
        }
    }

    // Dialog Logics
    val phoneNumberEdit = remember { mutableStateOf(false) }
    val usernameEdit = remember { mutableStateOf(false) }
    val genderEdit = remember { mutableStateOf(false) }
    val genders = listOf("Pria", "Wanita")
    val selectedItem = remember {
        mutableStateOf(genders[0])
    }

    viewModel.profileResponse.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
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

            is UiState.Success -> {
                val userData = uiState.data
                if (userData != null) {
                    TextFieldDialog(
                        data = "Nama",
                        openDialog = usernameEdit,
                        value = nameField,
                        onValueChanged = { nameField = it },
                        onSubmitClicked = {
                            viewModel.editProfile("name", nameField)
                            usernameEdit.value = false
                        }
                    )

                    TextFieldDialog(
                        data = "Nomor Telepon",
                        openDialog = phoneNumberEdit,
                        value = numberField,
                        onValueChanged = { numberField = it },
                        isNumber = true,
                        onSubmitClicked = {
                            viewModel.editProfile("number", numberField)
                            phoneNumberEdit.value = false
                        }
                    )

                    SelectionDialog(
                        openDialog = genderEdit,
                        options = genders,
                        selectedOptions = selectedItem,
                        onSubmitClicked = {
                            genderField = if (selectedItem.value == "Pria") {
                                "true"
                            } else {
                                "false"
                            }
                            viewModel.editProfile("gender", genderField)
                            genderEdit.value = false
                        }
                    )

                    ProfileContent(
                        fullName = userData.profile.nama,
                        phoneNumber = userData.profile.noTelp.ifEmpty { "Not yet set" },
                        gender = userData.profile.gender,
                        photoUrl = userData.profile.photoURL,
                        onFullNameClicked = { usernameEdit.value = true },
                        onPhoneNumberClicked = { phoneNumberEdit.value = true },
                        onGenderClicked = { genderEdit.value = true },
                        onLogoutClicked = { viewModel.deleteSession() },
                        onHistoryClicked = { navHostController.navigate(Screen.Schedule.route) },
                        onBackClicked = { navHostController.navigateUp() },
                        onEditClicked = {
                            launcher.launch("image/*")
                        }
                    )
                }
            }

            is UiState.Failure -> {
                FailureScreen(
                    onRefreshClicked = { viewModel.getProfile() },
                    logoutExist = true,
                    onLogoutClicked = { viewModel.deleteSession() })
            }
        }
    }
}

//Stateless

@Composable
fun ProfileContent(
    fullName: String,
    phoneNumber: String,
    gender: Int,
    photoUrl: String,
    onFullNameClicked: () -> Unit,
    onPhoneNumberClicked: () -> Unit,
    onGenderClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
    onHistoryClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onEditClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 10.dp, top = 5.dp)
                .offset(y = 5.dp)
                .clickable { onBackClicked() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Arrow Forward",
                modifier = Modifier.padding(end = 8.dp),
                tint = Color.Black
            )
        }
        Box {
            AsyncImage(
                model = photoUrl,
                contentDescription = "User Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(240.dp)
                    .padding(bottom = 10.dp)
                    .clip(CircleShape)
            )

            Button(
                onClick = { onEditClicked() },
                contentPadding = PaddingValues(),
                shape = CircleShape,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                modifier = Modifier
                    .offset(x = (-15).dp, y = (-5).dp)
                    .align(Alignment.BottomEnd)
                    .size(55.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit",
                        tint = Color.White,
                    )
                }
            }
        }

        Text(text = "Halo,", style = MaterialTheme.typography.bodyMedium)
        Text(
            text = fullName,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        UserEditComponent(
            icon = Icons.Outlined.PersonOutline,
            data = fullName,
            onClick = onFullNameClicked,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        UserEditComponent(
            icon = Icons.Rounded.PhoneAndroid,
            data = phoneNumber,
            onClick = onPhoneNumberClicked,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        UserEditComponent(
            icon = if (gender == 1) Icons.Outlined.Male else Icons.Outlined.Female,
            data = if (gender == 1) "Pria" else "Wanita",
            onClick = onGenderClicked,
            modifier = Modifier.padding(bottom = 10.dp)
        )
        Row {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                onClick = onHistoryClicked,
                modifier = Modifier
                    .width(180.dp)
                    .padding(bottom = 20.dp, end = 10.dp), shape = RoundedCornerShape(8.dp)
            ) {
                Text("Jadwal")
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                onClick = onLogoutClicked,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp), shape = RoundedCornerShape(8.dp)
            ) {
                Text("Logout")
            }
        }
    }
}