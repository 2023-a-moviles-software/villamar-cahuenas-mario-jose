package com.example.examenib

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.example.examenib.negocio.BaseDeDatosMemoria
import com.example.examenib.negocio.Profesion
import com.example.examenib.util.GestionardorIntent
import com.example.examenib.util.Toaster

class MainActivity : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<Profesion>
    var seleccion = 0
    val activity = GestionardorIntent(this)
    lateinit var mensaje: Toaster


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mensaje = Toaster(findViewById(R.id.et_lista_profesiones))

        activity.callbacks = { intent ->
            intent.putExtra("profesion", seleccion)
        }

        val listView = findViewById<ListView>(R.id.lv_profesiones)

        val profesiones = BaseDeDatosMemoria.arregloProfesion

        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            profesiones
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val botonAnadir = findViewById<Button>(R.id.btn_add_profesion)
        botonAnadir.setOnClickListener {
            activity.cambiarActivity(AnadirProfesion::class.java)
        }

        registerForContextMenu(listView)

        listView.setOnItemClickListener { parent, view, position, id ->
            seleccion = position
            mensaje.mostrarMensaje(
                "Profesión seleccionada: ${
                    BaseDeDatosMemoria.arregloProfesion[position].nombre
                }"
            )
        }
    }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            super.onCreateContextMenu(menu, v, menuInfo)
            val inflater = menuInflater
            inflater.inflate(R.menu.menu_profesion, menu)
            val info  = menuInfo as AdapterView.AdapterContextMenuInfo
            val id = info.position
            seleccion = adaptador.getItem(id)?.id!!
            Log.i("Item", "ID: ${seleccion}")
        }

        fun mostrarDialooEliminar() {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Esta seguro de eliminar?")
            builder.setPositiveButton("Si") { dialog, which ->
                if (BaseDeDatosMemoria.eliminarProfesionPorId(seleccion)) {
                    mensaje.mostrarMensaje("Profesión eliminada")
                    adaptador.notifyDataSetChanged()
                }
            }
            builder.setNegativeButton("No",null)
            val dialog = builder.create()
            dialog.show()
        }

        override fun onContextItemSelected(item: MenuItem): Boolean {
            return when(item.itemId) {
                R.id.item_editar -> {
                    activity.cambiarActivity(EditarProfesion::class.java)
                    true
                }
                R.id.item_eliminar -> {
                    mostrarDialooEliminar()
                    true
                }
                R.id.item_ver_carreras -> {
                    activity.cambiarActivity(VistaCarreras::class.java)
                    true
                }
                else -> super.onContextItemSelected(item)
            }
        }

        override fun onRestart() {
            super.onRestart()
            adaptador.notifyDataSetChanged()
        }
        override fun onResume() {
            super.onResume()
            adaptador.notifyDataSetChanged()
        }

}