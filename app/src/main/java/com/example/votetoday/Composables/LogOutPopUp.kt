package com.example.votetoday.Composables

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.votetoday.ui.theme.VoteTodayOrange

@Composable
fun LogOutPopUp(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmButton: () -> Unit
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismissRequest,
            confirmButton = {
                TextButton(onClick = onConfirmButton) {
                    Text(text = "Aceptar", color = Color.Red)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismissRequest) {
                    Text(text = "Cancelar", color = Color.Black)
                }
            },
            title = { Text("Cerrar Sesión", color = VoteTodayOrange) },
            text = { Text("¿Está seguro que desea cerrar sesión?", color = Color.Black) },
            backgroundColor = Color.White
        )
    }
}