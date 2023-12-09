package com.mastutor.tutoreal.data.remote.datahelper

import com.mastutor.tutoreal.data.remote.ProfileResponse
import com.mastutor.tutoreal.data.remote.ScheduleResponse
import com.mastutor.tutoreal.data.remote.TutorsResponse

data class HomeDataHelper(
    val profileResponse: ProfileResponse,
    val tutorsResponse: TutorsResponse,
    val scheduleResponse: ScheduleResponse
    //TODO: Add History Response
)
