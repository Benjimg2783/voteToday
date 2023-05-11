package com.example.votetoday.Common.Navigation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.SavedStateHandleSaveableApi
import androidx.lifecycle.viewmodel.compose.saveable
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
@HiltViewModel
class NavigationHostViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    @OptIn(SavedStateHandleSaveableApi::class)
    var dialogState by savedStateHandle.saveable { mutableStateOf(false) }
}