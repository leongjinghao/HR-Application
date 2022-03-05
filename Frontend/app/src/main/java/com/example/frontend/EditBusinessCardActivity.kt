package com.example.frontend

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.createBitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter

class EditBusinessCardActivity : AppCompatActivity() {
    //Set variables
    private lateinit var QRCode : ImageView
    private lateinit var nameData : EditText
    private lateinit var departmentData : EditText
    private lateinit var phoneNumbData : EditText
    private lateinit var emailData : EditText
    private lateinit var websiteData : EditText
    private lateinit var saveData : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editbusinesscard_page)

        //Get input text and button IDs
        QRCode = findViewById(R.id.qrCodeImageView)
        nameData = findViewById(R.id.editNameTextView)
        departmentData = findViewById(R.id.editDepartmentTextView)
        phoneNumbData = findViewById(R.id.editPhoneNumbTextView)
        emailData = findViewById(R.id.editEmailTextView)
        websiteData = findViewById(R.id.editWebsiteTextView)
        saveData = findViewById(R.id.saveButton)

        saveData.setOnClickListener {
            //Combine text data into 1 string for encoding into QR Code
            val encodeString = nameData.text.toString() + ":" +
                                departmentData.text.toString() + ":" +
                                phoneNumbData.text.toString() + ":" +
                                emailData.text.toString() + ":" +
                                websiteData.text.toString()
            //Remove white space
            encodeString.trim()
            //Check if empty
            if (encodeString.isEmpty()){
                Toast.makeText(this, "Input is empty", Toast.LENGTH_LONG).show()
            }
            else {
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
                }catch (e: WriterException){
                    e.printStackTrace()
                }
            }
        }
    }
}