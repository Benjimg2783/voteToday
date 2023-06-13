package com.example.votetoday.AppScreens.NewVoteScreen

import android.annotation.SuppressLint
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@OptIn(SavedStateHandleSaveableApi::class)
@SuppressLint("MutableCollectionMutableState")
class NewVoteScreenViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    var asunto by savedStateHandle.saveable { mutableStateOf("") }
    var descripcion by savedStateHandle.saveable { mutableStateOf("") }
    var respuesta by savedStateHandle.saveable { mutableStateOf("") }
    var respuestas by savedStateHandle.saveable { mutableStateOf(mutableListOf<String>()) }
    var tema by savedStateHandle.saveable { mutableStateOf("") }
    var temas by savedStateHandle.saveable { mutableStateOf(mutableListOf<String>()) }

    fun onAsuntoChange(newAsunto: String) {
        this.asunto= newAsunto
    }
    fun onDescripcionChange(newDescripcion: String) {
        this.descripcion= newDescripcion
    }
    fun onRespuestaChange(newRespuesta: String) {
        this.respuesta= newRespuesta
    }
    fun onTemaChange(newTema: String) {
        this.tema= newTema
    }
}