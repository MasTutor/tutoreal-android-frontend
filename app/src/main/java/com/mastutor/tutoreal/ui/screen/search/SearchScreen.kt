package com.mastutor.tutoreal.ui.screen.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mastutor.tutoreal.data.local.CategoriesData
import com.mastutor.tutoreal.data.remote.TutorItem
import com.mastutor.tutoreal.ui.components.CategoryComponentSmall
import com.mastutor.tutoreal.ui.components.TutorComponent
import com.mastutor.tutoreal.ui.screen.failure.FailureScreen
import com.mastutor.tutoreal.viewmodel.SearchViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    categoryIdx: Int,
    onBackClicked: () -> Unit,
    moveToTutorDetail: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel()
) {
    var search by remember { mutableStateOf("") }
    var selectedCategory: String? by remember {
        mutableStateOf(null)
    }
    val selectedCategoryIdx = remember{ mutableIntStateOf(categoryIdx) }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember{FocusRequester()}
    LaunchedEffect(key1 = true){
        lazyListState.animateScrollToItem(selectedCategoryIdx.intValue)
        focusRequester.requestFocus()
        if(categoryIdx != 0){
            selectedCategory = CategoriesData.categories[categoryIdx].id
        }
    }

    SearchContent(
        search = search,
        onSearchChanged = {text -> search = text} ,
        lazyListState = lazyListState,
        selectedCategoryIdx = selectedCategoryIdx,
        coroutineScope = coroutineScope,
        onBackClicked = onBackClicked,
        focusRequester = focusRequester,
        tutors = viewModel.searchTutors(specialization = search, category = selectedCategory).collectAsLazyPagingItems(),
        moveToTutorDetail = moveToTutorDetail,
        onSelectedCategoryChanged = {id -> selectedCategory = id}
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchContent(
    search: String,
    onSearchChanged: (String) -> Unit,
    lazyListState: LazyListState,
    selectedCategoryIdx: MutableIntState,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    focusRequester: FocusRequester,
    tutors: LazyPagingItems<TutorItem>,
    moveToTutorDetail: (String) -> Unit,
    onSelectedCategoryChanged: (String?) -> Unit
){
    Column(modifier = modifier.fillMaxWidth()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(color = MaterialTheme.colorScheme.tertiary)
            ,){
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 30.dp)
            ){
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Arrow Back", modifier = Modifier
                    .clickable {
                        onBackClicked()
                    }
                    .padding(end = 10.dp))
                TextField(
                    maxLines = 1,
                    value = search,
                    onValueChange = onSearchChanged,
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        disabledLabelColor = Color.Transparent,
                        cursorColor = Color.LightGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        textColor = Color.Black
                    ),
                    placeholder = {
                        Text(
                            text = "Cari Materi Yang Diinginkan",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.Normal,
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        )
                    },
                    textStyle = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester)

                )
            }
        }

        LazyRow(
            modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
            state = lazyListState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
        ){
            itemsIndexed(CategoriesData.categories){ index, category ->
                CategoryComponentSmall(category = category, modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    isSelected = index == selectedCategoryIdx.intValue,
                    onClick = {
                        selectedCategoryIdx.intValue = index
                        if(index != 0) {
                            onSelectedCategoryChanged(category.id)
                        }
                        else{
                            onSelectedCategoryChanged(null)
                        }
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(index)
                        }
                    }
                )
            }
        }
        SearchPaging(tutors = tutors, moveToTutorDetail = moveToTutorDetail)

    }
}

@Composable
fun SearchPaging(
    tutors: LazyPagingItems<TutorItem>,
    modifier: Modifier = Modifier,
    moveToTutorDetail: (String) -> Unit,
){
    LazyColumn(modifier = modifier.padding(horizontal = 10.dp)){
        items(items = tutors, key = {it.id}){ tutor ->
            if (tutor != null){
                TutorComponent(
                    photoUrl = tutor.picture.ifEmpty { "https://data.1freewallpapers.com/detail/face-surprise-emotions-vector-art-minimalism.jpg" },
                    name = tutor.nama,
                    job = tutor.specialization,
                    price = tutor.price.ifEmpty { "Rp. 30.000" },
                    onClick = { moveToTutorDetail(tutor.id) }
                )
            }

        }
        when(val state = tutors.loadState.refresh){
            is LoadState.Error ->{
                item {
                    if (state.error.message == "Null Pointer Nih"){
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()){
                            Text(text = "No Data", textAlign = TextAlign.Center)
                        }
                    }
                    else {
                        Column {
                            FailureScreen(onRefreshClicked = { tutors.refresh() })
                            Text(text = "${state.error.message}")
                        }
                    }
                }

            }
            is LoadState.Loading -> {
                item {
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
            }
            else -> {}
        }
        when(val state = tutors.loadState.append){
            is LoadState.Error -> {
                item {
                    if (state.error.message == "null"){
                        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally){
                            Text(text = "No Data")
                        }
                    }
                    else {
                        Column {
                            FailureScreen(onRefreshClicked = { tutors.retry()})
                            Text(text = "${state.error.cause}")
                        }
                    }
                }
            }
            is LoadState.Loading -> {
                item {
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
            }
            else -> {}
        }
    }
}