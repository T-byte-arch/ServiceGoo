package com.talitamorales.servicegoo.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.talitamorales.servicegoo.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var editEmail: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        editEmail = findViewById(R.id.editEmail)
        editPassword = findViewById(R.id.editPassword)
        btnRegistrar = findViewById(R.id.btnRegister)

        btnRegistrar.setOnClickListener {
            val email = editEmail.text.toString()
            val senha = editPassword.text.toString()

            if (email.isNotEmpty() && senha.isNotEmpty()) {
                auth.createUserWithEmailAndPassword(email, senha)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this,
                                "Registration completed successfully!",
                                Toast.LENGTH_SHORT
                            ).show()
                            Log.d("FirebaseAuth", "User: ${auth.currentUser?.email}")
                        } else {
                            Toast.makeText(
                                this,
                                "error when registering: ${task.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

