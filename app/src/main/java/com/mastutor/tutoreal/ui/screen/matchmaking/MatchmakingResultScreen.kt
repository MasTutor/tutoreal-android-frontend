package com.mastutor.tutoreal.ui.screen.matchmaking

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mastutor.tutoreal.data.local.CategoriesData
import com.mastutor.tutoreal.data.remote.DataItemMatched
import com.mastutor.tutoreal.ui.components.CategoryComponentSmall
import com.mastutor.tutoreal.ui.components.MatchTutorComponent
import com.mastutor.tutoreal.ui.navigation.screen.Screen
import com.mastutor.tutoreal.ui.screen.failure.FailureScreen
import com.mastutor.tutoreal.util.UiState
import com.mastutor.tutoreal.viewmodel.ShittyViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchmakingResultScreen(modifier: Modifier = Modifier, viewModel: ShittyViewModel = hiltViewModel(), navHostController: NavHostController) {
    SideEffect {
        viewModel.getToken()
    }

    var selectedCategory: String? by remember {
        mutableStateOf("Technology")
    }
    val selectedCategoryIdx = remember { mutableIntStateOf(0) }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.getShitty()
        viewModel.getMatched(selectedCategory)
    }
    Column(modifier = Modifier.fillMaxSize()) {


        LazyRow(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
            state = lazyListState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
        ) {
            itemsIndexed(CategoriesData.categoriesNoAll) { index, category ->
                CategoryComponentSmall(
                    category = category,
                    modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    isSelected = index == selectedCategoryIdx.intValue,
                    onClick = {
                        selectedCategory = category.id
                        if (selectedCategoryIdx.intValue != index) {
                            viewModel.getMatched(selectedCategory)
                        }
                        selectedCategoryIdx.intValue = index
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(index)
                        }
                    }
                )
            }
        }

        viewModel.matchedResponse.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
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
                    uiState.data?.let {
                        MatchmakingResultContent(
                            modifier = modifier.fillMaxSize(),
                            tutorData = it.data,
                            onClick = { id ->
                                navHostController.navigate(Screen.Tutor.createRoute(id)) {
                                    popUpTo(Screen.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }

                            },

                            )
                    }
                }

                is UiState.Failure -> {
                    FailureScreen(
                        onRefreshClicked = { viewModel.getMatched(selectedCategory) })
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MatchmakingResultContent(
    modifier: Modifier,
    /* imageUrl: String,
     onBackClicked: () -> Unit,
     onUserClicked: () -> Unit,*/
    tutorData: List<DataItemMatched>,
    onClick: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        /*Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
           Row(
                modifier = Modifier
                    .padding(start = 10.dp, top = 5.dp)
                    .offset(y = 15.dp)
                    .clickable { onBackClicked() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Arrow Forward",
                    modifier = Modifier.padding(end = 8.dp),
                    tint = Color.Black
                )
                Text(
                    text = "Kembali",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    ),
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
        }*/

        Box(modifier = Modifier.padding(top = 30.dp, start = 30.dp, end = 30.dp)) {
            Text(
                "Hasil Survey Anda",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
            )
        }

        Column(modifier = Modifier.padding(top = 15.dp)) {
            tutorData.forEach { tutor ->
                MatchTutorComponent(
                    photoUrl = tutor.picture.ifEmpty { "https://data.1freewallpapers.com/detail/face-surprise-emotions-vector-art-minimalism.jpg" },
                    name = tutor.nama,
                    job = tutor.specialization,
                    price = tutor.price.ifEmpty { "Rp. 30.000" },
                    percentage = tutor.accuracy,
                    onClick = {onClick(tutor.id)},
                    modifier = Modifier.padding(5.dp)
                )

            }
        }
    }


}