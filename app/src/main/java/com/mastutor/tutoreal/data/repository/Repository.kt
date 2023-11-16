package com.mastutor.tutoreal.data.repository

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
    private val tutorealApiService: TutorealApiService
){
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
}