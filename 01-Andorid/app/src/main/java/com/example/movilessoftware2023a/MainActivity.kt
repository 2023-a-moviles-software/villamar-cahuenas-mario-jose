package com.example.movilessoftware2023a

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.result.contract.ActivityResultContracts

class MainActivity : AppCompatActivity() {

    val callbackContenidoIntentExplicito =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if(result.resultCode === Activity.RESULT_OK){
                if(result.data != null){
                    val data = result.data
                    "${data?.getStringArrayExtra("nombreModificado")}"
                }
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val botonCicloVida =findViewById<Button>(
            R.id.btn_ciclo_vida
        )
        botonCicloVida.setOnClickListener{
            irActividad(AACicloVida::class.java)
        }

        val botonListView = findViewById<Button>(R.id.btn_ir_view)
        botonListView.setOnClickListener{
            irActividad(BListView::class.java)
        }
    }
    fun irActividad(
        clase: Class<*>
    ){
        val intent = Intent(this,clase)
        startActivity(intent)
    }

    fun abrirActividadConParametros(
        clase: Class<*>
    ){
        val intentoExplicito = Intent(this, clase)
        //Enviar parametros
        // (aceptamos primitivas)
        intentoExplicito.putExtra("nombre", "Mario")
        intentoExplicito.putExtra("apellido","Villamar")
        intentoExplicito.putExtra("edad",25)
        //enviamos intent con Respeusta
        // recibimos respuesta
        callbackContenidoIntentExplicito.launch(intentoExplicito)
    }
}