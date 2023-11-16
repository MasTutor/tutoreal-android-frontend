package com.mastutor.tutoreal.ui.screen.matchmaking

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastutor.tutoreal.ui.theme.TutorealTheme

@Composable
fun MatchmakingOnboardingScreen(
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    Box(modifier = modifier
        .fillMaxSize()
        ) {
        AsyncImage(
            model = "https://images.pexels.com/photos/4145354/pexels-photo-4145354.jpeg",
            contentDescription = "Teaching",
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary, blendMode = BlendMode.Multiply),
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxSize()
        )
        Row(
            modifier = Modifier
            .padding(start = 10.dp, top = 10.dp)
            .align(Alignment.TopStart)
            .clickable { onBackClicked() },
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription ="Arrow Forward",
                modifier = Modifier.padding(end = 8.dp),
                tint = Color.White
            )
            Text(
                text = "Kembali",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White, fontWeight = FontWeight.Bold),
                )
        }

        Text(
            text = "Selamat Datang",
            style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = 120.dp)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(start = 20.dp)
                .offset(y = (-40).dp)){
            Text(
                text = "Survey Kepribadian",
                style = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .align(Alignment.Start)
            )
            Text(
                text = "Sebelum pencocokan dengan pembimbing dimulai kamu akan diarahkan untuk mengisi 25 pertanyaan seputar kepribadian. Klik tombol dibawah untuk melanjutkan.",
                style = MaterialTheme.typography.bodySmall.copy(color = Color.White),
                overflow = TextOverflow.Ellipsis,
                maxLines = 5,
                modifier = Modifier
                    .width(280.dp)
                    .padding(bottom = 40.dp)
                    .align(Alignment.Start)
            )
            OutlinedIconButton(onClick = onNextClicked,
                modifier = Modifier
                    .size(98.dp)
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                border = BorderStroke(width = 2.dp, color = MaterialTheme.colorScheme.primary)
                ) {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription ="Arrow Forward",
                    modifier = Modifier
                        .size(49.dp),
                    tint = Color.White
                )
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
fun MatchmakingOnboardingScreenPreview(){
    TutorealTheme {
        MatchmakingOnboardingScreen(onBackClicked = {}, onNextClicked = {})
    }
}