package com.mastutor.tutoreal.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mastutor.tutoreal.data.local.CategoriesData
import com.mastutor.tutoreal.data.local.Category
import com.mastutor.tutoreal.ui.theme.TutorealTheme
import kotlinx.coroutines.launch

@Composable
fun CategoryComponentBig(
    category: Category,
    modifier: Modifier = Modifier
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .size(120.dp)
            .background(color = MaterialTheme.colorScheme.primary)
        ) {
        Icon(imageVector = category.icon, contentDescription = "Category", tint = Color.White, modifier = Modifier
            .size(78.dp))
        Text(text = category.name, color = Color.White, style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))

    }
}

@Composable
fun CategoryComponentSmall(
    category: Category,
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    onClick: () -> Unit,
){
    Row( verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .height(40.dp)
            .background(color = if (!isSelected) Color.White else MaterialTheme.colorScheme.primary)
            .clickable { onClick() }
    )
    {
        Box(modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clip(CircleShape)
            .background(color = if (!isSelected) MaterialTheme.colorScheme.primary else Color.White)
            ) {
            Icon(imageVector = category.icon, contentDescription = "Category", tint = if(!isSelected) Color.White else MaterialTheme.colorScheme.primary, modifier = Modifier.padding(4.dp))
        }
        Text(text = category.name, color = if(!isSelected) Color.Black else Color.White, style = MaterialTheme.typography.bodySmall)
        Spacer(modifier = Modifier.width(10.dp))
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Preview(
    device = "id:pixel_5",
    showSystemUi = true,
    backgroundColor = 0xFFE8F0F9,
    showBackground = true
)
@Composable
fun CategoryComponentPreview(){
    TutorealTheme {
        val selectedCategoryIdx = remember{ mutableIntStateOf(4) }
        val lazyListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        LaunchedEffect(key1 = true){
            lazyListState.animateScrollToItem(selectedCategoryIdx.intValue)
        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){

            LazyRow(){
                items(CategoriesData.categories){category ->
                    CategoryComponentBig(category = category, modifier = Modifier.padding(start = 5.dp, end = 5.dp))
                }
            }
            LazyRow(
                modifier = Modifier.padding(top = 10.dp),
                state = lazyListState,
                flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState)
                ){
                itemsIndexed(CategoriesData.categories){index, category ->
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
}