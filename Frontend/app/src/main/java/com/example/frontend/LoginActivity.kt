package com.example.frontend

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity()  {

    val TAG : String = "Login"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // findViewById for UI elements
        val usernameEditText = findViewById<EditText>(R.id.editTextUsernameLogin)
        val passwordEditText = findViewById<EditText>(R.id.editTextPasswordLogin)
        val recoverClickableText = findViewById<TextView>(R.id.textViewRecoverLogin)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val fingerprintLoginButton = findViewById<Button>(R.id.buttonFingerprintLogin)

        recoverClickableText.setOnClickListener {
            // TODO create intent to recover page
        }

        loginButton.setOnClickListener {
            // TODO implement authentication logic

            // TODO create intent to Home page
        }

        fingerprintLoginButton.setOnClickListener {
            // TODO implement authentication logic

            // TODO create intent to Home page
        }

    }
}