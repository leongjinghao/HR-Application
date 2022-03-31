package com.example.frontend.Activities

import android.os.Bundle
import android.util.Patterns
import android.view.Gravity
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityForgetpasswordBinding

class ForgetPasswordActivity : AppCompatActivity()  {

    lateinit var binding: ActivityForgetpasswordBinding

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding = ActivityForgetpasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        binding.buttonRecover.setOnClickListener{
            if(validateEmail(binding.editTextEmail)){
                //TODO Send password reset email logic
                finish()
            }
        }
    }

    private fun validateEmail(email:EditText):Boolean{
        if(email.text.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email.text).matches()){
            //TODO Validate user input with email from database
            toastMsg("Password recovery successful\nPlease check your email")
            return true
        }
        else{
            toastMsg("Invalid email address")
            return false
        }
    }

    private fun toastMsg(message:String){
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0,0)
        toast.show()
    }

}