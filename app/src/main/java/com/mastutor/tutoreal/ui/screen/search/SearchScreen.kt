package com.mastutor.tutoreal.ui.screen.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mastutor.tutoreal.data.dummy.TutorData
import com.mastutor.tutoreal.data.dummy.TutorDummy
import com.mastutor.tutoreal.data.local.CategoriesData
import com.mastutor.tutoreal.ui.components.CategoryComponentSmall
import com.mastutor.tutoreal.ui.components.TutorComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    categoryIdx: Int,
    onBackClicked: () -> Unit
    )
{
    var search by remember { mutableStateOf("") }
    val selectedCategoryIdx = remember{ mutableIntStateOf(categoryIdx) }
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val focusRequester = remember{FocusRequester()}
    LaunchedEffect(key1 = true){
        lazyListState.animateScrollToItem(selectedCategoryIdx.intValue)
        focusRequester.requestFocus()
    }

    SearchContent(
        search = search,
        onSearchChanged = {text -> search = text} ,
        lazyListState = lazyListState,
        selectedCategoryIdx = selectedCategoryIdx,
        coroutineScope = coroutineScope,
        onBackClicked = onBackClicked,
        focusRequester = focusRequester,
        tutorData = TutorData.tutors,
        moveToTutorDetail = {  }
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchContent(
    search: String,
    onSearchChanged: (String) -> Unit,
    tutorData: List<TutorDummy>,
    moveToTutorDetail: (String) -> Unit,
    lazyListState: LazyListState,
    selectedCategoryIdx: MutableIntState,
    coroutineScope: CoroutineScope,
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    focusRequester: FocusRequester
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
            modifier = Modifier.padding(top = 10.dp),
            state = lazyListState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
        ){
            itemsIndexed(CategoriesData.categories){ index, category ->
                CategoryComponentSmall(category = category, modifier = Modifier.padding(start = 5.dp, end = 5.dp),
                    isSelected = index == selectedCategoryIdx.intValue,
                    onClick = {
                        selectedCategoryIdx.intValue = index
                        coroutineScope.launch {
                            lazyListState.animateScrollToItem(index)
                        }
                    }
                )
            }
        }
    }
}