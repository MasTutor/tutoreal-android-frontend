package com.mastutor.tutoreal.data.remote.datahelper

import com.mastutor.tutoreal.data.remote.ProfileResponse
import com.mastutor.tutoreal.data.remote.ScheduleResponse
import com.mastutor.tutoreal.data.remote.TutorItem

data class HomeDataHelper(
    val profileResponse: ProfileResponse,
    val tutorsResponse: List<TutorItem>,
    val scheduleResponse: ScheduleResponse
)
