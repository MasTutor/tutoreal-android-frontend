package com.mastutor.tutoreal.data.repository

import com.mastutor.tutoreal.data.preferences.SessionPreferences
import com.mastutor.tutoreal.data.remote.LoginResponse
import com.mastutor.tutoreal.data.remote.RegisterResponse
import com.mastutor.tutoreal.data.remote.TutorealApiService
import com.mastutor.tutoreal.util.AuthUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class Repository @Inject constructor(
    private val tutorealApiService: TutorealApiService,
    private val sessionPreferences: SessionPreferences
){

    fun getUserExist(): Flow<Boolean>{
        return sessionPreferences.getUserExist()
    }
    fun getUserToken(): Flow<String>{
        return sessionPreferences.getUserToken()
    }

    suspend fun deleteSession(){
        sessionPreferences.deleteSession()
    }
    fun register(
        fullName: String,
        email: String,
        password: String,
        male: Boolean = true
    ): Flow<AuthUiState<RegisterResponse>>
    {
        val jsonObject = JSONObject()
        jsonObject.put("fullname", fullName)
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        jsonObject.put("hasPenis", male)

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
}