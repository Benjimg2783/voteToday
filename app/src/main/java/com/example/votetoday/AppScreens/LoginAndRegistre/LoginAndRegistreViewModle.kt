package com.example.votetoday.AppScreens.LoginAndRegistre


import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
@OptIn(SavedStateHandleSaveableApi::class)
class LoginAndRegistreViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    var isLogging by savedStateHandle.saveable { mutableStateOf(true) }

    var email by savedStateHandle.saveable { mutableStateOf("") }
    var password by savedStateHandle.saveable { mutableStateOf("") }
    var repeatPassword by savedStateHandle.saveable { mutableStateOf("") }
    var userName by savedStateHandle.saveable { mutableStateOf("") }

    var popNContinue by savedStateHandle.saveable { mutableStateOf(false) }

    fun onEmailChange(newValue: String) {
        this.email = newValue.trim()
    }

    fun onPasswordChange(newValue: String) {
        this.password = newValue.trim()
    }

    fun onRepeatPasswordChange(newValue: String) {
        this.repeatPassword = newValue.trim()
    }
    fun onUserNameChange(newValue: String) {
        this.userName = newValue.trim()
    }

    fun turnPopNContinue(){
        popNContinue = !popNContinue
    }

}