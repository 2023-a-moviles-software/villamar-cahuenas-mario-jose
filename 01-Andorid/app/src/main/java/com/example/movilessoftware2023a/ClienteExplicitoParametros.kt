package com.example.movilessoftware2023a

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ClienteExplicitoParametros : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cliente_explicito_parametros)
        val nombre: String? = intent.getStringExtra("nombre")
        val apellido: String?= intent.getStringExtra("apellido")
        val edad = intent.getIntExtra("edad",0)
        val boton = findViewById<Button>(R.id.btn_devolver_respuesta)
        boton.setOnClickListener { devolverRespeusta() }

    }
    fun devolverRespeusta(){
        val intentDevolverParametros  = Intent()
        intentDevolverParametros.putExtra("nombreModificado", "Jose")
        intentDevolverParametros.putExtra("edadModificada",33)
        setResult(
            RESULT_OK,
            intentDevolverParametros
        )
        finish()
    }
}