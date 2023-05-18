package com.example.votetoday.Composables

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.votetoday.AppScreens.ProfileScreen.ProfileViewModel
import com.example.votetoday.Common.DeviceConfiguration.Companion.heightPercentage
import com.example.votetoday.R
import com.example.votetoday.ui.theme.GrisClaro


@Composable
fun ImagePopUp(
    pfpUrl: String?,
    onDismissRequest: () -> Unit,
    defaultImage: Int,
    context: Context,
    showDialog: Boolean,
    onclick2: () -> Unit,
    bitmap: (ImageBitmap) -> Unit
    ) {

    //Muestra la imagen de perfil del usuario con un boton para editarla
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Surface(
                Modifier
                    .fillMaxWidth()
                    .height(heightPercentage(50))
                    .clip(shape = RoundedCornerShape(heightPercentage(4))),
                elevation = 1.dp
            ) {


                Box(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colors.background),
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .matchParentSize()
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
                        model =if (pfpUrl.isNullOrBlank())defaultImage else pfpUrl,
                        contentDescription = "Image"
                    )
                }
                Row(
                    verticalAlignment = Alignment.Bottom,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    //boton para editar la foto de perfil
                    ImageSelectionComponent(onImageSelected = { image ->
                        bitmap(image)

                    }, modifier = Modifier)
                }
            }
        }
    }
}
// crea una funcion para seleccionar una imagen de la galeria
@Composable
fun SelectImage(
    context: Context,
    viewModel: ProfileViewModel,
    mostrar: Boolean
) {
var pfp by remember { mutableStateOf<ImageBitmap?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            uri?.let {
                val bitmap = if (Build.VERSION.SDK_INT < 28) {
                    MediaStore.Images.Media.getBitmap(
                        context.contentResolver,
                        uri
                    )
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, uri)
                    ImageDecoder.decodeBitmap(source)
                }
                pfp = bitmap.asImageBitmap()
            }
        }
    )

    //boton para seleccionar la imagen
    OutlinedButton(
        onClick = {
            launcher.launch("image/*")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor = GrisClaro.copy(alpha = 0.5f))
    ) {
        Text(text = "Seleccionar imagen")
    }
}
@Composable
fun ImageSelectionComponent(onImageSelected: (ImageBitmap) -> Unit, modifier: Modifier) {
    val selectedImage = remember { mutableStateOf<ImageBitmap?>(null) }
    val context = LocalContext.current

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    val imageBitmap =
                        MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
                            .asImageBitmap()
                    selectedImage.value = imageBitmap
                    onImageSelected(imageBitmap)
                }
            }
        }

    Column {
        Button(
            onClick = {
                launcher.launch(
                    Intent(
                        Intent.ACTION_OPEN_DOCUMENT,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    ).apply {
                        type = "image/*"
                    }
                )
            },
            modifier=Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = GrisClaro.copy(alpha = 0.5f))
        ){
            Icon(painter = painterResource(R.drawable.edit), contentDescription = "Editar foto",modifier = Modifier.size(30.dp))
        }

    }
}
