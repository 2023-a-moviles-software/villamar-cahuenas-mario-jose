package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.examenib.negocio.BaseDeDatosMemoria
import com.example.examenib.negocio.Carrera
import com.example.examenib.util.GestionardorIntent
import com.example.examenib.util.Toaster

class EditarCarrera : AppCompatActivity() {
    var carrera: Carrera? = null
    var profesionId: Int? = null
    var actividad = GestionardorIntent(this)
    lateinit var mensaje: Toaster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_carrera)
        mensaje = Toaster(findViewById(R.id.tv_edicion_carrera))
        var botonActualizar = findViewById<Button>(R.id.btn_actualizar_carrera)

        val id = intent.getIntExtra("carrera", -1)
        carrera = BaseDeDatosMemoria.obtenerCarreraPorId(id)
        profesionId = intent.getIntExtra("profesion", -1)
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
        duracion.setText(carrera.duracion).toString()
        activa.isChecked = carrera.activa
    }
    fun actualizarCarrera(carrera: Carrera){
        var nombre = findViewById<EditText>(R.id.ete_nombre_carrera).text.toString()
        val descripcion = findViewById<EditText>(R.id.ete_descripcion_carrera).text.toString()
        val duracion = findViewById<EditText>(R.id.ete_duracion_carrera).text.toString().toInt()
        val activa = findViewById<CheckBox>(R.id.cbce_activa).isChecked

        if(
            nombre.isEmpty() ||
            descripcion.isEmpty() ||
            duracion == null
        ){
            mensaje.mostrarMensaje("Debe llenar todos los campos")
            return
        }
        carrera.nombre = nombre
        carrera.descripcion = descripcion
        carrera.duracion = duracion
        carrera.activa = activa
        mensaje.mostrarMensaje("Carrera actualizada")
        actividad.cambiarActivity(VistaCarreras::class.java)
    }
}