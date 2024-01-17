package com.mastutor.tutoreal.viewmodel

import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastutor.tutoreal.data.remote.LoginResponse
import com.mastutor.tutoreal.data.repository.Repository
import com.mastutor.tutoreal.util.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SurveyViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _personaResponse: MutableStateFlow<AuthUiState<LoginResponse>> =
        MutableStateFlow(AuthUiState.Idle)

    private val _currentPageIndex = mutableIntStateOf(0)
    val currentPageIndex : State<Int> get() = _currentPageIndex

    private val _answer = mutableIntStateOf(3)
    val answer: State<Int> get() = _answer
    val personaResponse: StateFlow<AuthUiState<LoginResponse>>
        get() = _personaResponse
    private val _answers = mutableStateListOf(
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3,
        3
    )
    private val _userToken = mutableStateOf("")

    fun getToken() {
        viewModelScope.launch {
            repository.getUserToken().collect {
                _userToken.value = it
            }
        }
    }
    val answers: SnapshotStateList<Int> get() = _answers
    fun addAnswers(answer: MutableIntState, currentIndex: MutableIntState) {
        _answers[currentIndex.intValue] = answer.intValue
    }

    fun postPersona(){
        viewModelScope.launch {
            repository.postPersona("Bearer ${_userToken.value}", _answers.toList()).collect(){
                _personaResponse.value = it
            }
        }

    }


}