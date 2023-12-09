package com.mastutor.tutoreal.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastutor.tutoreal.data.remote.datahelper.HomeDataHelper
import com.mastutor.tutoreal.data.repository.Repository
import com.mastutor.tutoreal.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _homeResponse: MutableStateFlow<UiState<HomeDataHelper>> = MutableStateFlow(
        UiState.Loading
    )
    val homeResponse: StateFlow<UiState<HomeDataHelper>>
        get() = _homeResponse

    private val _userToken = mutableStateOf("")

    fun getToken() {
        viewModelScope.launch {
            repository.getUserToken().collect {
                _userToken.value = it
            }
        }
    }

    fun deleteSession() {
        viewModelScope.launch {
            repository.deleteSession()
        }
    }

    fun getHomeProcess() {
        viewModelScope.launch {
            repository.getHomeNeeded("Bearer ${_userToken.value}").collect {
                _homeResponse.value = it
            }
        }
    }
}