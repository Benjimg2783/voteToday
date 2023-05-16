package com.example.votetoday.Common.GestorBD

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot

data class Votacion(
    val idUsuario: String = FirebaseAuth.getInstance().currentUser!!.uid,
    val asunto:String,
    val descripcion:String,
    val temas:List<String>,
    val Respuestas:List<String>
)
data class Usuario(
    val uname:String,
    val votaciones:List<Votacion>?=null,
    val fotoPerfil:String?=null
){
    companion object{
        fun fromSnapshot(snapshot: DocumentSnapshot) : Usuario{
            val uname = snapshot["uname"] as String
            val votaciones = snapshot["votaciones"] as List<Votacion>?
            val fotoPerfil = snapshot["fotoPerfil"] as String?

            return Usuario(uname,votaciones,fotoPerfil)
        }
    }
}