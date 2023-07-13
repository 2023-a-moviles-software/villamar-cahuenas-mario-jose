package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.examenib.negocio.BaseDeDatosMemoria
import com.example.examenib.negocio.Carrera
import com.example.examenib.negocio.Profesion
import com.example.examenib.util.GestionardorIntent
import com.example.examenib.util.Toaster
import java.util.ArrayList

class VistaCarreras : AppCompatActivity() {
    val actividad = GestionardorIntent(this)
    private var idCarrera = -1
    private var idProfesion = -1
    lateinit var adaptador: ArrayAdapter<Carrera>
    lateinit var profesion:Profesion
    lateinit var mensaje: Toaster


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_carreras)
        val profesionTv = findViewById<TextView>(R.id.tv_profesion)

        idProfesion = intent.getIntExtra("profesion", -1)

        actividad.callbacks = {
            intet ->
            intent.putExtra("carrera", idCarrera)
            intent.putExtra("profesion", idProfesion)
        }
        var carreras = ArrayList<Carrera>()
        carreras = BaseDeDatosMemoria.obtenerCarrerasPorProfesionId(idProfesion) as ArrayList<Carrera>

        if(idProfesion != -1){
            val profesionE = BaseDeDatosMemoria.obtenerProfesionPorId(idProfesion)
            if(profesionE != null){
                this.profesion = profesionE
                profesionTv.text = profesion.nombre
            }
        }
        val listView = findViewById<ListView>(R.id.lv_carreras)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            carreras
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        registerForContextMenu(listView)

        val botonCrear = findViewById<Button>(R.id.btn_add_carrera)
        botonCrear.setOnClickListener {
            actividad.cambiarActivity(AnadirCarrera::class.java)
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_carrera, menu)
        val info = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idCarrera = adaptador.getItem(id)?.id!!
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.item_editar_carrera -> {
                actividad.cambiarActivity(EditarCarrera::class.java)
                true
            }
            R.id.item_eliminar_carrera -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("¿Está seguro que desea eliminar la carrera?")
                    .setCancelable(false)
                    .setPositiveButton("Si") { dialog, id ->
                        BaseDeDatosMemoria.eliminarCarreraPorId(idCarrera)
                        adaptador.notifyDataSetChanged()
                        mensaje.mostrarMensaje("Carrera eliminada")
                    }
                builder.setNegativeButton("No",null)
                    val alert = builder.create()
                    alert.show()
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }
    override fun onResume() {
        super.onResume()
        adaptador.notifyDataSetChanged()
    }
}