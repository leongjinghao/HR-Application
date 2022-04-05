package com.example.frontend.Activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.example.frontend.R
import com.example.frontend.Utilities.ImageSaver
import com.example.frontend.retroAPI.api.model.userInformationModel
import com.example.frontend.retroAPI.api.repository.Repository
import com.example.frontend.retroAPI.api.viewModel.apiViewModel
import com.example.frontend.retroAPI.api.viewModel.apiViewModelFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.frontend.login.UserIdRepo
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class AccountActivity : AppCompatActivity() {

    private lateinit var profilePhoto : ImageView
    private lateinit var apiCall : apiViewModel
    private lateinit var userInfoData : userInformationModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Disable night mode

        //Get UI Elements
        profilePhoto = findViewById(R.id.accountPhoto)
        val txtAccName = findViewById<TextView>(R.id.txtAccName)
        val txtDepartment = findViewById<TextView>(R.id.txtDepartment)
        val txtAccDoB = findViewById<TextView>(R.id.txtAccDoB)
        val txtAccMobile = findViewById<TextView>(R.id.txtAccMobile)
        val txtAccEmail = findViewById<TextView>(R.id.txtAccEmail)
        val btnChangePass = findViewById<TextView>(R.id.btnChangePass)
        val btnAccEdit = findViewById<Button>(R.id.btnAccEdit)

        val repository = Repository()
        val apiModelFactory = apiViewModelFactory(repository)
        apiCall = ViewModelProvider(this,apiModelFactory).get(apiViewModel::class.java)
        userInfoData = userInformationModel(null)

        // retrieve userId from preference store
        var userId: String = ""

        lifecycleScope.launch {
            UserIdRepo.getInstance(this@AccountActivity).userPreferencesFlow.collect { settings ->
                userId = settings.id
            }
        }

        apiCall.getUserInformation(userId)
        apiCall.userInformationRes.observe(this, Observer { response ->
            userInfoData = response
            val employeeName = userInfoData.Items?.get(0)?.EmployeeName?.S.toString()
            val departmentName = userInfoData.Items?.get(0)?.Department?.S.toString()
            val dateOfBirth = userInfoData.Items?.get(0)?.DOB?.S.toString()
            val mobileNumber = userInfoData.Items?.get(0)?.Mobile?.S.toString()
            val email = userInfoData.Items?.get(0)?.Email?.S.toString()
            txtAccName.text = employeeName
            txtDepartment.text = departmentName
            txtAccDoB.text = dateOfBirth
            txtAccMobile.text = mobileNumber
            txtAccEmail.text = email
        })

        btnChangePass.setOnClickListener {
            val intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent)
        }

        btnAccEdit.setOnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        if (loadBitmapImage() != null)
            profilePhoto.setImageBitmap(loadBitmapImage())
        else
            profilePhoto.setImageResource(R.drawable.nameinputicon)
    }

    //Load Bitmap based on Filename
    private fun loadBitmapImage(): Bitmap? {
        return ImageSaver(applicationContext).setFileName("ProfilePhoto.png")
            .setDirectoryName("images").load()
    }
}