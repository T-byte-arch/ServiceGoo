package com.talitamorales.servicegoo.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.talitamorales.servicegoo.R
import com.talitamorales.servicegoo.models.registration.RegistrationMessages

class RegisterActivity : AppCompatActivity() {

    private lateinit var editEmailRegister: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnRegistrar: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        editEmailRegister = findViewById(R.id.editEmailRegister)
        editPassword = findViewById(R.id.editPassword)
        btnRegistrar = findViewById(R.id.btnRegister)


        btnRegistrar.setOnClickListener {
            val email = editEmailRegister.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, RegistrationMessages.emptyFieldError.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "You successfully registered!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        Toast.makeText(this, "We had an error with your request. Please check your internet connection and try again: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}

