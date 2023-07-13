package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import com.example.examenib.negocio.BaseDeDatosMemoria
import com.example.examenib.negocio.Carrera
import com.example.examenib.negocio.Profesion
import com.example.examenib.util.GestionardorIntent
import com.example.examenib.util.Toaster

class AnadirCarrera : AppCompatActivity() {
    lateinit var carrera: Carrera
    lateinit var profesion: Profesion
    val actividad = GestionardorIntent(this)
    lateinit var mensaje: Toaster

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_carrera)

        actividad.callbacks = {
            intet ->
            intent.putExtra("idProfesion", this.intent
                .getIntExtra("idProfesion", -1))
        }
        mensaje = Toaster(findViewById(R.id.tv_creacion_carrera))

        val idProfesion = this.intent.getIntExtra("idProfesion", -1)
        val profesion = BaseDeDatosMemoria.obtenerProfesionPorId(idProfesion)
        if(profesion != null){
            this.profesion = profesion
        }

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
        val idProfesion = this.intent.getIntExtra("idProfesion", -1)
        val profesion = BaseDeDatosMemoria.obtenerProfesionPorId(idProfesion)
        if(nombre.isEmpty() || descripcion.isEmpty() || duracion.isEmpty()){
            mensaje.mostrarMensaje("Debe llenar todos los campos")
            return
        }
        if (profesion != null) {
            carrera = Carrera(
                BaseDeDatosMemoria.obtenerCarrerasPorProfesionId(this.profesion.id).size + 1,
                nombre,
                descripcion,
                activa,
                duracion.toInt(),
                this.profesion.id
            )
            this.profesion.carreras.add(carrera)
            mensaje.mostrarMensaje("Carrera guardada")
            actividad.cambiarActivity(VistaCarreras::class.java)
        }
    }
}
