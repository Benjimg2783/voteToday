package com.example.votetoday.Common.GestorBD

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

object FBVotacion {
    fun createVotacion(votacion: Votacion) {
        val db = FirebaseFirestore.getInstance()
        val votacionesCollection = db.collection("Votaciones")
        if (votacion.asunto.trim().length > 10) {
            votacionesCollection.document(
                votacion.idUsuario + votacion.asunto.trim().substring(0, 10)
            ).set(votacion)
        } else {
            votacionesCollection.document(
                votacion.idUsuario + votacion.asunto.trim().padEnd(10, '-')
            ).set(votacion)
        }
    }

    fun getVotaciones(): Flow<MutableList<Votacion>> = callbackFlow {
        val db = FirebaseFirestore.getInstance()

        val votacionesCollection = db.collection("Votaciones")
        votacionesCollection.orderBy("fecha", Query.Direction.DESCENDING).get().addOnCompleteListener { task ->

            if (task.isSuccessful) {
                val votaciones = mutableListOf<Votacion>()
                task.result.forEach { document ->
                    val votacion = Votacion.fromVotacionSnapshot(document)
                    Log.i("vota", "${document.id} => ${document.data}")
                    if (votacion.idUsuario != FBAuth.getUserUID()) {
                        votaciones.add(votacion)
                    }
                    Log.i("vota", "añadido $votaciones")

                }
                trySend(votaciones)

            } else {
                Log.i("vota", "Error getting documents: ", task.exception)
            }

        }
        awaitClose()
    }

    fun getVotacionesPropias(): Flow<MutableList<Votacion>> = callbackFlow {
        val db = FirebaseFirestore.getInstance()

        val votacionesCollection = db.collection("Votaciones")
        votacionesCollection.orderBy("fecha", Query.Direction.DESCENDING).get().addOnCompleteListener { task ->

            if (task.isSuccessful) {
                val votaciones = mutableListOf<Votacion>()
                task.result.forEach { document ->
                    val votacion = Votacion.fromVotacionSnapshot(document)
                    Log.i("vota", "${document.id} => ${document.data}")
                    if (votacion.idUsuario == FBAuth.getUserUID()) {
                        votaciones.add(votacion)
                    }
                    Log.i("vota", "añadido $votaciones")

                }
                trySend(votaciones)

            } else {
                Log.i("vota", "Error getting documents: ", task.exception)
            }

        }
        awaitClose()
    }

    fun sumarRespuesta(votacion: Votacion) {
        val db = FirebaseFirestore.getInstance()
        val votacionesCollection = db.collection("Votaciones")
        if (votacion.asunto.trim().length > 10) {
            votacionesCollection.document(
                votacion.idUsuario + votacion.asunto.trim().substring(0, 10)
            ).update("recuento", votacion.recuento)
            votacionesCollection.document(
                votacion.idUsuario + votacion.asunto.trim().substring(0, 10)
            ).update("votantes", votacion.votantes)
        } else {
            votacionesCollection.document(
                votacion.idUsuario + votacion.asunto.trim().padEnd(10, '-')
            ).update("recuento", votacion.recuento)
            votacionesCollection.document(
                votacion.idUsuario + votacion.asunto.trim().padEnd(10, '-')
            ).update("votantes", votacion.votantes)
        }
    }
}