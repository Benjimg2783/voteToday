package com.example.votetoday.Common.GestorBD

import com.google.firebase.firestore.FirebaseFirestore

object FBVotacion{
    fun createVotacion(votacion: Votacion){
        val db = FirebaseFirestore.getInstance()
        val votacionesCollection = db.collection("Votaciones")
        votacionesCollection.add(votacion)
    }
}