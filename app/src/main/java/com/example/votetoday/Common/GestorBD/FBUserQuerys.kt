package com.example.votetoday.Common.GestorBD

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import okhttp3.internal.wait

class FBUserQuerys {
    companion object {
        fun checkUserNameExists(
            Uname: String,
            callback: (Boolean) -> Unit
        ) {
            Log.i("FBAuth", "checkUserNameExists: ${Uname}")
            val db = FirebaseFirestore.getInstance()
            val usersCollection = db.collection("Usuario")

            usersCollection.whereEqualTo("Uname", Uname)
                .get()
                .addOnSuccessListener { documents ->
                    if (documents.size() > 0) {
                        callback(false)
                    } else {
                        callback(true)
                    }
                }
        }

        fun insertUser(uname: String, email: String, callback: (Boolean) -> Unit) {
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

        fun changeUserName(newName: String) {
            val db = FirebaseFirestore.getInstance()
            val usersCollection = db.collection("Usuario")
            val uuid = FBAuth.getUserUID()
            try {
                if (uuid != null) {
                    usersCollection.document(uuid).update("Uname", newName)
                }
            } catch (e: Exception) {
                Log.i("FBAuth", "changeUserName: ${e.message}")
                insertUser(newName, FBAuth.getUserEmail()!!) { result ->
                    if (result) {
                        Log.i("FBAuth", "changeUserName: ${newName}")
                    }
                }
            }

        }

        suspend fun getUserName(): Flow<String> = callbackFlow {
            val db = FirebaseFirestore.getInstance()
            val usersCollection = db.collection("Usuario")
            val uuid = FBAuth.getUserUID()
            val user = ""
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
    }
}
