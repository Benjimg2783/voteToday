package com.example.votetoday.AppScreens.ProfileScreen

import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.votetoday.Common.GestorBD.FBAuth
import com.example.votetoday.Common.GestorBD.FBUserQuerys
import com.example.votetoday.Common.GestorBD.FBVotacion
import com.example.votetoday.Common.GestorBD.Votacion
import com.example.votetoday.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(SavedStateHandleSaveableApi::class)
@HiltViewModel
class ProfileViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    var uName by savedStateHandle.saveable { mutableStateOf(null ?: "") }
    var textFieldEnabled by savedStateHandle.saveable { mutableStateOf(false) }
    var dialogState by savedStateHandle.saveable { mutableStateOf(false) }
    var galeryState by savedStateHandle.saveable { mutableStateOf(false) }
    var fotoPerfilUrL by savedStateHandle.saveable { mutableStateOf("") }
    var refresh by savedStateHandle.saveable { mutableStateOf(false) }
    var fotoPerfilDefault = R.drawable.foto_perfil_default


    fun onUnameChange(newUname: String) {
        uName = newUname.trim()
    }

    fun updateUname() {
        viewModelScope.launch {
            FBUserQuerys.getUserName().collect { uname ->
                Log.i("FBAuth", "updateUname: $uname")
                uName = uname
            }
        }
    }

    fun updatePfp(context: Context) {
        viewModelScope.launch {
            fotoPerfilUrL = FBUserQuerys.getFotoPerfil(FBAuth.getUserUID(), context).toString()
        }

    }
    fun updateVotaciones(): Flow<MutableList<Votacion>> = flow{
        FBVotacion.getVotacionesPropias().collect{
            Log.i("updateView", it.toString())
            emit(it)
        }
    }
}

