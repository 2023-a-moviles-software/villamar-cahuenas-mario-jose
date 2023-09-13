package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.examenib.negocio.BaseDeDatosMemoria
import com.example.examenib.negocio.Profesion
import com.example.examenib.util.GestionardorIntent
import com.example.examenib.util.Toaster
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class EditarProfesion : AppCompatActivity() {
    var profesion: Profesion? = null
    val actividad = GestionardorIntent(this)
    lateinit var mensaje: Toaster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_profesion)
        mensaje = Toaster(findViewById(R.id.txt_anadir_profesion))
        val botonActualizar = findViewById<Button>(R.id.btn_actualizar_profesion)


        val id = intent.getStringExtra("id")?: ""
        Log.i("Profesion","$id")

        //profesion = BaseDeDatosMemoria.obtenerProfesionPorId(id)

        val name = intent.getStringExtra("nombre")?: ""
        val description = intent.getStringExtra("descripcion")?: ""
        val salary = intent.getDoubleExtra("salarioPromedio", 0.0)
        val active = intent.getBooleanExtra("activa", false)

        val profesion = Profesion(
            id,
            name,
            description,
            active,
            salary
        )

        cargarDatos(profesion)

        botonActualizar.setOnClickListener{
            actualizarProfesion(profesion!!)
        }

    }
    fun cargarDatos(profesion: Profesion){
        val nombre = findViewById<EditText>(R.id.ete_nombre_profesion)
        val descripcion = findViewById<EditText>(R.id.ete_descripcion_profesion)
        val salario = findViewById<EditText>(R.id.ete_salario_promedio)
        val activa = findViewById<CheckBox>(R.id.cbe_activa)
        nombre.setText(profesion.nombre)
        descripcion.setText(profesion.descripcion)
        salario.setText(profesion.salarioPromedio.toString())
        activa.isChecked = profesion.activa
    }

    fun actualizarProfesion(profesion: Profesion){
        val nombre = findViewById<EditText>(R.id.ete_nombre_profesion).text.toString()
        val descripcion = findViewById<EditText>(R.id.ete_descripcion_profesion).text.toString()
        val salario = findViewById<EditText>(R.id.ete_salario_promedio).text.toString().toDouble()
        val activa = findViewById<CheckBox>(R.id.cbe_activa).isChecked

        if(
            nombre.isEmpty() ||
            descripcion.isEmpty() ||
            salario == null
        ){
            mensaje.mostrarMensaje("Debe llenar todos los campos")
            return
        }

        profesion.nombre = nombre
        profesion.descripcion = descripcion
        profesion.salarioPromedio = salario
        profesion.activa = activa

        val updatedProfession = hashMapOf(
            "nombre" to profesion.nombre,
            "descripcion" to profesion.descripcion,
            "salarioPromedio" to profesion.salarioPromedio,
            "activa" to profesion.activa
        )

        val db = Firebase.firestore
        db.collection("profesiones")
            .document(profesion.id)
            .set(updatedProfession)
            .addOnSuccessListener {
                mensaje.mostrarMensaje("Profesión actualizada")
                finish()
            }
            .addOnFailureListener{
                mensaje.mostrarMensaje("Error al actualizar profesión")
            }


    }
}