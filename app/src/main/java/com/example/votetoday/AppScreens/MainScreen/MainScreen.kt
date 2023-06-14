package com.example.votetoday.AppScreens.MainScreen

import android.annotation.SuppressLint
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.votetoday.Common.DeviceConfiguration.Companion.heightPercentage
import com.example.votetoday.Common.DeviceConfiguration.Companion.widthPercentage
import com.example.votetoday.Common.GestorBD.FBAuth
import com.example.votetoday.Common.GestorBD.FBVotacion.sumarRespuesta
import com.example.votetoday.Common.GestorBD.Votacion
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

    viewModel.refreshing = true
    viewModel.updateVotaciones()

    Scaffold(bottomBar = { NavBar(navController = navController as NavHostController) }) {
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
                Image(painter = painterResource(id = R.drawable.logonobackground),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .height(heightPercentage(10))
                        .clip(RoundedCornerShape(5))
                        .clickable {
                            viewModel.refreshing = true
                            viewModel.updateVotaciones()
                        })
            }
            //Columna de votaciones
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = heightPercentage(15), bottom = heightPercentage(7))
                    .background(Color.White)
            ) {

                //Lista de votaciones
                if (viewModel.refreshing) {
                    item {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(
                                color = VoteTodayOrange,
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(widthPercentage(13))
                                    .padding(top = heightPercentage(3))
                            )
                        }
                    }
                } else {
                    items(viewModel.votaciones.size) { votacion ->
                        MostrarVotacion(
                            votacion = viewModel.votaciones[votacion], viewModel = viewModel
                        )
                    }
                }

            }

            // region FloationgActionButton
            // Boton de crear votacion
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = heightPercentage(10), end = widthPercentage(8))
                    .size(heightPercentage(9))
                    .border(1.dp, Color.White, CircleShape), onClick = {
                    navController.navigate("NewVoteScreen")
                }, shape = CircleShape, backgroundColor = VoteTodayOrange
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.post),
                    tint = Color.White,
                    contentDescription = "Crear una nueva votacion"
                )
            }
            // endregion
        }
    }
}

@Composable
fun MostrarVotacion(votacion: Votacion, viewModel: MainScreenViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = heightPercentage(2),
                bottom = heightPercentage(2),
                start = widthPercentage(5),
                end = widthPercentage(5)
            )
            .background(Color.White),
        //TODO() .clickable { },
        elevation = 10.dp,
    ) {
        Column {
            // region Titulo
            Text(
                text = votacion.asunto, modifier = Modifier.padding(
                    top = heightPercentage(3),
                    bottom = heightPercentage(2),
                    start = widthPercentage(4),
                    end = widthPercentage(2)
                ), color = Color.Black
            )

            // endregion
            // region Descripcion
            if (!votacion.descripcion.isNullOrBlank()) {
                Text(
                    text = votacion.descripcion, modifier = Modifier.padding(
                        top = heightPercentage(1),
                        bottom = heightPercentage(2),
                        start = widthPercentage(4),
                        end = widthPercentage(2)
                    ), color = Color.Black
                )
            }
            // endregion
            // region Respuestas
            Column(
                modifier = Modifier.padding(
                    top = heightPercentage(2),
                    bottom = heightPercentage(2),
                    start = widthPercentage(4),
                    end = widthPercentage(2)
                ), verticalArrangement = Arrangement.SpaceEvenly
            ) {
                if (votacion.votantes.isNullOrEmpty() || !votacion.votantes!!.contains(FBAuth.getUserUID())) {
                    votacion.respuestas.forEach { respuesta ->
                        Button(
                            onClick = {
                                votacion.recuento[votacion.respuestas.indexOf(respuesta)] += 1
                                if (!votacion.votantes.isNullOrEmpty()) {
                                    viewModel.votantes = votacion.votantes!!
                                }
                                FBAuth.getUserUID()?.let { viewModel.votantes.add(it) }
                                votacion.votantes = viewModel.votantes
                                sumarRespuesta(votacion)
                                viewModel.refreshing = true
                                viewModel.updateVotaciones()

                            },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = VoteTodayOrange
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    bottom = heightPercentage(2)
                                )
                                .height(heightPercentage(10)),
                        ) {
                            Text(
                                text = respuesta, modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = heightPercentage(2),
                                        bottom = heightPercentage(4),
                                        start = widthPercentage(4),
                                        end = widthPercentage(2)
                                    ), color = Color.White, textAlign = TextAlign.Center
                            )
                        }
                    }
                } else {
                    Row {


                        Text(
                            text = "Voto registrado con éxito", modifier = Modifier.padding(
                                top = heightPercentage(0),
                                bottom = heightPercentage(2),
                                start = widthPercentage(2),
                                end = widthPercentage(1)
                            ), color = Color(0xFF8BC34A)
                        )
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = "Voto registrado con éxito",
                            tint = Color(0xFF8BC34A)
                        )
                    }
                }
            }
            // endregion
        }
    }
}
