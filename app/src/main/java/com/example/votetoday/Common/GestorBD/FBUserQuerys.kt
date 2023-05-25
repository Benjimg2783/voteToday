package com.example.votetoday.Common.GestorBD

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object FBUserQuerys {

        fun checkUserNameNotExists(
            Uname: String,
            callback: (Boolean) -> Unit
        ) {
            Log.i("FBAuth", "checkUserNameExists: $Uname")
            val db = FirebaseFirestore.getInstance()
            val usersCollection = db.collection("Usuario")

            usersCollection.whereEqualTo("uname", Uname)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.size() > 0) {
                        callback(false)
                    } else {
                        callback(true)
                    }
                }
        }

        fun insertUser(uname: String, callback: (Boolean) -> Unit) {
            val db = FirebaseFirestore.getInstance()
            val usersCollection = db.collection("Usuario")
            val uuid = FBAuth.getUserUID()
            val user = Usuario(uname)
            if (uuid != null) {
                Log.i("FBAuth", "insertUser: $uuid")
                usersCollection.document(uuid).set(user)
                    .addOnSuccessListener {
                        callback(true)
                    }
                    .addOnFailureListener {
                        callback(false)
                    }
            } else {
                callback(false)
            }

        }

        fun changeUserName(newName: String, context: Context, callback: (Boolean) -> Unit) {
            val db = FirebaseFirestore.getInstance()
            val usersCollection = db.collection("Usuario")
            val uuid = FBAuth.getUserUID()
            try {
                if (uuid != null) {
                    checkUserNameNotExists(newName) { enabled ->
                        if (enabled) {
                            Log.i("benec", "changeUserName: $newName")
                            usersCollection.document(uuid).update("uname", newName)
                            Toast.makeText(
                                context,
                                "Nombre de usuario cambiado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                            callback(true)
                        } else {
                            Toast.makeText(
                                context,
                                "El nombre de usuario ya existe",
                                Toast.LENGTH_SHORT
                            ).show()
                            callback(false)
                        }
                    }
                }
            } catch (_: Exception) {

            }

        }

        suspend fun getUserName(): Flow<String> = callbackFlow {
            val db = FirebaseFirestore.getInstance()
            val usersCollection = db.collection("Usuario")
            val uuid = FBAuth.getUserUID()

            if (uuid != null) {
                Log.i("benec", "getUserName: $uuid")
                usersCollection.document(uuid).get().addOnCompleteListener { usuario ->
                    val user = Usuario.fromSnapshot(usuario.result)
                    trySend(user.uname)
                    Log.i("benec", "getUserName: $user")

                }
                awaitClose { channel.close() }
            }
        }

        fun setFotoPerfil(fotoPerfil: File?, context: Context) {
            val db = Firebase.storage.reference.child("porfileimages")
            if (Firebase.auth.uid != null) {
                fotoPerfil?.let { foto ->
                    db.child("${Firebase.auth.uid}/fotoPefil.png").putBytes(foto.readBytes())
                        .addOnSuccessListener {
                            suspend {
                                setUserFotoUrl(it.storage.downloadUrl.await().toString())
                            }
                            Toast.makeText(
                                context,
                                "Foto de perfil cambiada correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        .addOnFailureListener {
                            Toast.makeText(
                                context,
                                "Error al cambiar la foto de perfil ${it.message}.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                }

            } else {
                Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show()
            }
        }
        private fun setUserFotoUrl(url: String){
            val db = FirebaseFirestore.getInstance()
            val usersCollection = db.collection("Usuario")
            val uuid = FBAuth.getUserUID()
            if (uuid != null) {
                usersCollection.document(uuid).update("fotoPerfil", url)
            }
        }
        suspend fun getFotoPerfil(uid: String?,context: Context): String? = suspendCoroutine { c ->
            val pfp = Firebase.storage.reference.child("porfileimages/${uid}/fotoPefil.png")
            if (Firebase.auth.uid != null) {

                pfp.downloadUrl
                    .addOnSuccessListener { url ->
                        c.resume(url.toString())
                    }
                    .addOnFailureListener { e ->
                        Log.w("User", "No hay PFP", e)
                        c.resume("")
                    }

            } else Toast.makeText(context, "Error de conexion", Toast.LENGTH_SHORT).show()
        }


}

