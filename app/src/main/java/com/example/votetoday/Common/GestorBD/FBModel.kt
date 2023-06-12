package com.example.votetoday.Common.GestorBD

import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import java.util.Date

data class Votacion(
    val idUsuario: String = FirebaseAuth.getInstance().currentUser!!.uid,
    val asunto: String,
    val descripcion: String?,
    val temas: MutableList<String>,
    var respuestas: MutableList<String>,
    var votantes: MutableList<String>? = null,
    val recuento: MutableList<Int>,
    val fecha: Date? = Date()
) {
    companion object {
        fun fromVotacionSnapshot(snapshot: DocumentSnapshot): Votacion {
            val idUsuario = snapshot["idUsuario"] as String
            val asunto = snapshot["asunto"] as String
            val descripcion = snapshot["descripcion"] as String?
            val temas = snapshot["temas"] as MutableList<String>
            val respuestas = snapshot["respuestas"] as MutableList<String>
            val votantes = snapshot["votantes"] as MutableList<String>?
            val recuento = snapshot["recuento"] as MutableList<Int>
            val fechaTimestamp = snapshot.getTimestamp("fecha")
            val fecha = fechaTimestamp?.toDate() // Convert Timestamp to Date

            return Votacion(idUsuario, asunto, descripcion, temas, respuestas, votantes,recuento, fecha)
        }
    }
}

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