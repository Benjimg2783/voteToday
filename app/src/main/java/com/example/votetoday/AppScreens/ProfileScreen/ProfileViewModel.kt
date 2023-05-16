package com.example.votetoday.AppScreens.ProfileScreen

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.votetoday.Common.GestorBD.FBUserQuerys
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    var uName by savedStateHandle.saveable { mutableStateOf(null ?: "") }

    fun onUnameChange(newUname: String) {
        FBUserQuerys.changeUserName(newUname)
        updateUname()
    }

    fun updateUname() {
        viewModelScope.launch {
            FBUserQuerys.getUserName().collect{ uname ->
                Log.i("FBAuth", "updateUname: ${uname}")
                uName = uname
            }
        }

    }
}