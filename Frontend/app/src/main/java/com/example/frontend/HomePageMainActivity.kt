package com.example.frontend

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.cardview.widget.CardView
import com.example.frontend.leaveModule.LeaveCalendarActivity
import com.example.frontend.leaveModule.LeaveSummaryActivity


class HomePageMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage_layout)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Disable night mode

        //Get all Text
        val employeeNameTextView = findViewById<TextView>(R.id.nameTextView)
        val departmentNameTextView = findViewById<TextView>(R.id.departmentTextView)
        val officeNoTextView = findViewById<TextView>(R.id.officeNoTextView)
        val smartPhoneTextView = findViewById<TextView>(R.id.smartPhoneTextView)
        val websiteTextView = findViewById<TextView>(R.id.websiteTextView)
        val emailTextView = findViewById<TextView>(R.id.emailTextView)
        val informationText = findViewById<TextView>(R.id.informationText)

        //Get all Buttons and Interactables
        val checkInButton = findViewById<ImageButton>(R.id.checkInButton)
        val calendarButton = findViewById<ImageButton>(R.id.calendarButton)
        val leaveButton = findViewById<ImageButton>(R.id.leaveButton)
        val NFCScanButton = findViewById<ImageButton>(R.id.NFCButton)
        val accountButton = findViewById<ImageButton>(R.id.accountButton)
        val leaveApprButton = findViewById<ImageButton>(R.id.leaveApprButton)
        val nameCardButton = findViewById<CardView>(R.id.nameCardView)
        val informationButton = findViewById<ImageButton>(R.id.informationButton)

        informationText.text = ""
        var toggled : Int = 0


        //TO-DO:
        //1) get employee details from edit card page
//        employeeNameTextView.text = intent.getStringExtra("nameString")
//        departmentNameTextView.text = intent.getStringExtra("departmentString")
//        officeNoTextView.text = intent.getStringExtra("officeNoString")
//        smartPhoneTextView.text = intent.getStringExtra("phoneNumberString")
//        emailTextView.text = intent.getStringExtra("emailString")
//        websiteTextView.text = intent.getStringExtra("websiteString")

        //2) Feedback for name card interaction
        //3) Once other pages are completed, update button interaction

        //Set on Click Long Listeners for name card butoton
        nameCardButton.setOnLongClickListener {
            //Toast.makeText(this, "Moving to Edit Card Page", Toast.LENGTH_LONG).show()
            val intent = Intent(this, EditBusinessCardActivity::class.java)
            startActivity(intent)
            true
        }

        //Set all OnClick Listeners
        checkInButton.setOnClickListener {
//            Toast.makeText(this, "Moving to Check In Page", Toast.LENGTH_LONG).show()
            val intent = Intent(this, CheckInOutActivity::class.java)
            startActivity(intent)
        }

        calendarButton.setOnClickListener {
            val intent = Intent(this, LeaveCalendarActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Moving to Calendar Page", Toast.LENGTH_LONG).show()
        }

        leaveButton.setOnClickListener {
            val intent = Intent(this, LeaveSummaryActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Moving to Leave Page", Toast.LENGTH_LONG).show()
        }

        NFCScanButton.setOnClickListener {
            Toast.makeText(this, "Moving to NFC Scan Page", Toast.LENGTH_LONG).show()
        }

        accountButton.setOnClickListener {
            Toast.makeText(this, "Moving to Account Page", Toast.LENGTH_LONG).show()
        }

        leaveApprButton.setOnClickListener {
            Toast.makeText(this, "Moving to Leave Approve Page", Toast.LENGTH_LONG).show()
        }

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


}