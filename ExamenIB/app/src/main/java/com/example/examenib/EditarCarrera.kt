package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.examenib.negocio.BaseDeDatosMemoria
import com.example.examenib.negocio.Carrera
import com.example.examenib.util.GestionardorIntent
import com.example.examenib.util.Toaster
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarCarrera : AppCompatActivity() {
    var carrera: Carrera? = null
    var actividad = GestionardorIntent(this)
    lateinit var mensaje: Toaster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_carrera)
        mensaje = Toaster(findViewById(R.id.tv_edicion_carrera))
        var botonActualizar = findViewById<Button>(R.id.btn_actualizar_carrera)

        val professionId = intent.getStringExtra("profesionId")?: ""
        val carreraId = intent.getStringExtra("carreraId")?: ""
        val nombreCarrera = intent.getStringExtra("nombreCarrera")?: ""
        val descripcionCarrera = intent.getStringExtra("descripcionCarrera")?: ""
        val duracionCarrera = intent.getStringExtra("duracionCarrera")?: ""
        val activa = intent.getStringExtra("activa")?: ""

        val carrera = Carrera(
            carreraId,
            nombreCarrera,
            descripcionCarrera,
            activa.toBoolean(),
            duracionCarrera.toInt(),
            professionId
        )

        cargarDatos(carrera)

        Log.i("carrera", "Carrera: ${carrera}")
        carrera?.let {
            cargarDatos(carrera!!)
        }
        botonActualizar.setOnClickListener{
            actualizarCarrera(carrera!!)
        }
    }

    fun cargarDatos(carrera: Carrera){
        val nombre = findViewById<EditText>(R.id.ete_nombre_carrera)
        val descripcion = findViewById<EditText>(R.id.ete_descripcion_carrera)
        val duracion = findViewById<EditText>(R.id.ete_duracion_carrera)
        val activa = findViewById<CheckBox>(R.id.cbce_activa)
        nombre.setText(carrera.nombre)
        descripcion.setText(carrera.descripcion)
        duracion.setText(carrera.duracion.toString())
        activa.isChecked = carrera.activa
    }
    fun actualizarCarrera(carrera: Carrera){
        var nombre = findViewById<EditText>(R.id.ete_nombre_carrera).text.toString()
        val descripcion = findViewById<EditText>(R.id.ete_descripcion_carrera).text.toString()
        val duracion = findViewById<EditText>(R.id.ete_duracion_carrera).text.toString()
        val activa = findViewById<CheckBox>(R.id.cbce_activa).isChecked

        if(
            nombre.isEmpty() ||
            descripcion.isEmpty() ||
            duracion.isEmpty()
        ){
            mensaje.mostrarMensaje("Debe llenar todos los campos")
            return
        }
        carrera.nombre = nombre
        carrera.descripcion = descripcion
        carrera.duracion = duracion.toInt()
        carrera.activa = activa

        val carreraActualizada = hashMapOf(
            "nombre" to carrera.nombre,
            "descripcion" to carrera.descripcion,
            "duracion" to carrera.duracion,
            "activa" to carrera.activa,
            "idProfesion" to carrera.profesionId
        )

        val db = Firebase.firestore
        db.collection("carreras")
            .document(carrera.id)
            .set(carreraActualizada)
            .addOnSuccessListener {
                mensaje.mostrarMensaje("Carrera actualizada")
                finish()
            }
            .addOnFailureListener {
                mensaje.mostrarMensaje("Error al actualizar carrera")
            }
    }
}