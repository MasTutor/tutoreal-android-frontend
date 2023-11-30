package com.mastutor.tutoreal.data.remote

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface TutorealApiService {
    @POST("/user/signup")
    suspend fun register(@Body requestBody: RequestBody): RegisterResponse

    @POST("/user/signin")
    suspend fun login(@Body requestBody: RequestBody): LoginResponse

    @GET("/user/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String,
    ):ProfileResponse

    @GET("/tutor/alltutors")
    suspend fun searchTutor(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("specialization") specialization: String,
        @Query("category") category: String? = null
    ): TutorsResponse

    @GET("/tutor/detail")
    suspend fun getTutor(
        @Query("tutor_id") tutorId: String
    ): TutorResponse
}