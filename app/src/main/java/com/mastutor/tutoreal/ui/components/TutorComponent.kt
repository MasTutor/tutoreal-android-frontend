package com.mastutor.tutoreal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.mastutor.tutoreal.ui.theme.TutorealTheme

@Composable
fun MatchTutorComponent(
    modifier: Modifier = Modifier,
    photoUrl: String,
    name: String,
    job: String,
    price: String,
    percentage: String,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(140.dp)
            .clip(CircleShape)
            .clickable(onClick = onClick)
        ,
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = CircleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp, horizontal = 10.dp)
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = "User Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clip(CircleShape)
                    .size(78.dp)

            )
            Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(120.dp)

                )
                Text(
                    text = job,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Spacer(
                    modifier = Modifier
                        .size(8.dp)
                        .weight(1F)
                )
                Text(
                    text = "$price/Sesi",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Spacer(
                modifier = Modifier
                    .size(8.dp)
                    .weight(1F)
            )
            Box(
                contentAlignment = Alignment.Center, modifier = Modifier
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.primary)
                    .size(78.dp)
            ) {
                Text(text = percentage, color = Color.White)
            }
        }
    }
}

@Composable
fun TutorComponent(
    modifier: Modifier = Modifier,
    photoUrl: String,
    name: String,
    job: String,
    price: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clip(RoundedCornerShape(24.dp))
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = Color.White)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp, horizontal = 10.dp)
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = "User Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clip(CircleShape)
                    .size(78.dp)
            )
            Column(verticalArrangement = Arrangement.SpaceBetween, modifier = Modifier) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.width(120.dp)
                )
                Text(
                    text = job,
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp),
                )
                Spacer(
                    modifier = Modifier
                        .size(8.dp)
                        .weight(1F)
                )
                Text(
                    text = "$price/Sesi",
                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Composable
fun TutorComponentBig(
    modifier: Modifier = Modifier,
    photoUrl: String,
    name: String,
    job: String,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .width(198.dp)
            .height(218.dp)
            .background(Color.White)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = photoUrl,
            contentDescription = "User Photo",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .clip(CircleShape)
                .size(116.dp)
        )
        Text(
            text = name,
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = 10.dp)
        )
        Text(
            text = job,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Normal),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 8.dp),
        )
    }
}


@Preview(
    device = "id:pixel_5",
    showSystemUi = true,
    backgroundColor = 0xFFE8F0F9,
    showBackground = true
)
@Composable
fun TutorComponentPreview() {
    TutorealTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TutorComponent(
                photoUrl = "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg",
                name = "Jim Burton",
                job = "Con Artist",
                price = "Rp. 20.000",
                onClick = {}
            )
            MatchTutorComponent(
                photoUrl = "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg",
                name = "Jim Burton",
                job = "Con Artist",
                price = "Rp 30.000",
                percentage = "58.00%",
                onClick = {}
            )
            TutorComponentBig(
                photoUrl = "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg",
                name = "Jim Burton",
                job = "Con Artist",
                onClick = {})
        }
    }
}