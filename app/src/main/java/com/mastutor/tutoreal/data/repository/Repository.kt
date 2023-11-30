package com.mastutor.tutoreal.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mastutor.tutoreal.data.pagingsource.TutorPagingSource
import android.content.Context
import android.net.Uri
import com.mastutor.tutoreal.data.preferences.SessionPreferences
import com.mastutor.tutoreal.data.remote.ImgurApiService
import com.mastutor.tutoreal.data.remote.ImgurResponse
import com.mastutor.tutoreal.data.remote.LoginResponse
import com.mastutor.tutoreal.data.remote.ProfileResponse
import com.mastutor.tutoreal.data.remote.RegisterResponse
import com.mastutor.tutoreal.data.remote.TutorResponse
import com.mastutor.tutoreal.data.remote.TutorealApiService
import com.mastutor.tutoreal.util.AuthUiState
import com.mastutor.tutoreal.util.UiState
import com.mastutor.tutoreal.util.uriToFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val tutorealApiService: TutorealApiService,
    private val sessionPreferences: SessionPreferences,
    private val imgurApiService: ImgurApiService,
    @ApplicationContext private val context: Context
){
    fun searchTutors(
        specialization: String,
        category: String? = null
    ) = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = {
            TutorPagingSource(
                tutorealApiService, specialization, category
            )
        }
    ).flow

    fun getUserExist(): Flow<Boolean>{
        return sessionPreferences.getUserExist()
    }
    fun getUserToken(): Flow<String>{
        return sessionPreferences.getUserToken()
    }

    suspend fun deleteSession(){
        sessionPreferences.deleteSession()
    }
    fun getProfile(token: String):Flow<UiState<ProfileResponse>>{
        return flow {
            try {
                emit(UiState.Loading)
                val responseProfile = tutorealApiService.getProfile(token)
                emit(UiState.Success(responseProfile))
            }
            catch (e: Exception){
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }
    fun register(
        fullName: String,
        email: String,
        password: String,
        male: Boolean = true,
        photoUrl: String
    ): Flow<AuthUiState<RegisterResponse>>
    {
        val jsonObject = JSONObject()
        jsonObject.put("fullname", fullName)
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        jsonObject.put("hasPenis", male)
        jsonObject.put("PhotoURL", photoUrl)

        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                emit(AuthUiState.Idle)
                emit(AuthUiState.Load)
                val responseRegister = tutorealApiService.register(requestBody)
                emit(AuthUiState.Success(responseRegister))
            }
            catch (e: Exception){
                emit(AuthUiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun login(
        email: String,
        password: String
    ):Flow<AuthUiState<LoginResponse>>
    {
        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("password", password)

        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                emit(AuthUiState.Idle)
                emit(AuthUiState.Load)
                val responseLogin = tutorealApiService.login(requestBody)
                responseLogin.loginResult?.token?.accessToken.let {
                    if (it != null) {
                        sessionPreferences.startSession(true, it)
                    }
                }
                emit(AuthUiState.Success(responseLogin))
            }catch (e: Exception){
                emit(AuthUiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun uploadImage(image: Uri): Result<String> {
        return try {
            val response: Response<ImgurResponse>
            if (image == Uri.EMPTY) {
                val link = "https://i.imgur.com/INmQ7WW.png"
                val jsonObject = JSONObject()
                jsonObject.put("image", link)

                val requestBody =
                    jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

                response = imgurApiService.uploadFile(
                    requestBody
                )
            } else {
                val file = uriToFile(image, context)
                val filePart =
                    MultipartBody.Part.createFormData("image", null, file.asRequestBody())

                response = imgurApiService.uploadFile(
                    filePart
                )
            }

            if (response.isSuccessful) {
                val link = response.body()?.upload?.link ?: ""
                Result.success(link)
            } else {
                Result.failure(Exception("Unknown network Exception."))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getTutor(id: String):Flow<UiState<TutorResponse>>{
        return flow {
            try {
                emit(UiState.Loading)
                val responseProfile = tutorealApiService.getTutor(id)
                emit(UiState.Success(responseProfile))
            }
            catch (e: Exception){
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}