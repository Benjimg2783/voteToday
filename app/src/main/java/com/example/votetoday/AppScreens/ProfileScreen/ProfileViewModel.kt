package com.example.votetoday.AppScreens.ProfileScreen

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.votetoday.Common.GestorBD.FBUserQuerys
import com.example.votetoday.R
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel(){

    @OptIn(SavedStateHandleSaveableApi::class)
    var uName by savedStateHandle.saveable { mutableStateOf(null ?: "") }
    @OptIn(SavedStateHandleSaveableApi::class)
    var textFieldEnabled by savedStateHandle.saveable { mutableStateOf(false) }
    @OptIn(SavedStateHandleSaveableApi::class)
    var dialogState by savedStateHandle.saveable { mutableStateOf(false) }
    var galeryState by savedStateHandle.saveable { mutableStateOf(false) }
    var icon = if (!textFieldEnabled) {
        R.drawable.edit
    } else {
        R.drawable.done
    }


    var fotoPerfil = R.drawable.foto_perfil_default


    fun onUnameChange(newUname: String) {
        uName = newUname.trim()
    }

    fun updateUname() {
        viewModelScope.launch {
            FBUserQuerys.getUserName().collect{ uname ->
                Log.i("FBAuth", "updateUname: $uname")
                uName = uname
            }
        }
    }
}