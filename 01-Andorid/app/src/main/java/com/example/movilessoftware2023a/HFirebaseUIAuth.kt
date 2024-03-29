package com.example.movilessoftware2023a

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth

class HFirebaseUIAuth : AppCompatActivity() {

    // callback del INTENT de LOGIN
    private val respuestaLoginAuthUi = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) {
            res: FirebaseAuthUIAuthenticationResult ->
        if (res.resultCode == RESULT_OK) {
            if (res.idpResponse != null) {
                // Logica de negocio
                seLogeo(res.idpResponse!!)
            }
        }
    }

    fun seLogeo(res: IdpResponse) {
        val btnLogin: Button = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)

        tvBienvenido.text = "Bienvenido ${FirebaseAuth.getInstance().currentUser?.displayName}"
        btnLogout.visibility = View.VISIBLE
        btnLogin.visibility = View.INVISIBLE

        if (res.isNewUser == true) {
            registrarUsuarioPorPrimeraVez(res)
        }
    }

    fun seDeslogeo() {
        val btnLogin: Button = findViewById<Button>(R.id.btn_login_firebase)
        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)

        tvBienvenido.text = "Bienvenido"
        btnLogout.visibility = View.INVISIBLE
        btnLogin.visibility = View.VISIBLE

        FirebaseAuth.getInstance().signOut()
    }

    fun registrarUsuarioPorPrimeraVez(usuario: IdpResponse) {
        /* usuario.email; usuario.phoneNumber; usuario.user.name */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hfirebase_uiauth)

        val btnLogin = findViewById<Button>(R.id.btn_login_firebase)
        btnLogin.setOnClickListener {
            val providers = arrayListOf(
                // Arreglo de proveedores de autenticacion
                // Ej. Correo, Google, Facebook, Twitter, etc.
                AuthUI.IdpConfig.EmailBuilder().build()
            )

            // Construimos el intent de login
            val signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build()

            // Respuesta del Intent de Login
            respuestaLoginAuthUi.launch(signInIntent)
        }

        val btnLogout = findViewById<Button>(R.id.btn_logout_firebase)
        btnLogout.setOnClickListener { seDeslogeo() }

        val usuario = FirebaseAuth.getInstance().currentUser
        if (usuario != null) {
            val tvBienvenido = findViewById<TextView>(R.id.tv_bienvenido)
            btnLogin.visibility = View.INVISIBLE
            btnLogout.visibility = View.VISIBLE
            tvBienvenido.text = "Bienvenido ${usuario.displayName}"
        }
    }

}