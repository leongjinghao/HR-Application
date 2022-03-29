package com.example.frontend

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import coil.load
import coil.transform.CircleCropTransformation
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener

class EditProfileActivity : AppCompatActivity() {

    private val CAMERAREQUESTCODE = 1
    private val GALLERYREQUESTCODE = 2
    private lateinit var profilePhoto : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_account_page)

        profilePhoto = findViewById(R.id.profilePhoto)
        var editNameText = findViewById<EditText>(R.id.editNameText)
        var editBirthdateText = findViewById<EditText>(R.id.editBirthdateText)
        var editPhoneText = findViewById<EditText>(R.id.editPhoneText)
        var editEmailText = findViewById<EditText>(R.id.editEmailText)
        val saveProfileButton = findViewById<Button>(R.id.saveProfileButton)
        val profilePhotoButton = findViewById<ImageButton>(R.id.profilePhotoButton)

        profilePhotoButton.setOnClickListener {
            loadImageDialogue()
        }

        if (loadBitmapImage() != null)
            profilePhoto.setImageBitmap(loadBitmapImage())
        else
            profilePhoto.setImageResource(R.drawable.nameinputicon)

        //Save Profile Button
        saveProfileButton.setOnClickListener {
            // Convert Imageview to bitmap
            val profileDrawable = profilePhoto.drawable
            val profileBitmap = profileDrawable.toBitmap()
            ImageSaver(applicationContext).
                    setFileName("ProfilePhoto.png").
                    setDirectoryName("images").
                    save(profileBitmap) //Save Converted Bitmap to internal storage
            Toast.makeText(this@EditProfileActivity, "Profile Saved",
                Toast.LENGTH_LONG).show()
        }
    }

    //Ask for both Camera and Storage Permissions when "Take Photo" Option is selected
    private fun checkPermissionCamera() {
        Dexter.withContext(this)
            .withPermissions(android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                android.Manifest.permission.CAMERA).withListener(
            object  : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                    report?.let {
                        if (report.areAllPermissionsGranted()) {
                            camera() //Intent to camera
                        }
                    }
                }
                override fun onPermissionRationaleShouldBeShown(
                    p0: MutableList<PermissionRequest>?,
                    p1: PermissionToken?) {
                    displayDialogForPermission()
                }
            }
        ).onSameThread().check()
    }

    //Ask for Storage Permissions when "Select from Gallery" Option is selected
    private fun checkPermissionGallery() {
        Dexter.withContext(this).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                    gallery() //Intent to gallery
                }

                override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                    Toast.makeText(this@EditProfileActivity, "No permissions granted",
                    Toast.LENGTH_LONG).show()
                    displayDialogForPermission()
                }

                override fun onPermissionRationaleShouldBeShown(
                    p0: PermissionRequest?,
                    p1: PermissionToken?
                ) {
                    displayDialogForPermission()
                }

            }).onSameThread().check()
    }

    //Intent to Camera
    private fun camera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERAREQUESTCODE)
    }

    //Intent to gallery
    private fun gallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERYREQUESTCODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERAREQUESTCODE -> { //Request for Camera
                    val bitmap = data?.extras?.get("data") as Bitmap
                    profilePhoto.load(bitmap) {
                        crossfade(true)
                        crossfade(1000) //Fade
                        transformations(CircleCropTransformation()) //Circular Crop
                    }
                }
                GALLERYREQUESTCODE -> {
                    profilePhoto.load(data?.data) {
                        crossfade(true)
                        crossfade(1000) //Fade
                        transformations(CircleCropTransformation()) //Circular Cropt
                    }
                }
            }
        }
    }

    //Function for if No permissions are granted
    //And if user still tries to access Camera / Gallery
    // Dialogue box to bring user to app settings to change permissions
    private fun displayDialogForPermission() {
        AlertDialog.Builder(this)
            .setMessage("No permissions granted.")
            .setPositiveButton("Go to SETTINGS"){_,_->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                }catch (e : ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel"){dialog, _->
                dialog.dismiss()
            }.show()
    }

    // Dialogue Box to Display Options to choose between Camera or Gallery
    private fun loadImageDialogue() {
        val imageDialog = AlertDialog.Builder(this)
        imageDialog.setTitle("Take Photo or Choose from Gallery?")
        val dialogItem = arrayOf("Choose from Gallery", "Take Photo")
        imageDialog.setItems(dialogItem) { dialog, option ->
            when (option) {
                0 -> checkPermissionGallery()
                1 -> checkPermissionCamera()
            }
        }
        imageDialog.show()
    }

    //Load Bitmap based on Filename
    private fun loadBitmapImage(): Bitmap? {
        return ImageSaver(applicationContext).setFileName("ProfilePhoto.png")
            .setDirectoryName("images").load()
    }
}