package com.example.votetoday.Common.GestorBD

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FBUserQuerys {
    companion object{
        fun setUserBasicData(
            Uname:String,
            context: Context,
            email: String? =FBAuth.getUserEmail(),
            Uid: String? =FirebaseAuth.getInstance().currentUser?.uid

            ){
            Uid?.let {
                val usersCollection = FirebaseFirestore.getInstance().collection("Usuarios")

                // Check if Uname is already taken
                usersCollection.whereEqualTo("name", Uname)
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        //añade un if para que no se pueda crear un usuario con el mismo nombre
                        if (querySnapshot.isEmpty) {
                            //añade un if para que no se pueda crear un usuario con el mismo nombre
                            usersCollection.document(it).set(
                                Usuario(
                                    idUsuario = Uid,
                                    email = email,
                                    Uname = Uname
                                )
                            )
                            usersCollection.document(it).update("id", Uid)
                            usersCollection.document(it).update("email", email)
                            usersCollection.document(it).update("name", Uname)
                            Toast.makeText(context, "Usuario creado con exito", Toast.LENGTH_SHORT).show()
                        } else {
                            // Uname is already taken, handle the error
                            Toast.makeText(context, "El nombre de usuario ya esta en uso", Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { exception ->
                        Toast.makeText(context, "Error al crear el usuario: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
