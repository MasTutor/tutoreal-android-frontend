package com.mastutor.tutoreal.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastutor.tutoreal.data.remote.LoginResponse
import com.mastutor.tutoreal.data.remote.RegisterResponse
import com.mastutor.tutoreal.data.repository.Repository
import com.mastutor.tutoreal.util.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: Repository): ViewModel(){
    private val _loginResponse: MutableStateFlow<AuthUiState<LoginResponse>> = MutableStateFlow(AuthUiState.Idle)
    val loginResponse: StateFlow<AuthUiState<LoginResponse>>
        get() = _loginResponse

    private val _emailLogin = mutableStateOf("")
    val emailLogin: State<String> get() = _emailLogin

    private val _passwordLogin = mutableStateOf("")
    val passwordLogin: State<String> get() = _passwordLogin

    private val _userExist = mutableStateOf(false)
    val userExist: State<Boolean> get() = _userExist

    fun changeEmailLogin(email: String){
        _emailLogin.value = email
    }

    fun tryUserExist(){
        viewModelScope.launch {
            repository.getUserExist().collect(){
                _userExist.value = it
            }
        }
    }

    fun changePasswordLogin(password: String){
        _passwordLogin.value = password
    }

    fun login(){
        viewModelScope.launch {
            repository.login(email = emailLogin.value, password = passwordLogin.value).collect {
                _loginResponse.value = it
            }
        }
    }


    private val _registerResponse: MutableStateFlow<AuthUiState<RegisterResponse>> = MutableStateFlow(AuthUiState.Idle)
    val registerResponse: StateFlow<AuthUiState<RegisterResponse>>
        get() = _registerResponse

    private val _emailRegister = mutableStateOf("")
    val emailRegister: State<String> get() = _emailRegister

    private val _fullNameRegister = mutableStateOf("")
    val fullNameRegister: State<String> get() = _fullNameRegister

    private val _passwordRegister = mutableStateOf("")
    val passwordRegister: State<String> get() = _passwordRegister

    private val _passwordConfirm = mutableStateOf("")
    val passwordConfirm: State<String> get() = _passwordConfirm

    private val _male = mutableStateOf(true)
    val male: State<Boolean> get() = _male

    fun changeEmailRegister(email: String) {
        _emailRegister.value = email
    }
    fun changeFullNameRegister(fullName: String) {
        _fullNameRegister.value = fullName
    }
    fun changeGender(male: Boolean) {
        _male.value = male
    }

    fun changePasswordRegister(password: String) {
        _passwordRegister.value = password
    }

    fun changePasswordConfirm(password: String) {
        _passwordConfirm.value = password
    }

    fun register(){
        viewModelScope.launch {
            repository.register(fullName = fullNameRegister.value, email = emailRegister.value, password = passwordRegister.value, male = male.value).collect{
                _registerResponse.value = it
            }
        }
    }

}