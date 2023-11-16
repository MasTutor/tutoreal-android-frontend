package com.mastutor.tutoreal.ui.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastutor.tutoreal.ui.theme.TutorealTheme

@Composable
fun MatchmakingCardComponent(
    modifier: Modifier = Modifier,
    height: Int
){
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
            .padding(10.dp)
    )
    {
        Box {
            AsyncImage(
                model = "https://images.pexels.com/photos/4145354/pexels-photo-4145354.jpeg",
                contentDescription = "Teaching",
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.primary, blendMode = BlendMode.Hardlight),
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
            )
            Column(modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 10.dp)){
                Text(
                    text = "Coba Pencocokan Kita",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Temukan pembimbing yang cocok untukmu",
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription ="Arrow Forward",
                    modifier = Modifier.padding(bottom = 8.dp)
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
fun MatchmakingCardComponentPreview(){
    TutorealTheme {
        Column {
            MatchmakingCardComponent(height = 240)
        }
    }
}