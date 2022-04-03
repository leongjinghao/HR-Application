package com.example.frontend.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.frontend.R
import com.example.frontend.login.UserIdRepo
import com.example.frontend.retroAPI.api.model.currentPasswordModel
import com.example.frontend.retroAPI.api.repository.Repository
import com.example.frontend.retroAPI.api.viewModel.apiViewModel
import com.example.frontend.retroAPI.api.viewModel.apiViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PasswordActivity : AppCompatActivity() {

    private lateinit var apiCall : apiViewModel
    private lateinit var userPasswordData : currentPasswordModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepassword)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Disable night mode

        //Get UI Elements
        val txtCurrPass = findViewById<EditText>(R.id.txtCurrPass)
        val txtNewPass = findViewById<EditText>(R.id.txtNewPass)
        val txtConfirmPass = findViewById<EditText>(R.id.txtConfirmPass)
        val confirmPassButton = findViewById<Button>(R.id.confirmPassButton)

        var currPasswordCorrect : Boolean = false

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val repository = Repository()
        val apiModelFactory = apiViewModelFactory(repository)
        apiCall = ViewModelProvider(this, apiModelFactory).get(apiViewModel::class.java)

        // retrieve userId from preference store
        var userId: String = ""

        lifecycleScope.launch {
            UserIdRepo.getInstance(this@PasswordActivity).userPreferencesFlow.collect { settings ->
                userId = settings.id
            }
        }

        confirmPassButton.setOnClickListener {
            apiCall.getUserPassword(userId)
            apiCall.currentPasswordRes.observe(this, Observer { response ->
                userPasswordData = response
                if (txtCurrPass.text.toString() == response.password) {
                    if (txtNewPass.text.toString() == txtConfirmPass.text.toString()) {
                        apiCall.updateUserPassword(userId, txtNewPass.text.toString())
                        Toast.makeText(this, "Password Changed", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    else {
                        Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(this, "Current Password is Wrong", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}