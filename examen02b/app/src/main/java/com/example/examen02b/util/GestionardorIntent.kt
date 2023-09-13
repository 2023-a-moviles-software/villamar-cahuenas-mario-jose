package com.example.examen02b.util

import android.content.Context
import android.content.Intent
import android.os.Bundle

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