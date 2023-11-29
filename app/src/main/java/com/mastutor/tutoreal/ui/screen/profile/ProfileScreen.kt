package com.mastutor.tutoreal.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.mastutor.tutoreal.ui.components.UserEditComponent
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.ui.screen.failure.FailureScreen
import com.mastutor.tutoreal.ui.theme.TutorealTheme
import com.mastutor.tutoreal.util.UiState
import com.mastutor.tutoreal.viewmodel.ProfileViewModel

//Stateful
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, navHostController: NavHostController, viewModel: ProfileViewModel = hiltViewModel()){
    val userExist by viewModel.userExist
    SideEffect {
        viewModel.tryUserExist()
        viewModel.getToken()
    }

    LaunchedEffect(userExist){
        if(!userExist){
            navHostController.navigate(Screen.Chooser.route){
                popUpTo(0)
            }
        }
        viewModel.getProfile()
    }
    viewModel.profileResponse.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState){
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
                    ProfileContent(
                        fullName = userData.nama,
                        phoneNumber =if(userData.noTelp.isNullOrEmpty()){"Not Yet Implemented"} else{userData.noTelp},
                        gender = userData.gender,
                        photoUrl = userData.photoURL,
                        onFullNameClicked = {},
                        onPhoneNumberClicked = {},
                        onGenderClicked = {},
                        onLogoutClicked = {viewModel.deleteSession()},
                        onHistoryClicked = { })
                }
            }
            is UiState.Failure -> {
                FailureScreen(onRefreshClicked = {viewModel.getProfile()}, logoutExist = true, onLogoutClicked = {viewModel.deleteSession()})
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
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center ,
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp)

    ) {
        //no image changing, too complicated using imgur fuck you
        AsyncImage(
            model = photoUrl,
            contentDescription = "User Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(240.dp)
                .padding(bottom = 10.dp)
                .clip(CircleShape)
        )
        Text(text = "Halo,", style = MaterialTheme.typography.bodyMedium)
        Text(text = fullName, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(bottom = 20.dp))
        UserEditComponent(icon = Icons.Rounded.Person, data = fullName, onClick = onFullNameClicked, modifier = Modifier.padding(bottom = 10.dp))
        UserEditComponent(icon = Icons.Rounded.Call, data = phoneNumber, onClick = onPhoneNumberClicked, modifier = Modifier.padding(bottom = 10.dp))
        UserEditComponent(icon = Icons.Rounded.Face, data = if(gender == 1) "Male" else "Female", onClick = onGenderClicked, modifier = Modifier.padding(bottom = 10.dp))
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
@Preview(
    device = "id:pixel_5",
    showSystemUi = true,
    backgroundColor = 0xFFE8F0F9,
    showBackground = true
)
@Composable
fun ProfileContentPreview(){
    TutorealTheme {
        ProfileContent(
            fullName = "John Madden",
            phoneNumber = "+6285965434232",
            gender = 1,
            photoUrl = "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg",
            onFullNameClicked = { },
            onPhoneNumberClicked = { },
            onGenderClicked = { },
            onLogoutClicked = {  },
            onHistoryClicked = {})
    }
}