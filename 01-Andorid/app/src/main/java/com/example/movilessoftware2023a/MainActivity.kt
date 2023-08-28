package com.example.movilessoftware2023a

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
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
    val callbackIntentPickUri =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ){
            result ->
            if(result.resultCode === RESULT_OK){
                if(result.data != null){
                    if(result.data!!.data != null){
                        val uri: Uri = result.data!!.data!!
                        val cursor = contentResolver.query(
                            uri,
                            null,
                            null,
                            null,
                            null,
                            null)
                        cursor?.moveToFirst()
                        val indiceTelefono = cursor?.getColumnIndex(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                        val telefono =  cursor?.getString(indiceTelefono!!)
                        cursor?.close()
                        "Telefono ${telefono}"
                    }
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        EBaseDeDatos.tablaEntrenador = ESqLiteHelperEntrenador(this)

        val botonCicloVida =findViewById<Button>(
            R.id.btn_ciclo_vida
        )
        botonCicloVida.setOnClickListener{
            irActividad(AACicloVida::class.java)
        }

        val botonListView = findViewById<Button>(R.id.btn_ir_list_view)

        botonListView.setOnClickListener{
            irActividad(BListView::class.java)
        }

        val  botonIntentImplicito = findViewById<Button>(R.id.btn_ir_intent_implicito)

        botonIntentImplicito.setOnClickListener {
            val intentConRespuesta = Intent(
                Intent.ACTION_PICK,
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            )
            callbackIntentPickUri.launch(intentConRespuesta)
        }
        val botonIntentExpIicito = findViewById<Button>(R.id.btn_ir_intent_explicito)

        botonIntentExpIicito
                .setOnClickListener {
                    abrirActividadConParametros(CIntentExplicitoParametros:: class.java)
                }

        val botonSqlite = findViewById<Button>(R.id.btn_sqlite)
        botonSqlite
            .setOnClickListener {
                irActividad(ECrudEntrenador::class.java)
            }
        val botonRView = findViewById<Button>(R.id.btn_revcycler_view)
        botonRView
            .setOnClickListener {
                irActividad(FRecyclerView::class.java)
            }
        val botonGMaps = findViewById<Button>(R.id.btn_google_maps)
        botonGMaps
            .setOnClickListener {
                irActividad(GGoogleMaps::class.java)
            }
        val botonUiauth = findViewById<Button>(R.id.btn_intent_firebase_ui)
        botonUiauth
            .setOnClickListener {
                irActividad(HFirebaseUIAuth::class.java)
            }
        val botonFirestore = findViewById<Button>(R.id.btn_intent_firestore)
        botonFirestore
            .setOnClickListener {
                irActividad(Ifirestore::class.java)
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
        intentoExplicito.putExtra("entrenador",BEntrenador(
            1, "Mario","Entrenador"
        ))
        //enviamos intent con Respeusta
        // recibimos respuesta
        callbackContenidoIntentExplicito.launch(intentoExplicito)
    }
}