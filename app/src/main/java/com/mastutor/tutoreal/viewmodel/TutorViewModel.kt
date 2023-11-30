package com.mastutor.tutoreal.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastutor.tutoreal.data.remote.ProfileResponse
import com.mastutor.tutoreal.data.remote.TutorResponse
import com.mastutor.tutoreal.data.repository.Repository
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

    fun getTutor(id: String){
        viewModelScope.launch {
            repository.getTutor(id).collect() { data ->
                _tutorResponse.value = data
                Log.d("Tutor", data.toString())
            }
        }
    }
}