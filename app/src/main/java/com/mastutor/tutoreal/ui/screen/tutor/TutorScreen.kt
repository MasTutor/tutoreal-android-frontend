package com.mastutor.tutoreal.ui.screen.tutor

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mastutor.tutoreal.data.dummy.SillyText
import com.mastutor.tutoreal.data.dummy.TutorData
import com.mastutor.tutoreal.data.dummy.TutorDummy
import com.mastutor.tutoreal.data.local.CategoriesData
import com.mastutor.tutoreal.data.remote.TutorDetail
import com.mastutor.tutoreal.data.remote.TutorItem
import com.mastutor.tutoreal.ui.components.CategoryComponentBig
import com.mastutor.tutoreal.ui.screen.failure.FailureScreen
import com.mastutor.tutoreal.ui.theme.TutorealTheme
import com.mastutor.tutoreal.util.UiState
import com.mastutor.tutoreal.viewmodel.TutorViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun TutorScreen(
    id: String,
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: TutorViewModel = hiltViewModel()
) {
    var tabIndex by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf("About", "Reviews")

    viewModel.tutorResponse.collectAsState(UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getTutor(id)
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
                // TODO: Why tf is the response is on ArrayList. Tell backend later.
                uiState.data?.detailTutor!![0].let {
                    TutorContent(modifier = modifier,
                        onBackClicked = {
                            navHostController.navigateUp()
                        },
                        dataTutor = it,
                        tabs = tabs,
                        tabIndex = tabIndex,
                        onTabSelected = { index -> tabIndex = index }
                    )
                }
            }

            is UiState.Failure -> {
                FailureScreen(onRefreshClicked = { viewModel.getTutor(id) })
            }
        }
    }
}

@Composable
fun TutorContent(
    modifier: Modifier,
    onBackClicked: () -> Unit,
    dataTutor: TutorDetail,
    tabs: List<String>,
    tabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    Column(modifier = modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
        .background(color = MaterialTheme.colorScheme.tertiary)
        .padding(bottom = 10.dp)
    ) {
        Box(modifier = modifier
            .fillMaxWidth()
            .height(390.dp)
            .clip(RoundedCornerShape(bottomEndPercent = 10, bottomStartPercent = 10))
            .background(color = Color.White)
        ) {
            Row(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .align(Alignment.TopStart)
                    .offset(y = 20.dp)
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
            Column(modifier = modifier
                .fillMaxSize()
                .padding(top = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AsyncImage(
                    model = dataTutor.picture.ifEmpty { "https://images.pexels.com/photos/1674666/pexels-photo-1674666.jpeg" },
                    contentDescription = "User Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(150.dp)
                )
                Text(
                    text = dataTutor.nama,
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(top = 15.dp)
                )
                Text(
                    text = dataTutor.specialization,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary,
                    modifier = Modifier.padding(top = 3.dp)
                )
                Button(onClick = { /*TODO*/ },
                    shape = RoundedCornerShape(15),
                    modifier = modifier.padding(top = 20.dp)
                ) {
                    Text("Book A Session IDR ...",
                        style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold))
                }
            }
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(15.dp) ,modifier = modifier.align(Alignment.Center)) {
                tabs.forEachIndexed { index, title ->
                    // TODO: For non-selected, remove background
                    Button(onClick = { onTabSelected(index) },
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        Text(title, style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }

        when(tabIndex) {
            0 -> AboutSection(modifier = modifier, dataTutor = dataTutor)
            1 -> ReviewSection(modifier = modifier)
        }

    }
}

@Composable
fun AboutSection(modifier: Modifier, dataTutor: TutorDetail) {
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(top = 20.dp, start = 15.dp, end = 15.dp)) {
        Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("About ${dataTutor.nama}", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
            // TODO: GET Nama
            Text(dataTutor.about, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Justify)

            Text("Speciality", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
            // TODO: Lazy Row, maybe
            val selectedCategory = CategoriesData.categories.firstOrNull { it.id == dataTutor.categories }
            CategoryComponentBig(category = selectedCategory ?: CategoriesData.categories.first(), // TODO: Temporary handling
                onClick = {}, modifier = modifier.padding(bottom = 5.dp))

            Text("Skills and Experience", style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
            Text(dataTutor.about, style = MaterialTheme.typography.bodySmall, textAlign = TextAlign.Justify)
        }
    }
}

@Composable
fun ReviewSection(modifier: Modifier) {
    Box(modifier = modifier
        .fillMaxWidth()
        .padding(top = 20.dp, start = 15.dp, end = 15.dp)) {
        Text(
            modifier = modifier.fillMaxWidth(),
            text = "No Reviews",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
        )
    }
}