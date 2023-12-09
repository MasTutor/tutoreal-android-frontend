package com.mastutor.tutoreal.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastutor.tutoreal.data.remote.ScheduleResponse
import com.mastutor.tutoreal.data.repository.Repository
import com.mastutor.tutoreal.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _scheduleResponse: MutableStateFlow<UiState<ScheduleResponse>> = MutableStateFlow(
        UiState.Loading
    )
    val scheduleResponse: StateFlow<UiState<ScheduleResponse>> = _scheduleResponse
    private val _userToken = mutableStateOf("")

    fun getToken() {
        viewModelScope.launch {
            repository.getUserToken().collect {
                _userToken.value = it
            }
        }
    }

    fun getSchedule() {
        viewModelScope.launch {
            repository.getSchedule("Bearer ${_userToken.value}").collect {
                _scheduleResponse.value = it
            }
        }
    }
}