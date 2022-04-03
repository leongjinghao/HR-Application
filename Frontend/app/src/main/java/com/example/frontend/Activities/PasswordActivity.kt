package com.example.frontend.Activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.frontend.R
import com.example.frontend.retroAPI.api.model.userInformationModel
import com.example.frontend.retroAPI.api.repository.Repository
import com.example.frontend.retroAPI.api.viewModel.apiViewModel
import com.example.frontend.retroAPI.api.viewModel.apiViewModelFactory
import androidx.lifecycle.Observer

class PasswordActivity : AppCompatActivity() {
    private lateinit var apiCall : apiViewModel
    private lateinit var userInfoData : userInformationModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepassword)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Disable night mode

        //Get UI Elements
        val txtCurrPass = findViewById<EditText>(R.id.txtCurrPass)
        val txtNewPass = findViewById<EditText>(R.id.txtNewPass)
        val txtConfirmPass = findViewById<EditText>(R.id.txtConfirmPass)
        val confirmPassButton = findViewById<Button>(R.id.confirmPassButton)

        val repository = Repository()
        val apiModelFactory = apiViewModelFactory(repository)
        apiCall = ViewModelProvider(this,apiModelFactory).get(apiViewModel::class.java)
        userInfoData = userInformationModel(null)

        val password : String

        apiCall.getUserInformation("Ali456")
        apiCall.userInformationRes.observe(this, Observer { response ->
            userInfoData = response
            //password = userInfoData.Items?.get(0)?.
            val employeeName = userInfoData.Items?.get(0)?.EmployeeName?.S.toString()
            val departmentName = userInfoData.Items?.get(0)?.Department?.S.toString()
            val dateOfBirth = userInfoData.Items?.get(0)?.DOB?.S.toString()
            val mobileNumber = userInfoData.Items?.get(0)?.Mobile?.S.toString()
            val email = userInfoData.Items?.get(0)?.Email?.S.toString()
            //txtAccName.text = employeeName
        })
    }
}