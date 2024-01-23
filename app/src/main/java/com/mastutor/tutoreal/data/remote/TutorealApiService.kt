package com.mastutor.tutoreal.data.remote

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface TutorealApiService {
    @POST("/user/signup")
    suspend fun register(@Body requestBody: RequestBody): RegisterResponse

    @POST("/user/signin")
    suspend fun login(@Body requestBody: RequestBody): LoginResponse

    @GET("/user/profile")
    suspend fun getProfile(
        @Header("Authorization") token: String,
    ): ProfileResponse

    @GET("/tutor/alltutors")
    suspend fun searchTutor(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("specialization") specialization: String? = null,
        @Query("category") category: String? = null
    ): TutorsResponse

    @GET("/tutor/detail")
    suspend fun getTutor(
        @Query("tutor_id") tutorId: String
    ): TutorResponse

    @GET("/user/history")
    suspend fun getSchedule(
        @Header("Authorization") token: String,
    ): ScheduleResponse

    @PUT("/user/editprofile")
    suspend fun editProfile(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ): ProfileResponse

    @POST("/user/new-history")
    suspend fun newHistory(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ): BookResponse

    @POST("/user/personality")
    suspend fun postPersona(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ): LoginResponse

    @GET("/user/matchmaking")
    suspend fun matchmaking(
        @Header("Authorization") token: String,
        @Query("category") category: String? = null
    ): MatchedResponse
}