package com.example.examen02b.util

import android.view.View

class Toaster (
    var toast: View? = null
    ) {
    fun show() {
        toast?.visibility = View.VISIBLE
    }
    fun hide() {
        toast?.visibility = View.GONE
    }
    fun mostrarMensaje(mensaje: String) {

        Snackbar.make(toast!!, mensaje, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }
}