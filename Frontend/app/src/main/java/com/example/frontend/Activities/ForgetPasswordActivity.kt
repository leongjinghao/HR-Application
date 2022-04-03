package com.example.frontend.Activities

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.frontend.databinding.ActivityForgetpasswordBinding
import com.example.frontend.retroAPI.api.repository.Repository
import com.example.frontend.retroAPI.api.viewModel.apiViewModel
import com.example.frontend.retroAPI.api.viewModel.apiViewModelFactory

val TAG = "ForgetPassword"

class ForgetPasswordActivity : AppCompatActivity()  {

    lateinit var binding: ActivityForgetpasswordBinding
    lateinit var apiCall: apiViewModel

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityForgetpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val repository = Repository()
        val apiModelFactory = apiViewModelFactory(repository)
        apiCall = ViewModelProvider(this,apiModelFactory).get(com.example.frontend.retroAPI.api.viewModel.apiViewModel::class.java)

        binding.buttonRecover.setOnClickListener{
            if(validateEmail(binding.editTextEmail)){
                //TODO Send password reset email logic
                val userEmail = binding.editTextEmail.text.toString()
                apiCall.retrieveUserEmails(userEmail)
                apiCall.returnRespondModelRes.observe(this) { response ->
                    Log.d("TAG", "$response")
                    if (response.result) {
                        apiCall.sendRecoverEmail(userEmail)
                        finish()
                    } else {
                        Toast.makeText(this, "Email Not Found", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun validateEmail(email:EditText):Boolean{
        if(email.text.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.text).matches()){
            //TODO Validate user input with email from database
            return true
        }
        else{
            toastMsg("Invalid email address")
            return false
        }
    }

    private fun toastMsg(message:String){
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }

}