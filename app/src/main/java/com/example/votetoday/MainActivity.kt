package com.example.votetoday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.votetoday.Common.Navigation.NavigationHost
import com.example.votetoday.Common.Navigation.Screens
import com.example.votetoday.ui.theme.VoteTodayTheme
import com.google.firebase.auth.FirebaseAuth
import com.example.votetoday.Common.GetDeviceConfiguration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            VoteTodayTheme {
                Inicio()
            }
        }
    }
    @Composable
    fun Inicio() {
        GetDeviceConfiguration()

        val user = FirebaseAuth.getInstance().currentUser

        val startDestination: String = if (user == null){
            Screens.LoginScreen.ruta
        } else Screens.MainScreen.ruta

        val navController = rememberNavController()
        NavigationHost(navController,startDestination)
    }

}



