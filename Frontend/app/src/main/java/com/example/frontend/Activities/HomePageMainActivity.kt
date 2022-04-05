package com.example.frontend.Activities

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import com.example.frontend.Activities.leaveModule.LeaveCalendarActivity
import com.example.frontend.Activities.leaveModule.LeaveSummaryActivity
import com.example.frontend.Activities.leaveModule.utilities.beforeLeaveCalendar
import com.example.frontend.R
import com.example.frontend.Utilities.CardDetailsManager
import com.example.frontend.Utilities.ImageSaver
import com.example.frontend.login.UserIdRepo
import com.example.frontend.retroAPI.api.repository.Repository
import com.example.frontend.retroAPI.api.viewModel.apiViewModel
import com.example.frontend.retroAPI.api.viewModel.apiViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomePageMainActivity : AppCompatActivity() {
    private lateinit var cardDetailsManager: CardDetailsManager
    private lateinit var employeeNameTextView : TextView
    private lateinit var departmentNameTextView : TextView
    private lateinit var smartPhoneTextView : TextView
    private lateinit var officeNoTextView : TextView
    private lateinit var emailTextView : TextView
    private lateinit var websiteTextView : TextView
    private lateinit var qrCode : ImageView

    @RequiresApi(Build.VERSION_CODES.P)
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
        val accountButton = findViewById<ImageButton>(R.id.accountButton)
        val nameCardButton = findViewById<CardView>(R.id.nameCardView)
        val informationButton = findViewById<ImageButton>(R.id.informationButton)
        qrCode = findViewById(R.id.qrCode)

        informationText.text = ""
        var toggled = 0

        cardDetailsManager = CardDetailsManager(this)

        //Retrieve Card Details Data from User Preference Datastore
        observeData()

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
            var userId = ""
            lifecycleScope.launch {
                UserIdRepo.getInstance(this@HomePageMainActivity).userPreferencesFlow.collect { settings ->
                    userId = settings.id
                }
            }

            beforeLeaveCalendar(this,this,this,userId)
        }

        leaveButton.setOnClickListener {
            val intent = Intent(this, LeaveSummaryActivity::class.java)
            startActivity(intent)
        }

        accountButton.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
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
    }

    override fun onStart() {
        super.onStart()
        val bitmap : Bitmap? = ImageSaver(applicationContext).
        setFileName("QRImage.png").
        setDirectoryName("images").
        load()
        if (bitmap != null)
            qrCode.setImageBitmap(bitmap)
        else
            qrCode.setImageResource(R.drawable.qrcodeprompt)
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
                smartPhoneTextView.text = phoneNumber  //Set retrieved data to office number

            }
        }
        cardDetailsManager.officeFlow.asLiveData().observe(this){ officeNumber->
            officeNumber?.let {
                officeNoTextView.text = officeNumber  //Set retrieved data to phone number
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