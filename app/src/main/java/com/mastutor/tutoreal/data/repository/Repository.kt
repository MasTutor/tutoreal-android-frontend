package com.mastutor.tutoreal.data.repository

import android.content.Context
import android.net.Uri
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mastutor.tutoreal.data.pagingsource.TutorPagingSource
import com.mastutor.tutoreal.data.preferences.SessionPreferences
import com.mastutor.tutoreal.data.remote.BookResponse
//import com.mastutor.tutoreal.data.remote.EditResponse
import com.mastutor.tutoreal.data.remote.ImgurApiService
import com.mastutor.tutoreal.data.remote.ImgurResponse
import com.mastutor.tutoreal.data.remote.LoginResponse
import com.mastutor.tutoreal.data.remote.MatchedResponse
import com.mastutor.tutoreal.data.remote.ProfileResponse
import com.mastutor.tutoreal.data.remote.RegisterResponse
import com.mastutor.tutoreal.data.remote.ScheduleResponse
import com.mastutor.tutoreal.data.remote.TutorResponse
import com.mastutor.tutoreal.data.remote.TutorealApiService
import com.mastutor.tutoreal.data.remote.datahelper.HomeDataHelper
import com.mastutor.tutoreal.data.remote.datahelper.ShittyHelper
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
import kotlin.random.Random

class Repository @Inject constructor(
    private val tutorealApiService: TutorealApiService,
    private val sessionPreferences: SessionPreferences,
    private val imgurApiService: ImgurApiService,
    @ApplicationContext private val context: Context
) {
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



    fun getUserExist(): Flow<Boolean> {
        return sessionPreferences.getUserExist()
    }

    fun getUserToken(): Flow<String> {
        return sessionPreferences.getUserToken()
    }

    suspend fun deleteSession() {
        sessionPreferences.deleteSession()
    }

    fun getSchedule(token: String): Flow<UiState<ScheduleResponse>> {
        return flow {
            try {
                emit(UiState.Loading)
                val responseSchedule = tutorealApiService.getSchedule(token)
                emit(UiState.Success(responseSchedule))
            } catch (e: Exception) {
                emit(UiState.Failure(e))
            }
        }
    }

    fun getProfile(token: String): Flow<UiState<ProfileResponse>> {
        return flow {
            try {
                emit(UiState.Loading)
                val responseProfile = tutorealApiService.getProfile(token)
                emit(UiState.Success(responseProfile))
            } catch (e: Exception) {
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
    ): Flow<AuthUiState<RegisterResponse>> {
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
            } catch (e: Exception) {
                emit(AuthUiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun login(
        email: String,
        password: String
    ): Flow<AuthUiState<LoginResponse>> {
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
            } catch (e: Exception) {
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

    fun getTutor(id: String): Flow<UiState<TutorResponse>> {
        return flow {
            try {
                emit(UiState.Loading)
                val responseTutors = tutorealApiService.getTutor(id)
                emit(UiState.Success(responseTutors))
            } catch (e: Exception) {
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getHomeNeeded(token: String): Flow<UiState<HomeDataHelper>> {
        return flow {
            try {
                emit(UiState.Loading)
                val responseProfile = tutorealApiService.getProfile(token)
                val responseTutors = tutorealApiService.searchTutor(page = 1, size = 100).tutors.items.shuffled().take(4)
                val responseSchedule = tutorealApiService.getSchedule(token)
                emit(
                    UiState.Success(
                        HomeDataHelper(
                            responseProfile,
                            responseTutors,
                            responseSchedule
                        )
                    )
                )
            } catch (e: Exception) {
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getShitty(): Flow<UiState<List<ShittyHelper>>> {
        return flow {
            try {
                emit(UiState.Loading)
                val responseTutors = tutorealApiService.searchTutor(page = 1, size = 100).tutors.items.shuffled().take(5)
                val percent0 = Math.round(Random.nextDouble(90.00, 98.00) * 1000.0) / 1000.0
                val percent1 = Math.round(Random.nextDouble(80.00, percent0) * 1000.0) / 1000.0
                val percent2 = Math.round(Random.nextDouble(70.00, percent1) * 1000.0) / 1000.0
                val percent3 = Math.round(Random.nextDouble(65.00, percent2) * 1000.0) / 1000.0
                val percent4 = Math.round(Random.nextDouble(60.00, percent3) * 1000.0) / 1000.0
                val listItem = listOf(
                    ShittyHelper(responseTutors[0], percent0),
                    ShittyHelper(responseTutors[1], percent1),
                    ShittyHelper(responseTutors[2], percent2),
                    ShittyHelper(responseTutors[3], percent3),
                    ShittyHelper(responseTutors[0], percent4)
                )
                emit(UiState.Success(listItem))
            }catch(e: Exception){
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }
    fun getMatchedUser(token: String, category: String? = null): Flow<UiState<MatchedResponse>>{
        return flow{
            try {
                emit(UiState.Loading)
                val responseMatched = tutorealApiService.matchmaking(token, category)
                if (responseMatched.error == "true") {
                    emit(UiState.Failure(java.lang.Exception()))
                } else {
                    emit(UiState.Success(responseMatched))
                }
            }catch(e: Exception){
                emit(UiState.Failure(e))
            }


        }.flowOn(Dispatchers.IO)

    }

    fun newHistory(
        token: String,
        id: String,
        title: String,
        date: String,
        status: String
    ): Flow<AuthUiState<BookResponse>> {
        val jsonObject = JSONObject()
        jsonObject.put("tutorId", id)
        jsonObject.put("title", title)
        jsonObject.put("status", status)
        jsonObject.put("Date", date)

        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                emit(AuthUiState.Idle)
                emit(AuthUiState.Load)
                val responseHistory = tutorealApiService.newHistory(token, requestBody)
                emit(AuthUiState.Success(responseHistory))
            } catch (e: Exception) {
                emit(AuthUiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun postPersona(token: String, persona:List<Int>): Flow<AuthUiState<LoginResponse>>{
        val jsonObject = JSONObject()
        val stringPersona = persona.joinToString(separator = ",", prefix = "[", postfix = "]")
        jsonObject.put("Persona", stringPersona)
        val requestBody = jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                emit(AuthUiState.Idle)
                emit(AuthUiState.Load)
                val responsePersona = tutorealApiService.postPersona(token, requestBody)
                emit(AuthUiState.Success(responsePersona))
            }catch (e: Exception) {
                emit(AuthUiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun editProfile(
        token: String,
        name: String? = null,
        gender: Boolean? = null,
        nomor: String? = null,
        picture: String? = null
    ): Flow<UiState<ProfileResponse>> {

        val jsonObject = JSONObject()
        jsonObject.put("fullname", name)
        jsonObject.put("hasPenis", gender)
        jsonObject.put("noTelp", nomor)
        jsonObject.put("PhotoURL", picture)
        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                emit(UiState.Loading)
                val responseEdit = tutorealApiService.editProfile(token, requestBody)
                emit(UiState.Success(responseEdit))
            } catch (e: Exception) {
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}