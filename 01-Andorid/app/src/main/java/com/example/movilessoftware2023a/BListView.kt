package com.example.movilessoftware2023a

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
import android.view.View
import android.view.View.OnCreateContextMenuListener
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AlertDialog

class BListView : AppCompatActivity() {
    val arreglo = BbaseDeDatosMemoria.arregloBEntrenador
    var idItemSeleccionado = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blist_view)
        // addaptador
        val lisView = findViewById<ListView>(R.id.lv_list_view)
        val adaptador = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_1,
            arreglo
        )
        lisView.adapter = adaptador
        adaptador.notifyDataSetChanged()
        val botonAnadirListView = findViewById<Button>(R.id.btn_anadir_list_view)
        botonAnadirListView.setOnClickListener{
            anadirEntrenador(adaptador)
        }
        registerForContextMenu(lisView)
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //obtner el id del item selecionado
        val info= menuInfo as AdapterView.AdapterContextMenuInfo
        val id = info.position
        idItemSeleccionado = id
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.mi_editar ->{
                "Hacer algo con ${idItemSeleccionado}"
                return true
            }
            R.id.mi_eliminar -> {
                abrirDialogo()
                "Hacer algo con ${idItemSeleccionado}"
                return true
            }
            else -> super.onContextItemSelected(item)
        }

    }
    fun anadirEntrenador(
        adaptador: ArrayAdapter<BEntrenador>
    ){
        arreglo.add(
            BEntrenador(4,"Mario","Villamar")
        )
        adaptador.notifyDataSetChanged()
    }
    fun abrirDialogo(){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Desea eleiminar?")
        builder.setPositiveButton(
            "Aceptar",
            DialogInterface.OnClickListener{
                dialog, which ->
            }
        )
        builder.setNegativeButton("Cancelar",null)

        val opciones = resources.getStringArray(R.array.string_array_opciones_dialogo)
        val seleccionPrevia = booleanArrayOf(
            true,
            false,
            false
        )
        builder.setMultiChoiceItems(
            opciones,
            seleccionPrevia,
            {
                dialog,which, isChecked -> "Dio Clic en item ${which}"
            }
        )
        val dialogo  =  builder.create()
        dialogo.show()
    }

}