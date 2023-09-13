package com.frankz.examen_2b_mario

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.frankz.examen_2b_mario.negocio.BaseDeDatosMemoria
import com.frankz.examen_2b_mario.negocio.Profesion
import com.frankz.examen_2b_mario.negocio.ProfesionDto
import com.frankz.examen_2b_mario.util.GestionardorIntent
import com.frankz.examen_2b_mario.util.Toaster
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AnadirProfesion : AppCompatActivity() {
    lateinit var mensaje: Toaster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_profesion)
        mensaje = Toaster(findViewById(R.id.txt_anadir_profesion))
        val botonGuardar = findViewById<Button>(R.id.btn_guardar_profesion)
        botonGuardar.setOnClickListener{
            guardarProfesion()
        }
    }

    fun guardarProfesion(){
        val nombre = findViewById<EditText>(R.id.et_nombre_profesion).text.toString()
        val descripcion = findViewById<EditText>(R.id.et_descripcion_profesion).text.toString()
        val salario = findViewById<EditText>(R.id.et_salario_promedio).text.toString()
        val activa = findViewById<CheckBox>(R.id.cb_activa).isChecked

        if(
            nombre.isEmpty() ||
            descripcion.isEmpty() ||
            salario.isEmpty()
        ){
            mensaje.mostrarMensaje("Debe llenar todos los campos")
            return
        }

        val profesion = hashMapOf(
            "nombre" to nombre,
            "descripcion" to descripcion,
            "salarioPromedio" to salario.toDouble(),
            "activa" to activa
        )

        val db = Firebase.firestore

        db.collection("profesiones")
            .add(profesion)
            .addOnSuccessListener {
                mensaje.mostrarMensaje("Profesión guardada")
                finish()
            }
            .addOnFailureListener {
                mensaje.mostrarMensaje("Error al guardar la profesión")
            }
    }
}