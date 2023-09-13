package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView

import com.example.examenib.negocio.Carrera
import com.example.examenib.negocio.CarreraDto
import com.example.examenib.negocio.Profesion
import com.example.examenib.util.GestionardorIntent
import com.example.examenib.util.Toaster
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AnadirCarrera : AppCompatActivity() {
    lateinit var carrera: Carrera
    var profesionId: String? = null
    val actividad = GestionardorIntent(this)
    lateinit var mensaje: Toaster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_carrera)


        mensaje = Toaster(findViewById(R.id.tv_anadir_carrera))

        profesionId = intent.getStringExtra("profesionId")
        val nombreProfesion = intent.getStringExtra("profesionNombre")

        val textProfesion = findViewById<TextView>(R.id.tv_anadir_carrera)
        textProfesion.setText(nombreProfesion)

        Log.i("Profesion", "ID: ${profesionId}")


        val botonGuardar = findViewById<Button>(R.id.btn_guardar_carrera)
        botonGuardar.setOnClickListener {
            guardarCarrera()
        }
    }

    override fun onRestart() {
        super.onRestart()
    }

    fun guardarCarrera(){
        val nombre = findViewById<EditText>(R.id.et_nombre_carrera).text.toString()
        val descripcion = findViewById<EditText>(R.id.et_descripcion_carrera).text.toString()
        val duracion = findViewById<EditText>(R.id.et_duracion_carrera).text.toString()
        val activa = findViewById<CheckBox>(R.id.cb_activa_carrera).isChecked
        if(nombre.isEmpty() || descripcion.isEmpty() || duracion.isEmpty()){
            mensaje.mostrarMensaje("Debe llenar todos los campos")
            return
        }
        println(profesionId)
        if (profesionId != null) {
            val carrera = CarreraDto(nombre, descripcion, activa, duracion.toInt(), profesionId!!)
            val nuevaCarrera = hashMapOf(
                "nombre" to carrera.nombre,
                "descripcion" to carrera.descripcion,
                "activa" to carrera.activa,
                "duracion" to carrera.duracion,
                "idProfesion" to carrera.profesionId
            )
            val db = Firebase.firestore
            db.collection("carreras")
                .add(nuevaCarrera)
                .addOnSuccessListener {
                    mensaje.mostrarMensaje("Carrera guardada")
                    finish()
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "Error al agregar carrera")
                }
        }
    }
}
