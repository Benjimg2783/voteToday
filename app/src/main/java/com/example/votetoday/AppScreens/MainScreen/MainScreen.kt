package com.example.votetoday.AppScreens.MainScreen

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.votetoday.Common.DeviceConfiguration.Companion.heightPercentage
import com.example.votetoday.Common.DeviceConfiguration.Companion.widthPercentage
import com.example.votetoday.Common.Navigation.NavigationFunctions.Companion.NavBar
import com.example.votetoday.Common.SystemBarColor
import com.example.votetoday.R
import com.example.votetoday.ui.theme.VoteTodayBackground
import com.example.votetoday.ui.theme.VoteTodayOrange

@Preview
@Composable
fun PreviewMainScreen() {
    MainScreen(navController = rememberNavController())
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(navController: NavController, viewModel: MainScreenViewModel = hiltViewModel()) {
    Scaffold(
        bottomBar = { NavBar(navController = navController as NavHostController) }
    ) {
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
                //Logo
                Image(
                    painter = painterResource(id = R.drawable.logonobackground),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(heightPercentage(10))
                        .clip(RoundedCornerShape(5))
                )
            }
            //Columna de votaciones
            LazyColumn(
                modifier = Modifier
                    .width(widthPercentage(67))
                    .height(heightPercentage(90))
                    .padding(top = heightPercentage(15), start = widthPercentage(5))
                    .background(Color.White, shape = RoundedCornerShape(5)),
            ) {

            }
            Column(
                modifier = Modifier
                    .padding(
                        start = widthPercentage(70),
                        top = heightPercentage(17)
                    )
                    .height(heightPercentage(90)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                //Boton de crear votacion
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.post),
                    tint=Color.White,
                    contentDescription = "Crear una nueva votacion",
                    modifier = Modifier
                        .height(heightPercentage(9))
                        .width(widthPercentage(18))
                        .clip(CircleShape)
                        .background(VoteTodayOrange)
                        .padding(top = heightPercentage(1), bottom = heightPercentage(1))
                        .clickable(onClick = { navController.navigate("NewVoteScreen") })
                )
                //Lista de temas recomendados
                Text(
                    text = "Temas recomendados",
                    color = Color.Black,
                    modifier = Modifier.padding(top = heightPercentage(4))
                )
            }
        }
    }
}
