package com.example.examenib.util

import android.content.ComponentCallbacks
import android.content.Context
import android.content.Intent

class GestionardorIntent (
    val context: Context,
    var callbacks: (intet: Intent) -> Unit = { }

) {
    fun cambiarActivity( clase: Class<*>) {
        val intent = Intent(
            context,
            clase
        )
        callbacks(intent)
        context.startActivity(intent)
    }
}