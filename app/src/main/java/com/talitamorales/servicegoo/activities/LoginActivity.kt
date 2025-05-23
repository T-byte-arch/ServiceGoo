package com.talitamorales.servicegoo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.talitamorales.servicegoo.R

class LoginActivity : AppCompatActivity() {

    private lateinit var editEmailLogin: EditText
    private lateinit var editPasswordLogin: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var txttogoRegister: TextView
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        editEmailLogin = findViewById(R.id.editEmailLogin)
        editPasswordLogin = findViewById(R.id.editPasswordLogin)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        txttogoRegister = findViewById(R.id.txtogoRegister)

        btnLogin.setOnClickListener {
            val email = editEmailLogin.text.toString()
            val password = editPasswordLogin.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "login successful", Toast.LENGTH_SHORT).show()
                            Log.d("FirebaseAuth", "logged in user: ${auth.currentUser?.email}")
                        }
                    }
                //TODO: Redirecionar para a tela principal do app
                // startActivity(Intent(this, MainActivity::class.java))
                // finish()
            } else {
                Toast.makeText(this, "fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
        txttogoRegister.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}