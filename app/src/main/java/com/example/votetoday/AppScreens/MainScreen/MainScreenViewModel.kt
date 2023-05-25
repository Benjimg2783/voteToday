package com.example.votetoday.AppScreens.MainScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
@OptIn(SavedStateHandleSaveableApi::class)
class MainScreenViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {

}