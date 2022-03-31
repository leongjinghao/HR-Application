package com.example.frontend

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.frontend.leaveModule.LeaveCalendarActivity
import com.example.frontend.leaveModule.LeaveSummaryActivity
import com.example.frontend.retroAPI.api.repository.Repository
import com.example.frontend.retroAPI.api.viewModel.apiViewModel
import com.example.frontend.retroAPI.api.viewModel.apiViewModelFactory
import androidx.lifecycle.Observer
import com.example.frontend.retroAPI.api.model.userInformationModel

class HomePageMainActivity : AppCompatActivity() {
    private lateinit var cardDetailsManager: CardDetailsManager
    private lateinit var employeeNameTextView : TextView
    private lateinit var departmentNameTextView : TextView
    private lateinit var smartPhoneTextView : TextView
    private lateinit var officeNoTextView : TextView
    private lateinit var emailTextView : TextView
    private lateinit var websiteTextView : TextView
    private lateinit var apiCall : apiViewModel
    private lateinit var userInfoData : userInformationModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage_layout)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Disable night mode

        //Get all Text
        employeeNameTextView = findViewById(R.id.nameTextView)
        departmentNameTextView = findViewById(R.id.departmentTextView)
        officeNoTextView = findViewById(R.id.officeNoTextView)
        smartPhoneTextView = findViewById(R.id.smartPhoneTextView)
        websiteTextView = findViewById(R.id.websiteTextView)
        emailTextView = findViewById(R.id.emailTextView)
        val informationText = findViewById<TextView>(R.id.informationText)

        //Get all Buttons
        val checkInButton = findViewById<ImageButton>(R.id.checkInButton)
        val calendarButton = findViewById<ImageButton>(R.id.calendarButton)
        val leaveButton = findViewById<ImageButton>(R.id.leaveButton)
        val nfcScanButton = findViewById<ImageButton>(R.id.NFCButton)
        val accountButton = findViewById<ImageButton>(R.id.accountButton)
        val leaveApproveButton = findViewById<ImageButton>(R.id.leaveApprButton)
        val nameCardButton = findViewById<CardView>(R.id.nameCardView)
        val informationButton = findViewById<ImageButton>(R.id.informationButton)
        val qrCode = findViewById<ImageView>(R.id.qrCode)

        val repository = Repository()
        val apiModelFactory = apiViewModelFactory(repository)
        apiCall = ViewModelProvider(this,apiModelFactory).get(apiViewModel::class.java)
        userInfoData = userInformationModel(null)

        informationText.text = ""
        var toggled = 0

        cardDetailsManager = CardDetailsManager(this)

        apiCall.getUserInformation("Ali456")
        apiCall.userInformationRes.observe(this, Observer { response ->
            userInfoData = response
            Log.i("Name:", response.toString())
            val employeeName = userInfoData.Items?.get(0)?.Name?.S.toString()
            employeeNameTextView.text = employeeName
        })

        //val departmentName = userInfoData.Items?.get(0)?.Department
        //get employeeID from login page and retrieve details based on employeeID

//        departmentNameTextView.text = ""
//        officeNoTextView.text = ""
//        smartPhoneTextView.text = ""
//        emailTextView.text = ""
//        websiteTextView.text = ""


        //Set on Click Long Listeners for name card button
        nameCardButton.setOnLongClickListener {
            //Toast.makeText(this, "Moving to Edit Card Page", Toast.LENGTH_LONG).show()
            val intent = Intent(this, EditBusinessCardActivity::class.java)
            startActivity(intent)
            true
        }

        //Set all OnClick Listeners
        checkInButton.setOnClickListener {
            val intent = Intent(this, CheckInOutActivity::class.java)
            startActivity(intent)
        }

        calendarButton.setOnClickListener {
            val intent = Intent(this, LeaveCalendarActivity::class.java)
            startActivity(intent)
        }

        leaveButton.setOnClickListener {
            val intent = Intent(this, LeaveSummaryActivity::class.java)
            startActivity(intent)
        }

        nfcScanButton.setOnClickListener {
            Toast.makeText(this, "Moving to NFC Scan Page", Toast.LENGTH_LONG).show()
        }

        accountButton.setOnClickListener {
            Toast.makeText(this, "Moving to Account Page", Toast.LENGTH_LONG).show()
        }

        leaveApproveButton.setOnClickListener {
            Toast.makeText(this, "Moving to Leave Approve Page", Toast.LENGTH_LONG).show()
        }

        //Toggle Button for Card Details Info
        informationButton.setOnClickListener {
            if (toggled == 0){
                informationText.text = getString(R.string.cardButtonHint)
                toggled = 1
            }
            else if (toggled == 1) {
                informationText.text = ""
                toggled = 0
            }
        }

        //Retrieve Card Details Data from User Preference Datastore
        //observeData()
    }

    //Retrieve Card Details Function using LiveData
    private fun observeData() {
        cardDetailsManager.nameFlow.asLiveData().observe(this){ name->
            name?.let {
                employeeNameTextView.text = name //Set retrieved data to employee name
            }
        }
        cardDetailsManager.departmentFlow.asLiveData().observe(this){ department->
            department?.let {
                departmentNameTextView.text = department //Set retrieved data to department
            }
        }
        cardDetailsManager.phoneFlow.asLiveData().observe(this){ phoneNumber->
            phoneNumber?.let {
                val numberString = "+65 $phoneNumber" //Add +65 at the front
                smartPhoneTextView.text = numberString  //Set retrieved data to office number
            }
        }
        cardDetailsManager.officeFlow.asLiveData().observe(this){ officeNumber->
            officeNumber?.let {
                val numberString = "+65 $officeNumber" //Add +65 at the front
                officeNoTextView.text = numberString  //Set retrieved data to phone number
            }
        }
        cardDetailsManager.emailFlow.asLiveData().observe(this){ email->
            email?.let {
                emailTextView.text = email  //Set retrieved data to email
            }
        }
        cardDetailsManager.websiteFlow.asLiveData().observe(this){ website->
            website?.let {
                websiteTextView.text = website  //Set retrieved data to website
            }
        }
    }
}