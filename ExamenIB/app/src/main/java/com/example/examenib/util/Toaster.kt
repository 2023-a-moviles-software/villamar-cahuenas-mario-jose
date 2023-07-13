package com.example.examenib.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

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