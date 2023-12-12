package com.mastutor.tutoreal.ui.screen.matchmaking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mastutor.tutoreal.data.remote.datahelper.ShittyHelper
import com.mastutor.tutoreal.ui.components.MatchTutorComponent
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.ui.screen.failure.FailureScreen
import com.mastutor.tutoreal.util.UiState
import com.mastutor.tutoreal.viewmodel.ShittyViewModel

@Composable
fun MatchmakingResultScreen(modifier: Modifier = Modifier, viewModel: ShittyViewModel = hiltViewModel(), navHostController: NavHostController) {
    LaunchedEffect(key1 = true) {
        viewModel.getShitty()
    }

    viewModel.shittyResponse.collectAsState(initial = UiState.Loading).value.let {uiState ->
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
                uiState.data?.let {
                    MatchmakingResultContent(
                        modifier = modifier,
                        tutorData = it,
                        onClick = {id ->
                            navHostController.navigate(Screen.Tutor.createRoute(id)) {
                                popUpTo(Screen.Home.route) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }

                        }
                    )
                }
            }
            is UiState.Failure -> {
                FailureScreen(
                    onRefreshClicked = { viewModel.getShitty() })
            }
        }
    }
}

@Composable
fun MatchmakingResultContent(
    modifier: Modifier,
   /* imageUrl: String,
    onBackClicked: () -> Unit,
    onUserClicked: () -> Unit,*/
    tutorData: List<ShittyHelper>,
    onClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        /*Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
           Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 5.dp)
                    .offset(y = 15.dp)
                    .clickable { onBackClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Arrow Forward",
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
            Spacer(
                modifier = Modifier
                    .size(8.dp)
                    .weight(1F)
            )
            AsyncImage(model = imageUrl,
                contentDescription = "User Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 30.dp, end = 10.dp)
                    .clip(CircleShape)
                    .size(50.dp)
                    .clickable { onUserClicked() })
        }*/

        Box(modifier = Modifier.padding(top = 30.dp, start = 30.dp, end = 30.dp)) {
            Text(
                "Hasil Survey Anda",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Column(modifier = Modifier.padding(top = 15.dp)) {
            tutorData.forEach { tutor ->
                MatchTutorComponent(
                    photoUrl = tutor.tutorResponse.picture.ifEmpty { "https://data.1freewallpapers.com/detail/face-surprise-emotions-vector-art-minimalism.jpg" },
                    name = tutor.tutorResponse.nama,
                    job = tutor.tutorResponse.specialization,
                    price = tutor.tutorResponse.price.ifEmpty { "Rp. 30.000" },
                    percentage = tutor.percentage,
                    onClick = {onClick(tutor.tutorResponse.id)},
                    modifier = Modifier.padding(5.dp)
                )

            }
        }
    }


}