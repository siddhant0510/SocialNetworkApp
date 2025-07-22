package com.example.socialnetworkapp.presentation.login

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel(){

    private val _usernameText = mutableStateOf("")
    val usernameText: State<String> = _usernameText

    private val _passwordText = mutableStateOf("")
    val passwordText: State<String> = _passwordText

    private val _showPassword = mutableStateOf(false)
    val showPassword: State<Boolean> = _showPassword

    private val _userNameError = mutableStateOf("")
    val userNameError: State<String> = _userNameError

    private val _passwordError = mutableStateOf("")
    val passwordError: State<String> = _passwordError

    fun setUsernameText(username: String){
        _usernameText.value = username
    }
    fun setPasswordText(password: String) {
        _passwordText.value = password
    }
    fun setIsUsernameError(error: String) {
        _userNameError.value = error
    }
    fun setIsPasswordError(error: String) {
        _passwordError.value = error
    }
    fun setShowPassword(showPassword: Boolean) {
        _showPassword.value = showPassword
    }
}