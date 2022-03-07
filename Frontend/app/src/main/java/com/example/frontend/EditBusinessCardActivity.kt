package com.example.frontend

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.qrcode.QRCodeWriter

class EditBusinessCardActivity : AppCompatActivity() {
    //Set variables
    private lateinit var QRCode : ImageView
    private lateinit var nameData : EditText
    private lateinit var departmentData : EditText
    private lateinit var phoneNumbData : EditText
    private lateinit var officeNumbData : EditText
    private lateinit var emailData : EditText
    private lateinit var websiteData : EditText
    private lateinit var saveData : Button
    private lateinit var qrScan : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editbusinesscard_page)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) //Disable night mode

        //Get input text and button IDs
        QRCode = findViewById(R.id.qrCodeImageView)
        nameData = findViewById(R.id.editNameTextView)
        departmentData = findViewById(R.id.editDepartmentTextView)
        phoneNumbData = findViewById(R.id.editPhoneNumbTextView)
        officeNumbData = findViewById(R.id.editTextPhone)
        emailData = findViewById(R.id.editEmailTextView)
        websiteData = findViewById(R.id.editWebsiteTextView)
        saveData = findViewById(R.id.saveButton)
        qrScan = findViewById(R.id.scanQRButton)

        val bitmap : Bitmap? = ImageSaver(applicationContext).
                                setFileName("myImage.png").
                                setDirectoryName("images").
                                load()
        if (bitmap != null)
            QRCode.setImageBitmap(bitmap)
        else
            QRCode.setImageResource(R.drawable.blueflushicon)



        //QR Code Scanner Button
        qrScan.setOnClickListener {
            val intentIntegrator = IntentIntegrator(this)
            intentIntegrator.setDesiredBarcodeFormats(listOf(IntentIntegrator.QR_CODE))
            intentIntegrator.setOrientationLocked(true) //Lock Orientation
            intentIntegrator.captureActivity = CaptureActivityPortrait::class.java
            intentIntegrator.setBeepEnabled(false)
            intentIntegrator.initiateScan()
        }

        //QR Code Generator Button
        saveData.setOnClickListener {
            val nameString = nameData.text.toString()
            val departmentString = departmentData.text.toString()
            val phoneNumberString = phoneNumbData.text.toString()
            val officeNumberString = officeNumbData.text.toString()
            val emailString = emailData.text.toString()
            val websiteString = websiteData.text.toString()
            //Combine text data into 1 string for encoding into QR Code
            val encodeString = nameString + ":" +
                                departmentString + ":" +
                                phoneNumberString + ":" +
                                officeNumberString + ":" +
                                emailString + ":" +
                                websiteString
            //Remove white space
            encodeString.trim()
            //Check if empty
            if (encodeString.isEmpty()){
                Toast.makeText(this, "Input is empty", Toast.LENGTH_LONG).show()
            }
            else {
                //Generate QR Code
                val writer = QRCodeWriter()
                try {
                    val bitMatrix = writer.encode(encodeString, BarcodeFormat.QR_CODE, 512, 512)
                    val width = bitMatrix.width
                    val height = bitMatrix.height
                    val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)

                    for (x in 0 until width) {
                        for (y in 0 until height){
                            bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
                        }
                    }
                    QRCode.setImageBitmap(bmp)
                    ImageSaver(applicationContext).
                        setFileName("myImage.png").
                        setDirectoryName("images").
                        save(bmp)
                }catch (e: WriterException){
                    e.printStackTrace()
                }
            }
        }
    }

    //QR Code Result Activity
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(resultCode, data) //Save QR Content to a variable
        val stringResult = result.contents //Save QR result Contents to a string
        val delim = ":" //Used as identifier to split string
        val dataArr = stringResult.split(delim).toTypedArray() //Retrieve individual data and store them in array
        if (result != null) {
            AlertDialog.Builder(this) //Dialogue Start
                .setMessage("Save to contacts?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { //If Yes is selected
                        dialogInterface, i ->
                    val intent = Intent(ContactsContract.Intents.Insert.ACTION)
                    intent.type = ContactsContract.RawContacts.CONTENT_TYPE //Initialize Intent to contact app
                    intent.putExtra(ContactsContract.Intents.Insert.NAME, dataArr[0]) //Name
                    intent.putExtra(ContactsContract.Intents.Insert.JOB_TITLE, dataArr[1]) //Department/Job Title
                    intent.putExtra(ContactsContract.Intents.Insert.COMPANY, "Blue Flush") // Company Name
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE, dataArr[2]) // Mobile Number
                    intent.putExtra(ContactsContract.Intents.Insert.PHONE_TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE) // Set Phone Type
                    intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE, dataArr[3]) // Office Number
                    intent.putExtra(ContactsContract.Intents.Insert.SECONDARY_PHONE_TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK) //Set Phone type
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL, dataArr[4]) // Email
                    intent.putExtra(ContactsContract.Intents.Insert.EMAIL_TYPE,
                        ContactsContract.CommonDataKinds.Email.TYPE_WORK) // Set Email type
                    startActivity(intent)
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { //If No is selected
                        dialogInterface, i ->
                })
                .create()
                .show()
        }
    }

}