package com.mastutor.tutoreal.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mastutor.tutoreal.R
import com.mastutor.tutoreal.ui.theme.TutorealTheme

@Composable
fun UserEditComponent(
    icon: ImageVector,
    data: String,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color.White), modifier = modifier
            .fillMaxWidth()
            .height(88.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Icon(
                    imageVector = icon, contentDescription = icon.name, modifier = Modifier
                        .padding(end = 20.dp, start = 10.dp)
                        .size(64.dp), tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = data,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }

            Icon(
                imageVector = Icons.Filled.KeyboardArrowRight, contentDescription = stringResource(
                    R.string.keyboard_arrow_right
                ), modifier = Modifier.size(64.dp)
            )
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
fun UserEditComponentPreview() {
    TutorealTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.primary),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            UserEditComponent(
                icon = Icons.Rounded.Person,
                data = "John Madden",
                modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 20.dp)
            )
            UserEditComponent(
                icon = Icons.Rounded.Call,
                data = "+6285965434232",
                modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 10.dp)
            )
            UserEditComponent(
                icon = Icons.Rounded.Face,
                data = "Male",
                modifier = Modifier.padding(start = 40.dp, end = 40.dp, top = 10.dp)
            )
        }
    }
}