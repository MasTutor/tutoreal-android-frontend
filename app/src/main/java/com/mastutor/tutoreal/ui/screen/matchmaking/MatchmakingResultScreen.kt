package com.mastutor.tutoreal.ui.screen.matchmaking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastutor.tutoreal.data.dummy.TutorData
import com.mastutor.tutoreal.ui.components.MatchTutorComponent
import com.mastutor.tutoreal.ui.navigation.screen.Screen

@Composable
fun MatchmakingResultScreen(modifier: Modifier, onBackClicked: () -> Unit) {
    MatchmakingResultContent(
        modifier = modifier,
        imageUrl = "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg",
        onBackClicked = {},
        onUserClicked = {}
    )
}

@Composable
fun MatchmakingResultContent(
    modifier: Modifier,
    imageUrl: String,
    onBackClicked: () -> Unit,
    onUserClicked: () -> Unit
) {
    Column(modifier = modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxWidth()
        .verticalScroll(rememberScrollState())
    ) {
        Row(modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 5.dp)
                    .offset(y = 15.dp)
                    .clickable { onBackClicked() },
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription ="Arrow Forward",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color.Black
                )
                Text(
                    text = "Kembali",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Black, fontWeight = FontWeight.Bold),
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
        }

        Box(modifier = modifier.padding(top = 30.dp, start = 30.dp, end = 30.dp)) {
            Text("Your Survey Results", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
        }

        Column(modifier = modifier.padding(top = 15.dp)) {
            TutorData.tutors.forEach { tutor ->
                MatchTutorComponent(
                    photoUrl = tutor.photoUrl,
                    name = tutor.name,
                    job = tutor.job,
                    price = tutor.price,
                    percentage = tutor.percentage
                )
            }
        }
    }


}