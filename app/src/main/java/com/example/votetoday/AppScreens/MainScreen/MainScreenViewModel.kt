package com.example.votetoday.AppScreens.MainScreen

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import com.example.votetoday.AppScreens.LoginAndRegistre.LoginScreen
import com.example.votetoday.Common.GestorBD.FBVotacion
import com.example.votetoday.Common.GestorBD.Votacion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject
@SuppressLint("MutableCollectionMutableState")
@HiltViewModel
@OptIn(SavedStateHandleSaveableApi::class)
class MainScreenViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

    var refreshing by savedStateHandle.saveable { mutableStateOf(false) }
    var votantes by savedStateHandle.saveable { mutableStateOf(mutableListOf<String>()) }
    var refresh by savedStateHandle.saveable { mutableStateOf(false) }

    fun updateVotaciones(): Flow<MutableList<Votacion>> = flow {

        FBVotacion.getVotaciones().collect{
            Log.i("updateView", it.toString())
            emit(it)
        }
    }
}