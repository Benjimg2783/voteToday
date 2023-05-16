package com.example.votetoday.Common.GestorBD

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class FBUserQuerys {
    companion object {
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
                Log.i("FBAuth", "insertUser: ${uuid}")
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

        fun changeUserName(newName: String,context:Context,callback: (Boolean) -> Unit) {
            val db = FirebaseFirestore.getInstance()
            val usersCollection = db.collection("Usuario")
            val uuid = FBAuth.getUserUID()
            try {
                if (uuid != null) {
                    checkUserNameNotExists(newName) { enabled ->
                        if (enabled) {
                            Log.i("benec", "changeUserName: $newName")
                            usersCollection.document(uuid).update("uname", newName)
                            Toast.makeText(context, "Nombre de usuario cambiado correctamente", Toast.LENGTH_SHORT).show()
                            callback(true)
                        }else{
                            Toast.makeText(context, "El nombre de usuario ya existe", Toast.LENGTH_SHORT).show()
                            callback(false)
                        }
                    }
                }
            } catch (e: Exception) {

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
                awaitClose{channel.close()}
            }
        }
        fun setFotoPerfil(imagen: String, callback: (Boolean) -> Unit){
            //TODO()
        }


    }
}

