package com.mastutor.tutoreal.data.remote

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface TutorealApiService {
    @POST("/user/signup")
    suspend fun register(@Body requestBody: RequestBody): RegisterResponse

    @POST("/user/signin")
    suspend fun login(@Body requestBody: RequestBody): LoginResponse
}