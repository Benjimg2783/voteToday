package com.example.votetoday.AppScreens.NewVoteScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.VectorPainter
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.votetoday.Common.DeviceConfiguration.Companion.heightPercentage
import com.example.votetoday.Common.DeviceConfiguration.Companion.widthPercentage
import com.example.votetoday.Common.GestorBD.FBVotacion
import com.example.votetoday.Common.GestorBD.Votacion
import com.example.votetoday.R
import com.example.votetoday.ui.theme.VoteTodayBackground
import com.example.votetoday.ui.theme.VoteTodayOrange

@Preview
@Composable
fun PreviewNewVoteScreenView() {
    NewVoteScreen(rememberNavController())
}

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NewVoteScreen(
    navController: NavController,
    viewModel: NewVoteScreenViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            TopBar(navController = navController)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(VoteTodayBackground)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box() {
                    LazyColumn(modifier= Modifier
                        .padding(
                            top = heightPercentage(7),
                            start = widthPercentage(10),
                            end = widthPercentage(10),
                            bottom = heightPercentage(7)
                        )
                        .background(Color.White, RoundedCornerShape(5))) {

                        item {
                            AsuntoTextField(
                                text = viewModel.asunto,
                                onValueChange = viewModel::onAsuntoChange
                            )
                            DescriptionTextField(
                                text = viewModel.descripcion,
                                onValueChange = viewModel::onDescripcionChange
                            )
                            Row {
                                RespuestaTextField(
                                    text = viewModel.respuesta,
                                    onValueChange = viewModel::onRespuestaChange
                                ) {
                                    viewModel.respuestas.add(viewModel.respuesta)
                                    viewModel.respuesta = ""
                                }

                            }
                            Column {
                                viewModel.respuestas.forEach { respuesta ->
                                    OutlinedTextField(
                                        value = respuesta, onValueChange = {},
                                        readOnly = true,
                                        enabled = false,
                                        modifier = Modifier
                                            .padding(
                                                start = widthPercentage(7),
                                                top = heightPercentage(1),
                                                bottom = heightPercentage(1),
                                                end = widthPercentage(7)
                                            )
                                            .offset(x = widthPercentage(-1))
                                            .height(heightPercentage(6)),
                                        trailingIcon = {
                                            IconButton(onClick = {
                                                viewModel.respuesta += " "
                                                viewModel.respuestas.remove(respuesta)
                                                viewModel.respuesta = viewModel.respuesta.removeSuffix(" ")
                                            },
                                                modifier = Modifier
                                                    .width(widthPercentage(5))
                                            ){
                                                Icon(imageVector = ImageVector.vectorResource(id = R.drawable.remove), contentDescription = "Remove", tint = Color.Red)
                                            }
                                        },
                                        colors = TextFieldDefaults.textFieldColors(
                                            backgroundColor = Color.LightGray,
                                            focusedIndicatorColor = Color.Transparent,
                                            unfocusedIndicatorColor = Color.Transparent,
                                            disabledIndicatorColor = Color.Transparent
                                        ),
                                        textStyle = TextStyle(fontSize = 10.sp)
                                    )
                                }
                            }
                            Row {
                                TemaTextField(
                                    text = viewModel.tema,
                                    onValueChange = viewModel::onTemaChange
                                ) {
                                    viewModel.temas.add(viewModel.tema)
                                    viewModel.tema = ""
                                }
                            }
                            SubmitButton(
                                asunto = viewModel.asunto,
                                descripcion = viewModel.descripcion,
                                respuestas = viewModel.respuestas,
                                temas = viewModel.temas
                            )
                        }

                    }
                }
            }

        }
    }
}

//region TopBar
@Composable
fun TopBar(navController: NavController) {
    TopAppBar(
        title = { Text(text = "Nueva votación", color = Color.White) },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
        backgroundColor = VoteTodayOrange
    )
}

//endregion TopBar
//region AsuntoTextField
@Composable
fun AsuntoTextField(text: String, onValueChange: (String) -> Unit) {

    Column(
        modifier = Modifier.padding(start = heightPercentage(2), end = heightPercentage(2), top = heightPercentage(2)),

        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = text,
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = onValueChange,
            label = { Text(text = "Asunto", color = Color.Black) },
            placeholder = { Text(text = "Asunto", color = Color.Black) }
        )
    }
}

//endregion
//region DescriptionTextField
@Composable
fun DescriptionTextField(text: String, onValueChange: (String) -> Unit) {

    Column(
        modifier = Modifier.padding(horizontal = heightPercentage(2)),

        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = text,
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = onValueChange,
            label = { Text(text = "Descripción", color = Color.Black) },
            placeholder = { Text(text = "Descripción", color = Color.Black) }
        )
    }
}

//endregion
//region RespuestaTextField
@Composable
fun RespuestaTextField(text: String, onValueChange: (String) -> Unit, onclick: () -> Unit) {

    Column(
        modifier = Modifier.padding(horizontal = heightPercentage(2)),

        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = text,
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = onValueChange,
            label = { Text(text = "Añadir Respuesta", color = Color.Black) },
            placeholder = { Text(text = "Añadir Respuesta", color = Color.Black) },
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.add),
                    tint = Color.White,
                    contentDescription = "Crear una nueva votacion",
                    modifier = Modifier
                        .height(heightPercentage(7))
                        .width(widthPercentage(15))
                        .background(VoteTodayOrange)
                        .padding(top = heightPercentage(1), bottom = heightPercentage(1))
                        .clickable(onClick = { onclick() })
                )
            }
        )
    }
}

//endregion
//region TemaTextField
@Composable
fun TemaTextField(text: String, onValueChange: (String) -> Unit, onclick: () -> Unit) {

    Column(
        modifier = Modifier.padding(horizontal = heightPercentage(2)),

        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = text,
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            onValueChange = onValueChange,
            label = { Text(text = "Añadir tema", color = Color.Black) },
            placeholder = { Text(text = "Añadir tema", color = Color.Black) },
            trailingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.add),
                    tint = Color.White,
                    contentDescription = "Crear una nueva votacion",
                    modifier = Modifier
                        .height(heightPercentage(7))
                        .width(widthPercentage(15))
                        .background(VoteTodayOrange)
                        .padding(top = heightPercentage(1), bottom = heightPercentage(1))
                        .clickable(onClick = { onclick() })
                )
            }
        )
    }
}

//endregion
//region submitButton
@Composable
fun SubmitButton(
    asunto: String,
    descripcion: String,
    respuestas: List<String>,
    temas: List<String>
) {
    Column(
        modifier = Modifier.padding(start = heightPercentage(2), end = heightPercentage(2), bottom = heightPercentage(2)),

        horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Bottom
    ) {
        Button(
            onClick = {
                FBVotacion.createVotacion(
                    Votacion(
                        asunto = asunto,
                        descripcion = descripcion,
                        respuestas = respuestas,
                        temas = temas
                    )
                )
            },
            modifier = Modifier
                .height(heightPercentage(9))
                .fillMaxWidth()
                .padding(top = heightPercentage(1), bottom = heightPercentage(1))
                .clip(shape = RoundedCornerShape(5)),
            colors = ButtonDefaults.buttonColors(backgroundColor = VoteTodayOrange)
        ) {
            Text(text = "Crear", color = Color.White)
        }
    }
}
//endregion