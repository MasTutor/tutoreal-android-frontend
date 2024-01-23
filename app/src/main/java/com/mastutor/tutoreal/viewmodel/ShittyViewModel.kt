package com.mastutor.tutoreal.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastutor.tutoreal.data.remote.MatchedResponse
import com.mastutor.tutoreal.data.remote.datahelper.ShittyHelper
import com.mastutor.tutoreal.data.repository.Repository
import com.mastutor.tutoreal.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShittyViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _shittyResponse: MutableStateFlow<UiState<List<ShittyHelper>>> = MutableStateFlow(
        UiState.Loading
    )
    val shittyResponse: StateFlow<UiState<List<ShittyHelper>>> = _shittyResponse
    private val _matchedResponse: MutableStateFlow<UiState<MatchedResponse>> = MutableStateFlow(UiState.Loading)
    val matchedResponse: StateFlow<UiState<MatchedResponse>> = _matchedResponse

    private val _userToken = mutableStateOf("")

    fun getToken() {
        viewModelScope.launch {
            repository.getUserToken().collect {
                _userToken.value = it
            }
        }
    }

    fun getShitty() {
        viewModelScope.launch {
            repository.getShitty().collect{
                _shittyResponse.value = it
            }

        }
    }
    fun getMatched(category: String? = null){
        viewModelScope.launch {
            repository.getMatchedUser("Bearer ${_userToken.value}", category).collect(){
                _matchedResponse.value = it
            }
        }
    }
}