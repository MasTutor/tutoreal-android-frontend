package com.mastutor.tutoreal.ui.screen.schedule

import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mastutor.tutoreal.data.local.StatusData
import com.mastutor.tutoreal.data.remote.HistoryDataItem
import com.mastutor.tutoreal.ui.components.ScheduleComponent
import com.mastutor.tutoreal.ui.screen.failure.FailureScreen
import com.mastutor.tutoreal.util.UiState
import com.mastutor.tutoreal.viewmodel.ScheduleViewModel

@Composable
fun ScheduleScreen(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    viewModel: ScheduleViewModel = hiltViewModel()
) {
    SideEffect {
        viewModel.getToken()
    }
    LaunchedEffect(key1 = true) {
        viewModel.getSchedule()
    }
    viewModel.scheduleResponse.collectAsState(initial = UiState.Loading).value.let { uiState ->
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
                ScheduleContent(
                    schedules = uiState.data?.historyData,
                    onBackClicked = {
                        navHostController.navigateUp()
                    },
                    modifier = Modifier.padding(10.dp)
                )
            }

            is UiState.Failure -> {
                FailureScreen(onRefreshClicked = { viewModel.getSchedule() })
            }
        }
    }

}

@Composable
fun ScheduleContent(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit,
    schedules: List<HistoryDataItem?>?
) {
    Row(
        modifier = Modifier
            .padding(start = 10.dp, top = 5.dp)
            .offset(y = 5.dp)
            .clickable { onBackClicked() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = "Arrow Forward",
            modifier = Modifier.padding(end = 8.dp),
            tint = Color.Black
        )

    }
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = "Histori Jadwal",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 8.dp, top = 20.dp)
                .fillMaxWidth()
        )
        if (schedules != null) {
            if (schedules.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(schedules) { schedule ->
                        if (schedule != null) {
                            ScheduleComponent(
                                modifier = Modifier.padding(5.dp),
                                title = schedule.sessionName.toString(),
                                tutorName = schedule.tutorName.toString(),
                                date = schedule.date.toString(),
                                status = if (schedule.status.toString() == "OnGoing") {
                                    StatusData(
                                        status = schedule.status.toString(),
                                        color = Color.Yellow
                                    )
                                } else if (schedule.status.toString() == "Completed") {
                                    StatusData(
                                        status = schedule.status.toString(),
                                        color = Color.Green
                                    )
                                } else {
                                    StatusData(
                                        status = schedule.status.toString(),
                                        color = Color.Red
                                    )
                                },
                            )
                        }
                    }
                }
            } else {
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
                    Text(
                        text = "Jadwal Kosong",
                        style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary),
                        modifier = Modifier.align(
                            Alignment.Center
                        )
                    )
                }
            }
        }
    }
}