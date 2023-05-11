package com.example.votetoday.AppScreens.ProfileScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.votetoday.Common.DeviceConfiguration.Companion.heightPercentage
import com.example.votetoday.Common.DeviceConfiguration.Companion.widthPercentage
import com.example.votetoday.Common.GestorBD.Usuario
import com.example.votetoday.Common.Navigation.NavigationFunctions
import com.example.votetoday.Common.SystemBarColor
import com.example.votetoday.R
import com.example.votetoday.ui.theme.VoteTodayBackground
import com.example.votetoday.ui.theme.VoteTodayOrange

@Preview
@Composable
fun PreviewProfile() {
    ProfileScreen(navController = rememberNavController())
}



@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel = hiltViewModel()) {
    Scaffold(
        bottomBar = { NavigationFunctions.NavBar(navController = navController as NavHostController) }
    ){
        SystemBarColor(color = VoteTodayOrange)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VoteTodayBackground)
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = heightPercentage(2))
                    .height(heightPercentage(10)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                //crea una imagen circular a la izquierda de la fila, que se puede cambiar y contenga R.drawable.logonobackground
                Image(
                    painter = painterResource(id = R.drawable.foto_perfil_default),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .width(widthPercentage(20))
                        .height(heightPercentage(10))
                        .clip(CircleShape)
                )
                Text(
                    text = "Nombre de usuario",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                )

            }
            LazyColumn(
                modifier = Modifier.run {
                    width(widthPercentage(92))
                                .height(heightPercentage(93))
                                .padding(
                                    top=heightPercentage(20),
                                    start = widthPercentage(8)
                                )
                                .background(Color.White, RoundedCornerShape(5))
                }
            ) {

            }
        }
    }
}