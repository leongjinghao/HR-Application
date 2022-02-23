package com.example.frontend

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.frontend.databinding.ActivityMainBinding
import org.w3c.dom.Text

class HomePageMainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.homepage_layout)

        //Get all Text
        val employeeNameTextView = findViewById<TextView>(R.id.nameTextView)
        val departmentNameTextView = findViewById<TextView>(R.id.departmentTextView)
        val officeNoTextView = findViewById<TextView>(R.id.officeNoTextView)
        val smartPhoneTextView = findViewById<TextView>(R.id.smartPhoneTextView)
        val websiteTextView = findViewById<TextView>(R.id.websiteTextView)
        val emailTextView = findViewById<TextView>(R.id.emailTextView)

        //Get all Buttons and Interactables
        val checkInButton = findViewById<ImageButton>(R.id.checkInButton)
        val calendarButton = findViewById<ImageButton>(R.id.calendarButton)
        val leaveButton = findViewById<ImageButton>(R.id.leaveButton)
        val NFCScanButton = findViewById<ImageButton>(R.id.NFCButton)
        val accountButton = findViewById<ImageButton>(R.id.accountButton)
        val leaveApprButton = findViewById<ImageButton>(R.id.leaveApprButton)
        val nameCardButton = findViewById<CardView>(R.id.nameCardView)


        //TO-DO:
        //1) get employee details from database and set the name card details
        //2) Feedback for name card interaction
        //3) Once other pages are completed, update button interaction

        //Set on Click Long Listeners for name card butoton
        nameCardButton.setOnLongClickListener {
            Toast.makeText(this, "Moving to Edit Card Page", Toast.LENGTH_LONG).show()
            true
        }

        //Set all OnClick Listeners
        checkInButton.setOnClickListener {
            Toast.makeText(this, "Moving to Check In Page", Toast.LENGTH_LONG).show()
        }

        calendarButton.setOnClickListener {
            Toast.makeText(this, "Moving to Calendar Page", Toast.LENGTH_LONG).show()
        }

        leaveButton.setOnClickListener {
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






    }


}