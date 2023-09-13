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
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.examenib.negocio.BaseDeDatosMemoria
import com.example.examenib.negocio.Carrera
import com.example.examenib.negocio.Profesion
import com.example.examenib.util.GestionardorIntent
import com.example.examenib.util.Toaster
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class VistaCarreras : AppCompatActivity() {
    val actividad = GestionardorIntent(this)
    private var seleccion = 0
    private var idProfesion: String? = null
    lateinit var adaptador: ArrayAdapter<Carrera>
    lateinit var mensaje: Toaster
    val carreras: ArrayList<Carrera> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vista_carreras)

        val nombreProfesion = intent.getStringExtra("nombreProfesion")

        if (nombreProfesion != null) {
            val profesionTv = findViewById<TextView>(R.id.tv_profesion)
            profesionTv.text = nombreProfesion
            mensaje = Toaster(findViewById(R.id.tv_profesion))
        }

        idProfesion = intent.getStringExtra("profesionId")
        Log.i("Profesion", "ID: ${idProfesion}")

        val botonCrear = findViewById<Button>(R.id.btn_add_carrera)
        botonCrear.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("profesionId", idProfesion)
            bundle.putString("profesionNombre", nombreProfesion)

            goToActivity(AnadirCarrera::class.java, bundle)
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
        val info  = menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        seleccion = id
        Log.i("Carrera", "ID: ${seleccion}")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.item_editar_carrera -> {
                val db = Firebase.firestore
                db.collection("profesiones")
                    .document(idProfesion!!)
                    .get()
                    .addOnSuccessListener {
                        val profession = Profesion(
                            it.id,
                            it.get("nombre").toString(),
                            it.get("descripcion").toString(),
                            it.get("activa").toString().toBoolean(),
                            it.get("salarioPromedio").toString().toDouble()
                        )
                        val bundle = Bundle()
                        bundle.putString("profesionId", profession.id)
                        bundle.putString("carreraId", carreras[seleccion].id)
                        bundle.putString("nombreCarrera", carreras[seleccion].nombre)
                        bundle.putString("descripcionCarrera", carreras[seleccion].descripcion)
                        bundle.putString("duracionCarrera", carreras[seleccion].duracion.toString())
                        bundle.putString("activa", carreras[seleccion].activa.toString())

                        goToActivity(EditarCarrera::class.java, bundle)
                    }

                true
            }
            R.id.item_eliminar_carrera -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("¿Está seguro que desea eliminar la carrera?")
                    .setCancelable(false)
                    .setPositiveButton("Si") { dialog, id ->
                        val db = Firebase.firestore
                        db.collection("carreras")
                            .document(carreras[seleccion].id)
                            .delete()
                            .addOnSuccessListener {
                                carreras.removeAt(seleccion)
                                adaptador.notifyDataSetChanged()
                                mensaje.mostrarMensaje("Carrera eliminada")
                                //cargarCarreras()
                            }
                            .addOnFailureListener { exception ->
                                Log.w("Error", "Error getting documents.", exception)
                            }
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
        cargarCarreras()
    }

    fun cargarCarreras(){
        val listView = findViewById<ListView>(R.id.lv_carreras)
        adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            carreras
        )

        listView.adapter = adaptador
        adaptador.notifyDataSetChanged()

        carreras.clear()
        val db = Firebase.firestore
        db.collection("carreras")
            .whereEqualTo("idProfesion", idProfesion)
            .get()
            .addOnSuccessListener { result ->
                for (carrera in result) {
                    carreras.add(
                        Carrera(
                            carrera.id,
                            carrera.data["nombre"].toString(),
                            carrera.data["descripcion"].toString(),
                            carrera.data["activa"].toString().toBoolean(),
                            carrera.data["duracion"].toString().toInt(),
                            carrera.data["idProfesion"].toString()
                        )
                    )
                }
                adaptador.notifyDataSetChanged()
                registerForContextMenu(listView)
            }
            .addOnFailureListener { exception ->
                Log.w("Error", "Error getting documents.", exception)
            }
    }

    private fun goToActivity(activity: Class<*>, params: Bundle? = null) {
        val intent = Intent(this, activity)
        if (params != null) {
            intent.putExtras(params)
        }
        startActivity(intent)
    }
}