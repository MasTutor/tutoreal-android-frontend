package com.mastutor.tutoreal.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mastutor.tutoreal.data.remote.ProfileResponse
import com.mastutor.tutoreal.data.repository.Repository
import com.mastutor.tutoreal.util.AuthUiState
import com.mastutor.tutoreal.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository): ViewModel(){
    private val _userExist = mutableStateOf(false)
    val userExist: State<Boolean> get() = _userExist

    private val _userToken = mutableStateOf("")

    private val _profileResponse: MutableStateFlow<UiState<ProfileResponse>> = MutableStateFlow(UiState.Loading)
    val profileResponse: StateFlow<UiState<ProfileResponse>> = _profileResponse
    fun tryUserExist(){
        viewModelScope.launch {
            repository.getUserExist().collect(){
                _userExist.value = it
            }
        }
    }
    fun getToken(){
        viewModelScope.launch {
            repository.getUserToken().collect(){
                _userToken.value = it
            }
        }
    }
    fun deleteSession(){
        viewModelScope.launch {
            repository.deleteSession()
        }
    }
    fun getProfile(){
        viewModelScope.launch {
            repository.getProfile("Bearer ${_userToken.value}").collect(){
                _profileResponse.value = it
            }
        }
    }

    fun editProfile(field: String, data: String) {
        val token = "Bearer ${_userToken.value}"
        viewModelScope.launch {
            when (field) {
                "number" -> {
                    repository.editProfile(
                        token,
                        nomor = data
                    ).collect() {
                        _profileResponse.value = it
                    }
                }
                "name" -> {
                    repository.editProfile(
                        token,
                        name = data
                    ).collect() {
                        _profileResponse.value = it
                    }
                }
                "gender" -> {
                    repository.editProfile(
                        token,
                        gender = data.toBoolean()
                    ).collect() {
                        _profileResponse.value = it
                    }
                }
            }
        }
    }

    fun editPicture(imageUri: Uri){
        viewModelScope.launch {
            _profileResponse.value = UiState.Loading
            val result = imageUri.let { repository.uploadImage(it) }

            result.fold(
                onSuccess = { image ->
                    repository.editProfile(
                        "Bearer ${_userToken.value}",
                        picture = image
                    ).collect() {
                        _profileResponse.value = it
                    }
                },
                onFailure = {
                }
            ).toString()
        }
    }
}