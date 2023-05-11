package com.example.votetoday.Common.GestorBD

import com.google.firebase.auth.FirebaseAuth

data class Votacion(
    val idUsuario: String = FirebaseAuth.getInstance().currentUser!!.uid,
    val asunto:String,
    val descripcion:String,
    val temas:List<String>,
    val Respuestas:List<String>
)
data class Usuario(
    val Uname:String,
    val votaciones:List<Votacion>?=null,
    val fotoPerfil:String?=null,
    val email: String? = FBAuth.getUserEmail(),
    val idUsuario: String = FirebaseAuth.getInstance().currentUser!!.uid
)