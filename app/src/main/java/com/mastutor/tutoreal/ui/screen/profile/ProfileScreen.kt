package com.mastutor.tutoreal.ui.screen.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastutor.tutoreal.ui.components.UserEditComponent
import com.mastutor.tutoreal.ui.theme.TutorealTheme

//Stateful
@Composable
fun ProfileScreen(){

}

//Stateless

@Composable
fun ProfileContent(
    fullName: String,
    phoneNumber: String,
    gender: String,
    photoUrl: String,
    onFullNameClicked: () -> Unit,
    onPhoneNumberClicked: () -> Unit,
    onGenderClicked: () -> Unit,
    onLogoutClicked: () -> Unit,
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
        UserEditComponent(icon = Icons.Rounded.Face, data = gender, onClick = onGenderClicked, modifier = Modifier.padding(bottom = 10.dp))
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
            gender = "Male",
            photoUrl = "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg",
            onFullNameClicked = { /*TODO*/ },
            onPhoneNumberClicked = { /*TODO*/ },
            onGenderClicked = { /*TODO*/ },
            onLogoutClicked = { /*TODO*/ })
    }
}