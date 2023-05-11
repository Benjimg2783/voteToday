package com.example.votetoday.Common.Navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens (val ruta : String, val icon:ImageVector?= null){

    object LoginScreen : Screens("LoginScreen", Icons.Filled.ExitToApp)
    object MainScreen: Screens("MainScreen", Icons.Filled.Home)
    object ProfileScreen: Screens("ProfileScreen", Icons.Filled.Person)


}