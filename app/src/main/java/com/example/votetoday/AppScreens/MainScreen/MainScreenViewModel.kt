package com.example.votetoday.AppScreens.MainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.votetoday.Common.GestorBD.FBVotacion
import com.example.votetoday.Common.GestorBD.Votacion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("MutableCollectionMutableState")
@HiltViewModel
@OptIn(SavedStateHandleSaveableApi::class)
class MainScreenViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    var refreshing by savedStateHandle.saveable { mutableStateOf(true) }
    var votantes by savedStateHandle.saveable { mutableStateOf(mutableListOf<String>()) }
    var votaciones by savedStateHandle.saveable { mutableStateOf(mutableListOf<Votacion>()) }
    fun updateVotaciones(){

        viewModelScope.launch {

            FBVotacion.getVotaciones().collect{
                Log.i("updateView", it.toString())
                votaciones = it

                refreshing = false

            }

        }

    }
}