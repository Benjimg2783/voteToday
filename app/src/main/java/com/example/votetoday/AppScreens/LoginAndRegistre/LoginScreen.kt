package com.example.votetoday.AppScreens.LoginAndRegistre

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.votetoday.Common.DeviceConfiguration.Companion.heightPercentage
import com.example.votetoday.Common.GestorBD.FBAuth
import com.example.votetoday.Common.GestorBD.FBUserQuerys
import com.example.votetoday.Common.GetDeviceConfiguration
import com.example.votetoday.Common.Navigation.NavigationFunctions.Companion.NavigatePop
import com.example.votetoday.Common.Navigation.Screens
import com.example.votetoday.Common.SystemBarColor
import com.example.votetoday.R
import com.example.votetoday.ui.theme.VoteTodayOrange
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


@Preview
@Composable
fun PreviewLoginScreen() {
    LoginScreen(rememberNavController())
}

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginAndRegistreViewModel = hiltViewModel()
) {

    var isLogging by remember { mutableStateOf(true) }

    SystemBarColor(color = VoteTodayOrange)
    GetDeviceConfiguration()

    if (viewModel.popNContinue) {
        viewModel.popNContinue = false
        NavigatePop(navController = navController, destination = Screens.MainScreen.ruta)
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFFFFFEFE), VoteTodayOrange),
                )
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        //region LOGO
        Column(
            modifier = Modifier
                .height(heightPercentage(40))
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Logo()
        }
        //endregion

        //region TextFields
        Column(
            Modifier
                .height(
                    if (isLogging) {
                        heightPercentage(40)
                    } else heightPercentage(56)
                )
                .width(heightPercentage(45))
                .clip(RoundedCornerShape(heightPercentage(3)))
                .background(Color(0xFFFCFAFA)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                EmailTextField(value = viewModel.email, onValueChange = viewModel::onEmailChange)
                PasswordTextField(
                    value = viewModel.password, onValueChange = viewModel::onPasswordChange
                )
                if (!isLogging) {
                    ConfirmPasswordTextField(
                        viewModel.repeatPassword, viewModel::onRepeatPasswordChange
                    )
                    UserNameTextField(
                        text = viewModel.userName, onValueChange = viewModel::onUserNameChange
                    )
                }
                Spacer(Modifier.height(heightPercentage(3)))
                SubmittButton(
                    isLogging,
                    viewModel.email,
                    viewModel.password,
                    viewModel.repeatPassword,
                    viewModel.userName,
                    viewModel::turnPopNContinue
                )
                ClickableText(isLogging) { isLogging = !isLogging }


            }

        }
        //endregion


    }

}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Logo() {

    val image: Painter = painterResource(id = R.drawable.logocolor)

    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { startAnimation = true }

    AnimatedVisibility(
        visible = startAnimation,
        enter = slideInVertically(initialOffsetY = { -40 }) + expandVertically(
            expandFrom = Alignment.Top
        ) + scaleIn(
            transformOrigin = TransformOrigin(0.5f, 0f)
        ) + fadeIn(initialAlpha = 0.3f),
        exit = fadeOut()
    ) {
        Image(painter = image, contentDescription = "logo")
    }


}

@Composable
fun EmailTextField(value: String, onValueChange: (String) -> Unit) {

    Column {
        OutlinedTextField(
            value = value,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            modifier = Modifier.padding(horizontal = heightPercentage(2)),
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    tint = Color.Black,
                    contentDescription = "personIcon"
                )
            },
            onValueChange = onValueChange,
            label = { Text(text = "Introduce tu e-mail", color = Color.Black) },
            placeholder = { Text(text = "Introduce tu e-mail", color = Color.Black) }
            )
    }
}

@Composable
fun PasswordTextField(value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = heightPercentage(2)),

        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        var passwordVisibility by remember { mutableStateOf(false) }

        val icon = if (passwordVisibility) painterResource(id = R.drawable.view)
        else painterResource(id = R.drawable.notview)

        OutlinedTextField(
            value = value,
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            onValueChange = {
                onValueChange(it)
            },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.keypass),
                    tint = Color.Black,
                    contentDescription = "personIcon"
                )

            },


            placeholder = { Text(text = "Contraseña", color = Color.White) },
            label = { Text(text = "Contraseña", color = Color.Black) },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        tint = Color.Unspecified,
                        contentDescription = "Visibility Icon",
                        modifier = Modifier.size(heightPercentage(4))
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )
    }
}

@Composable
fun UserNameTextField(text: String, onValueChange: (String) -> Unit) {

    Column(
        modifier = Modifier.padding(horizontal = heightPercentage(2)),

        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {

        OutlinedTextField(
            value = text,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            maxLines = 1,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                Color.Black,
                cursorColor = Color.Black,
                focusedBorderColor = Color.Black,
                unfocusedBorderColor = Color.Black
            ),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person,
                    tint = Color.Black,
                    contentDescription = "personIcon"
                )
            },
            onValueChange = onValueChange,
            label = { Text(text = "Introduce un nombre de usuario", color = Color.Black) },
            placeholder = { Text(text = "Introduce un nombre de usuario", color = Color.Black) }
        )
    }
}

@Composable
fun ConfirmPasswordTextField(text: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = heightPercentage(2)),

        horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        var passwordVisibility by remember { mutableStateOf(false) }

        val icon = if (passwordVisibility) painterResource(id = R.drawable.view)
        else painterResource(id = R.drawable.notview)

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
            onValueChange = {
                onValueChange(it)
            },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.keypass),
                    tint = Color.Black,
                    contentDescription = "personIcon"
                )

            },


            placeholder = { Text(text = "Repita la contraseña", color = Color.Black) },
            label = { Text(text = "Repita la contraseña", color = Color.Black) },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        tint = Color.Unspecified,
                        contentDescription = "Visibility Icon",
                        modifier = Modifier.size(heightPercentage(4))
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (passwordVisibility) VisualTransformation.None
            else PasswordVisualTransformation()
        )
    }
}


@Composable
fun SubmittButton(
    isLogging: Boolean,
    email: String,
    password: String,
    repeatPassword: String,
    uName: String,
    turnPopNContinue: () -> Unit
) {

    val context = LocalContext.current

    Button(
        onClick = {

            when (isLogging) {
                true -> FBAuth.onLogIn(email, password, context) { success ->
                    if (success) {
                        FBAuth.updateUserUID(Firebase.auth.uid.toString())
                        turnPopNContinue()
                    }
                }
                false -> if (password == repeatPassword && password.length >= 8) {

                    FBAuth.onSignUp(email, password, context) { success ->
                        if (success) {
                            turnPopNContinue()
                        }
                    }
                    FBUserQuerys.setUserBasicData(uName,context, email = email)
                } else if (password.length < 8) {
                    Toast.makeText(
                        context,
                        "La contraseña debe tener al menos 8 caracteres",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(context, "Las contraseñas no coinciden", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }, colors = ButtonDefaults.buttonColors(backgroundColor = VoteTodayOrange)
    ) {

        Text(
            text = (if (isLogging) {
                "Iniciar Sesión"
            } else {
                "Registrar"
            }), color = Color.White
        )
    }
}


@Composable
fun ClickableText(isLogging: Boolean, toggleLogin: () -> Unit) {
    Text(
        text = AnnotatedString(
            if (isLogging) {
                "¿No tienes cuenta? Registrate"
            } else "¿Ya tienes cuenta? Inicia Sesión"
        ),
        style = TextStyle(color = VoteTodayOrange, textDecoration = TextDecoration.Underline),
        modifier = Modifier.clickable(onClick = { toggleLogin() })
    )
}