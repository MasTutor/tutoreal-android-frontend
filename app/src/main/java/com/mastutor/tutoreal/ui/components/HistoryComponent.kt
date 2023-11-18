package com.mastutor.tutoreal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.mastutor.tutoreal.R
import com.mastutor.tutoreal.data.local.StatusData
import com.mastutor.tutoreal.ui.theme.TutorealTheme

@Composable
fun HistoryComponent(
    photoUrl: String,
    title: String,
    tutorName: String,
    date: String,
    status: StatusData,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(24.dp),
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .padding(vertical = 20.dp, horizontal = 10.dp)
        ) {
            AsyncImage(
                model = photoUrl,
                contentDescription = "User Photo",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .size(78.dp)
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                        .width(160.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Person ",
                        modifier = Modifier
                            .padding(end = 10.dp, start = 10.dp)
                            .size(32.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = tutorName,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Filled.DateRange,
                        contentDescription = "Person ",
                        modifier = Modifier
                            .padding(end = 10.dp, start = 10.dp)
                            .size(32.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = date,
                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp)
                    )
                }
            }
            Spacer(
                modifier = Modifier
                    .size(8.dp)
                    .weight(1F)
            )

            Column {
                Icon(
                    imageVector = Icons.Filled.KeyboardArrowRight,
                    contentDescription = stringResource(
                        R.string.keyboard_arrow_right
                    ),
                    modifier = Modifier.size(64.dp)
                )
                Spacer(
                    modifier = Modifier
                        .size(8.dp)
                        .weight(1F)
                )
                TagComponent(name = status.status, color = status.color)
            }
        }
    }
}

@Composable
fun ScheduleComponent(
    title: String,
    tutorName: String,
    date: String,
    status: StatusData,
    modifier: Modifier = Modifier
){
    Row(modifier = modifier

        .clip(RoundedCornerShape(16.dp))
        .fillMaxWidth()
        .height(120.dp)
    ){
        Box(modifier = Modifier
            .fillMaxHeight()
            .width(40.dp)
            .background(color = MaterialTheme.colorScheme.primary))
        Spacer(modifier = Modifier.width(4.dp))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(8.dp)
        ){
            Text(text = title, style = MaterialTheme.typography.bodySmall, color = Color.LightGray, modifier = Modifier.padding(bottom = 4.dp))
            Text(text = "Meeting with $tutorName",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = Color.Black,
                modifier = Modifier.padding(bottom = 20.dp).width(220.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Row(modifier = Modifier.padding(end = 4.dp), verticalAlignment = Alignment.CenterVertically){
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(color = MaterialTheme.colorScheme.primary)
                    .padding(vertical = 4.dp, horizontal = 8.dp)
                ) {
                    Icon(imageVector = Icons.Filled.DateRange, contentDescription = "Date", tint = Color.White, modifier = Modifier.padding(end = 4.dp))
                    Text(text = date, style = MaterialTheme.typography.bodySmall, color = Color.White)
                }
                Spacer(
                    modifier = Modifier
                        .size(8.dp)
                        .weight(1F)
                )
                TagComponent(name = status.status, color = status.color)

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
fun HistoryComponentPreview() {
    TutorealTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.secondary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            HistoryComponent(
                photoUrl = "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg",
                title = "Scamming People",
                tutorName = "Tim Burton",
                date = "12/09/2023",
                status = StatusData(status = "Pending", color = Color.Yellow),
                modifier = Modifier.padding(10.dp)
            )
            ScheduleComponent(title = "Scamming People", tutorName = "Tim Burton", date = "12/09/2023", status = StatusData(status = "Pending", color = Color.Yellow), modifier = Modifier.padding(10.dp))

        }
    }
}
