package com.talitamorales.servicegoo.activities

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.talitamorales.servicegoo.R
import com.talitamorales.servicegoo.models.registration.RegistrationMessages

class LoginActivity : AppCompatActivity() {

    private lateinit var editEmailLogin: EditText
    private lateinit var editPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button
    private lateinit var imageViewTogglePassword: ImageView
    private lateinit var auth: FirebaseAuth
    private var isPasswordVisible = false

    fun isValidPassword(password: String): Boolean {
        val passwordPattern = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
                "^(?=.*[a-z])" +
                "(?=.*[A-Z])" +
                "(?=.*\\d)" +
                "(?=.*[@#\\\$%^&*!?_\\+\\-=\\(\\)\\[\\]\\{\\};:\\.,<>/|\\\\])" +
                "[A-Za-z\\d@#\\\$%^&*!?_\\+\\-=\\(\\)\\[\\]\\{\\};:\\.,<>/|\\\\]{8,}\$"
        return passwordPattern.matches(password)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        editEmailLogin = findViewById(R.id.editEmailLogin)
        editPassword= findViewById(R.id.editPassword)
        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)
        imageViewTogglePassword = findViewById(R.id.imageViewTogglePassword)

        imageViewTogglePassword.setOnClickListener {
            if (isPasswordVisible) {
                editPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                imageViewTogglePassword.setImageResource(R.drawable.ic_eye_closed)
            } else {
                editPassword.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                imageViewTogglePassword.setImageResource(R.drawable.ic_eye_open)
            }
            editPassword.setSelection(editPassword.text.length)
            isPasswordVisible = !isPasswordVisible
        }

        btnLogin.setOnClickListener {
            val email = editEmailLogin.text.toString().trim()
            val password = editPassword.text.toString().trim()


            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, RegistrationMessages.emptyFieldError.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (!isValidPassword(password)) {
                Toast.makeText(this, RegistrationMessages.minimumPasswordLenghtError.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show()

                        // Vai para a tela principal
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error while logging in : ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        btnRegister.setOnClickListener {
            val email = editEmailLogin.text.toString().trim()
            val password = editPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, RegistrationMessages.emptyFieldError.message, Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else if (isValidPassword(password)) {
                Toast.makeText(this, RegistrationMessages.minimumPasswordLenghtError.message, Toast.LENGTH_SHORT).show()
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