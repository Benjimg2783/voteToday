package com.example.votetoday.Common.GestorBD

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class FBAuth {
    companion object {
        private var UID = mutableStateOf(Firebase.auth.uid.toString())

        //region LOGIN
        fun onLogIn(
            email: String,
            password: String,
            context: Context,
            callback: (Boolean) -> Unit
        ) {

            try {
                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Logueado con éxito", Toast.LENGTH_SHORT).show()
                            callback(true)
                        }
                    }.addOnFailureListener { exc ->
                        when (exc) {
                            is FirebaseAuthInvalidCredentialsException -> {
                                Toast.makeText(context, "Error de credenciales", Toast.LENGTH_SHORT)
                                    .show()
                                callback(false)
                            }

                            is FirebaseNetworkException -> {
                                Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT)
                                    .show()
                                callback(false)
                            }

                            else -> {
                                // imprimir mensaje de error generado por firebase
                                Toast.makeText(
                                    context,
                                    "Error de inicio de sesion: ${exc.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                callback(false)
                            }
                        }
                    }
            } catch (error: Exception) {
                Toast.makeText(context, "No dejes campos vacios", Toast.LENGTH_SHORT).show()
                callback(false)
            }
        }
        //endregion

        //region Registro
        fun onSignUp(
            email: String,
            password: String,
            context: Context,
            uname: String,
            callback: (Boolean) -> Unit
        ) {

            try {
                FBUserQuerys.checkUserNameNotExists(uname) { exists ->
                    Log.i("FBAuth", "onSignUp: $exists")
                    if (exists) {

                        FirebaseAuth.getInstance()
                            .createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    Toast.makeText(
                                        context,
                                        "Registrado con éxito",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    onLogIn(email, password, context, callback)
                                    FBUserQuerys.insertUser(uname) { success ->
                                        if (success) {
                                            callback(true)
                                        } else {
                                            callback(false)
                                        }
                                    }
                                }

                            }.addOnFailureListener { exc ->
                                when (exc) {
                                    is FirebaseNetworkException -> {
                                        Toast.makeText(
                                            context,
                                            "Error de conexion",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        callback(false)
                                    }

                                    is FirebaseAuthEmailException -> {
                                        Toast.makeText(
                                            context,
                                            "Correo no valido",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        callback(false)
                                    }

                                    is FirebaseAuthUserCollisionException -> {
                                        Toast.makeText(context, "Correo en uso", Toast.LENGTH_SHORT)
                                            .show()
                                        callback(false)
                                    }

                                    is FirebaseAuthInvalidCredentialsException -> {
                                        Toast.makeText(
                                            context,
                                            "Formato de correo incorrecto",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        callback(false)
                                    }

                                    else -> {
                                        Toast.makeText(
                                            context,
                                            "Error al crear la cuenta:${exc.message}",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        callback(false)
                                    }
                                }
                            }
                    } else {
                        Toast.makeText(context, "Nombre Existente", Toast.LENGTH_SHORT).show()
                        callback(false)
                    }
                }


            } catch (error: Exception) {
                Toast.makeText(context, "No dejes campos vacios", Toast.LENGTH_SHORT).show()
                callback(false)
            }
        }
        //endregion

        fun getUserUID(): String? {
            return Firebase.auth.currentUser?.uid
        }

        fun updateUserUID(uid: String) {
            UID.value = uid
        }

        fun logOut() {
            try {
                FirebaseAuth.getInstance().signOut()
            } catch (_: FirebaseException) {
            }
        }
    }
}
