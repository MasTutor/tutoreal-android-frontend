package com.mastutor.tutoreal.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.mastutor.tutoreal.data.local.CategoriesData
import com.mastutor.tutoreal.data.local.Category
import com.mastutor.tutoreal.data.remote.TutorItem
import com.mastutor.tutoreal.ui.components.CategoryComponentBig
import com.mastutor.tutoreal.ui.components.MatchmakingCardComponent
import com.mastutor.tutoreal.ui.components.TutorComponentBig
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.ui.screen.failure.FailureScreen
import com.mastutor.tutoreal.ui.theme.TutorealTheme
import com.mastutor.tutoreal.util.UiState
import com.mastutor.tutoreal.viewmodel.HomeViewModel

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel()
){
    SideEffect {
        viewModel.getToken()
    }

    LaunchedEffect(key1 = true){
        viewModel.getHomeProcess()
    }

    viewModel.homeResponse.collectAsState(initial = UiState.Loading).value.let {uiState ->
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
                val profileResponse = uiState.data?.profileResponse?.profile
                val tutorData = uiState.data?.tutorsResponse?.tutors?.items?.shuffled()?.take(4)
                if (profileResponse != null) {
                    if (tutorData != null) {
                        HomeContent(
                            searchOnClick = {navHostController.navigate(Screen.Search.createRoute(0))},
                            onCategoryClicked = {navHostController.navigate(Screen.Search.createRoute(it + 1))},
                            categories = CategoriesData.categories,
                            name = profileResponse.nama,
                            imageUrl = profileResponse.photoURL,
                            onUserClicked = {navHostController.navigate(Screen.Profile.route)},
                            onMatchmakingClicked = {navHostController.navigate(Screen.Matchmaking.route)},
                            listTutor = tutorData
                        )
                    }
                }
            }
            is UiState.Failure -> {
                FailureScreen(onRefreshClicked = {viewModel.getHomeProcess()}, logoutExist = true, onLogoutClicked = {viewModel.deleteSession()})
            }
        }
    }

}

@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    searchOnClick: () -> Unit,
    onCategoryClicked:(Int) -> Unit,
    onUserClicked:() -> Unit,
    categories: List<Category>,
    name: String,
    imageUrl: String,
    onMatchmakingClicked: () -> Unit,
    listTutor: List<TutorItem>
){
    Column(modifier = modifier
        .fillMaxSize()
        ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(color = MaterialTheme.colorScheme.tertiary),
            horizontalArrangement = Arrangement.Center
        ){
            Row(verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxSize().offset(y = 10.dp)
            ){
                Column(verticalArrangement = Arrangement.Center,
                        modifier = Modifier.padding(start = 10.dp).fillMaxHeight().offset(y = 4.dp)
                ) {
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
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clip(CircleShape)
                        .size(48.dp)
                        .clickable { onUserClicked() })
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
        MatchmakingCardComponent(height = 180, modifier = Modifier
            .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
            .clickable {
                onMatchmakingClicked()
            })
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
            //TODO: Implement Jadwal user jika sudah jadi
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
            itemsIndexed(categories.subList(fromIndex = 1, toIndex = 8)) { idx, category ->
                CategoryComponentBig(category = category, modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp), onClick = {onCategoryClicked(idx)})
            }
        }
        Text(
            text = "Random Tutor",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp, start = 10.dp, end = 10.dp)
        )
            //TODO: Implement OnClick ke Detail User
            LazyRow(modifier = Modifier.padding(bottom = 20.dp)){
                items(listTutor){tutor ->
                    TutorComponentBig(
                        photoUrl = tutor.picture.ifEmpty { "https://data.1freewallpapers.com/detail/face-surprise-emotions-vector-art-minimalism.jpg" },
                        name = tutor.nama,
                        job = tutor.specialization,
                       modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    )

                }
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
fun HomeContentPreview(){
    TutorealTheme {
    }
}