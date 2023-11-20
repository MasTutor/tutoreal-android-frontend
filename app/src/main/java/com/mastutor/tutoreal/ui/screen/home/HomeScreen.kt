package com.mastutor.tutoreal.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.mastutor.tutoreal.data.local.CategoriesData
import com.mastutor.tutoreal.data.local.Category
import com.mastutor.tutoreal.ui.components.CategoryComponentBig
import com.mastutor.tutoreal.ui.components.MatchmakingCardComponent
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.ui.theme.TutorealTheme
import kotlin.reflect.typeOf

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
){
    HomeContent(
        searchOnClick = {},
        onCategoryClicked = {},
        categories = CategoriesData.categories,
        name = "John Madden",
        imageUrl = "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg",
        onUserClicked = {navHostController.navigate(Screen.Profile.route)}
    )
}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    searchOnClick: () -> Unit,
    onCategoryClicked:(String) -> Unit,
    onUserClicked:() -> Unit,
    categories: List<Category>,
    name: String,
    imageUrl: String,
){
    Column(modifier = modifier
        .fillMaxSize()
        ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = MaterialTheme.colorScheme.tertiary),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            Row(Modifier.padding(top = 20.dp)){
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = "Halo,",
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                    Text(text = name, style = MaterialTheme.typography.bodyLarge)

                }
                Spacer(
                    modifier = Modifier
                        .size(8.dp)
                        .weight(1F)
                )
                AsyncImage(model = imageUrl,
                    contentDescription = "User Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.padding(end = 10.dp)
                        .clip(CircleShape).size(50.dp).clickable { onUserClicked() })
            }
        }
        Column(modifier = Modifier
            .verticalScroll(rememberScrollState())) {
        Row(horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(color = Color.White)
                .clickable { searchOnClick() }
        ) {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search",
                tint = Color.DarkGray,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp)
                    .alpha(0.6F)
            )
            Text(
                text = "Cari Tutor Kamu",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.alpha(0.6F)
            )
        }
        Text(
            text = "Pencocokan Tutor Idealmu",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp, top = 20.dp, start = 10.dp, end = 10.dp)
        )
        MatchmakingCardComponent(height = 180, modifier = Modifier.padding(bottom = 20.dp, start = 10.dp, end = 10.dp))
        Text(
            text = "Jadwal Terbaru",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp, start = 10.dp, end = 10.dp)
        )
        Box(
            modifier = Modifier
                .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
                .clip(RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(16.dp)
                )
                .fillMaxWidth()
                .height(120.dp)

        ) {
            //Implement Jadwal user jika sudah jadi
            Text(
                text = "Jadwal Kosong",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
                modifier = Modifier.align(
                    Alignment.Center
                )
            )
        }
        Text(
            text = "Kategori",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp, start = 10.dp, end = 10.dp)
        )
        LazyRow(modifier = Modifier.padding(bottom = 20.dp)) {
            items(categories) { category ->
                CategoryComponentBig(category = category, modifier = Modifier
                    .clickable { onCategoryClicked(category.id) }
                    .padding(start = 5.dp, end = 5.dp))
            }
        }
        Text(
            text = "Random Tutor",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp, start = 10.dp, end = 10.dp)
        )
            //Implement random tutor grabber
        Text(
            text = "Not Yet Implemented",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.CenterHorizontally)
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
fun HomeContentPreview(){
    TutorealTheme {
        HomeContent(searchOnClick = {},
            onCategoryClicked = {},
            categories = CategoriesData.categories,
            name = "John Madden",
            imageUrl = "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg",
            onUserClicked = {}
            )
    }
}