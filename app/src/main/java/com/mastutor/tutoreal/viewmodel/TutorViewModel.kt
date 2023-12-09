package com.mastutor.tutoreal.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastutor.tutoreal.data.remote.BookResponse
import com.mastutor.tutoreal.data.remote.ProfileResponse
import com.mastutor.tutoreal.data.remote.ScheduleResponse
import com.mastutor.tutoreal.data.remote.TutorDetail
import com.mastutor.tutoreal.data.remote.TutorResponse
import com.mastutor.tutoreal.data.repository.Repository
import com.mastutor.tutoreal.util.AuthUiState
import com.mastutor.tutoreal.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TutorViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _tutorResponse: MutableStateFlow<UiState<TutorResponse>> = MutableStateFlow(
        UiState.Loading)
    val tutorResponse: StateFlow<UiState<TutorResponse>> = _tutorResponse

    private val _tutorData = mutableStateOf(
        TutorDetail("", "", "", 1, "", "", "", "", "", "", "")
    )
    val tutorData: State<TutorDetail> get() = _tutorData

    private val _userToken = mutableStateOf("")

    fun getToken(){
        viewModelScope.launch {
            repository.getUserToken().collect(){
                _userToken.value = it
            }
        }
    }

    fun getTutor(id: String){
        viewModelScope.launch {
            repository.getTutor(id).collect() { data ->
                _tutorResponse.value = data
                Log.d("Tutor", data.toString())
            }
        }
    }

    fun setData(data: TutorDetail) {
        _tutorData.value = data
    }

    private val _sessionTitle = mutableStateOf("")
    val sessionTitle: State<String> get() = _sessionTitle

    private val _sessionDate = mutableStateOf("")
    val sessionDate: State<String> get() = _sessionDate

    fun changeTitle(title: String) {
        _sessionTitle.value = title
    }

    fun changeDate(date: String) {
        _sessionDate.value = date
    }

    private val _titleError = mutableStateOf(true)
    val titleError: State<Boolean> get() = _titleError

    private val _dateError = mutableStateOf(true)
    val dateError: State<Boolean> get() = _dateError

    fun changeTitleError(error: Boolean) {
        _titleError.value = error
    }

    fun changeDateError(error: Boolean) {
        _dateError.value = error
    }

    private val _bookResponse: MutableStateFlow<AuthUiState<BookResponse>> =
        MutableStateFlow(AuthUiState.Idle)
    val bookResponse: StateFlow<AuthUiState<BookResponse>>
        get() = _bookResponse

    fun bookTutor(id: String, title: String, date: String, status: String) {
        viewModelScope.launch {
            repository.newHistory(
                token = "Bearer ${_userToken.value}",
                id = id,
                title = title,
                date = date,
                status = status
            ).collect {
                _bookResponse.value = it
            }
        }
    }
}