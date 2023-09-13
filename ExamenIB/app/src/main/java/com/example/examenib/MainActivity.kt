package com.example.examenib

import android.content.Intent
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
import com.example.examenib.negocio.Profesion
import com.example.examenib.util.GestionardorIntent
import com.example.examenib.util.Toaster
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var adaptador: ArrayAdapter<Profesion>
    val profesiones: ArrayList<Profesion> = ArrayList()
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

        //cargarProfesiones()

        val botonAnadir = findViewById<Button>(R.id.btn_add_profesion)
        botonAnadir.setOnClickListener {
            goToActivity(AnadirProfesion::class.java)
        }

//        listView.setOnItemClickListener { parent, view, position, id ->
//            seleccion = position
//            mensaje.mostrarMensaje(
//                "Profesión seleccionada: ${
//                    BaseDeDatosMemoria.arregloProfesion[position].nombre
//                }"
//            )
//        }
    }

    private fun cargarProfesiones() {
        val listView = findViewById<ListView>(R.id.lv_profesiones)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            profesiones
        )
        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        val db = Firebase.firestore
        profesiones.clear()
        db.collection("profesiones")
            .get()
            .addOnSuccessListener {
                for (profesion in it) {
                    profesiones.add(
                        Profesion(
                            profesion.id,
                            profesion.data["nombre"].toString(),
                            profesion.data["descripcion"].toString(),
                            profesion.data["activa"].toString().toBoolean(),
                            profesion.data["salarioPromedio"].toString().toDouble()
                        )
                    )
                }
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listView)
            }
            .addOnFailureListener {
                Log.i("firebase-firestore", "Error: ${it}")
            }
    }

    private fun goToActivity(activity: Class<*>, params: Bundle? = null) {
        val intent = Intent(this, activity)
        if (params != null) {
            intent.putExtras(params)
        }
        startActivity(intent)
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
        seleccion = id
        Log.i("Profesion", "ID: ${seleccion}")
    }

    fun mostrarDialooEliminar() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Esta seguro de eliminar?")
        builder.setPositiveButton("Si") { dialog, which ->
            val db = Firebase.firestore
            db.collection("profesiones")
                .document(profesiones[seleccion].id)
                .delete()
                .addOnSuccessListener {
                    profesiones.removeAt(seleccion)
                    mensaje.mostrarMensaje("Profesión eliminada")
                    //cargarProfesiones()
                    adaptador.notifyDataSetChanged()
                    Log.i("firebase-firestore", "Profesion eliminada")
                }
                .addOnFailureListener {
                    Log.i("firebase-firestore", "Error: ${it}")
                }

        }
        builder.setNegativeButton("No",null)
        val dialog = builder.create()
        dialog.show()
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.item_editar -> {
                val profesion = profesiones[seleccion]
                val bundle = Bundle()
                bundle.putString("id", profesion.id)
                bundle.putString("nombre", profesion.nombre)
                bundle.putString("descripcion", profesion.descripcion)
                bundle.putBoolean("activa", profesion.activa)
                bundle.putDouble("salarioPromedio", profesion.salarioPromedio)
                goToActivity(EditarProfesion::class.java, bundle)
                true
            }
            R.id.item_eliminar -> {
                mostrarDialooEliminar()
                true
            }
            R.id.item_ver_carreras -> {
                val bundle = Bundle()
                bundle.putString("profesionId", profesiones[seleccion].id)
                bundle.putString("nombreProfesion", profesiones[seleccion].nombre)
                goToActivity(VistaCarreras::class.java, bundle)
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onRestart() {
        super.onRestart()
        //cargarProfesiones()
    }
    override fun onResume() {
        super.onResume()
        cargarProfesiones()
    }

}