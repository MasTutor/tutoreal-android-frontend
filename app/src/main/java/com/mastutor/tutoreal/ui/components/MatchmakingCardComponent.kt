package com.mastutor.tutoreal.ui.components


import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mastutor.tutoreal.ui.theme.TutorealTheme

@Composable
fun MatchmakingCardComponent(
    modifier: Modifier = Modifier,
    height: Int
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(height.dp)
    )
    {
        Box {
            AsyncImage(
                model = "https://i.imgur.com/LXAIL9h.jpeg",
                contentDescription = "Teaching",
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary,
                    blendMode = BlendMode.Hardlight
                ),
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 10.dp)
            ) {
                Text(
                    text = "Coba\nPencocokan Kita",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 30.sp, lineHeight = 32.sp),
                    modifier = Modifier.padding(bottom = 2.dp).alpha(0.5F)
                )
                Text(
                    text = "Temukan pembimbing yang cocok untukmu",
                    style = MaterialTheme.typography.bodySmall,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(bottom = 8.dp).alpha(0.5F)
                )
                Icon(
                    imageVector = Icons.Filled.ArrowForward,
                    contentDescription = "Arrow Forward",
                    modifier = Modifier.padding(bottom = 8.dp).alpha(0.5F)
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
fun MatchmakingCardComponentPreview() {
    TutorealTheme {
        Column {
            MatchmakingCardComponent(height = 240)
        }
    }
}