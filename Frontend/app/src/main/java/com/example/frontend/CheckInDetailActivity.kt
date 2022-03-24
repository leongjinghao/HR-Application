package com.example.frontend

import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.os.PersistableBundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.frontend.databinding.ActivityCheckInSelfieBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class CheckInDetailActivity : AppCompatActivity(), SurfaceHolder.Callback, Camera.PictureCallback {

    private lateinit var binding: ActivityCheckInSelfieBinding

    private var surfaceHolder: SurfaceHolder? = null
    private var camera: Camera? = null
    private var confirmFlag = false
    private lateinit var tempSelfieByte: ByteArray


    private val neededPermissions = arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckInSelfieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val checkInResetButton = findViewById<Button>(R.id.buttonCheckInReset)

        checkInResetButton.setOnClickListener {
            // Call function for resetting camera view
            resetCamera()

            // Reset button text to "capture" mode and confirm flag back to false
            binding.buttonCheckInConfirm.text = "Capture"
            confirmFlag = false
        }

        // Check permissions required
        val permission = checkPermission()
        // If all permissions granted display camera view on surface holder
        if (permission) {
            setupSurfaceHolder()
        }
    }

    // Function to check permissions required
    private fun checkPermission(): Boolean {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            val permissionsNotGranted = ArrayList<String>()
            for (permission in neededPermissions) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        permission
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    permissionsNotGranted.add(permission)
                }
            }
            if (permissionsNotGranted.size > 0) {
                var shouldShowAlert = false
                for (permission in permissionsNotGranted) {
                    shouldShowAlert =
                        ActivityCompat.shouldShowRequestPermissionRationale(this, permission)
                }

                val arr = arrayOfNulls<String>(permissionsNotGranted.size)
                val permissions = permissionsNotGranted.toArray(arr)
                if (shouldShowAlert) {
                    showPermissionAlert(permissions)
                } else {
                    requestPermissions(permissions)
                }
                return false
            }
        }
        return true
    }

    // Function to display dialog box for requesting permissions
    private fun showPermissionAlert(permissions: Array<String?>) {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setCancelable(true)
        alertBuilder.setTitle(R.string.permission_required)
        alertBuilder.setMessage(R.string.permission_message)
        alertBuilder.setPositiveButton(R.string.yes) { _, _ -> requestPermissions(permissions) }
        val alert = alertBuilder.create()
        alert.show()
    }

    // Function to request all permissions from user
    private fun requestPermissions(permissions: Array<String?>) {
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE)
    }

    // Function to check if all permissions are granted by user
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {
                for (result in grantResults) {
                    if (result == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(
                            this,
                            R.string.permission_warning,
                            Toast.LENGTH_LONG
                        ).show()
                        binding.showPermissionMsg.visibility = View.VISIBLE
                        return
                    }
                }

                setupSurfaceHolder()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // Setting up the view for surface holder
    private fun setupSurfaceHolder() {
        binding.buttonCheckInConfirm.visibility = View.VISIBLE
        binding.surfaceViewCamera.visibility = View.VISIBLE

        surfaceHolder = binding.surfaceViewCamera.holder
        binding.surfaceViewCamera.holder.addCallback(this)
        setBtnClick()
    }

    // Set on click listener to "capture" or "confirm" button
    private fun setBtnClick() {
        binding.buttonCheckInConfirm.setOnClickListener { captureImage() }
    }

    /* Perform actions depending on the current mode (capture or confirm)
        Capture mode: store the bytes of captured image in camera view in a temporary variable
        Confirm mode: save/send the bytes of image store in the temporary variable
     */
    private fun captureImage() {
        // If in "capture" mode, not "confirm"
        if (!confirmFlag) {
            if (camera != null) {
                camera!!.takePicture(null, null, this)
                confirmFlag = true
            }
        }
        // Else in "confirm" mode
        else {
            saveImage(tempSelfieByte)
            Toast.makeText(this, "to send the selfie here!", Toast.LENGTH_LONG).show()
            confirmFlag = false

            // TODO: send the selfie to DB and record check in details

            // Go back to previous page on successful check in process
            finish()
        }
    }

    // Start the camera view on surface view created
    override fun surfaceCreated(surfaceHolder: SurfaceHolder) {
        startCamera()
    }

    // Function to start the camera view
    private fun startCamera() {
        camera = Camera.open()
        camera!!.setDisplayOrientation(90)
        try {
            camera!!.setPreviewDisplay(surfaceHolder)
            camera!!.startPreview()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    // On surface view changed
    override fun surfaceChanged(surfaceHolder: SurfaceHolder, i: Int, i1: Int, i2: Int) {
        resetCamera()
    }

    // Function to reset the camera view, i.e. on pause in "confirm" mode
    private fun resetCamera() {
        if (surfaceHolder!!.surface == null) {
            // Return if preview surface does not exist
            return
        }

        // Stop if preview surface is already running.
        camera!!.stopPreview()
        try {
            // Set preview display
            camera!!.setPreviewDisplay(surfaceHolder)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Start the camera preview
        camera!!.startPreview()
    }

    // Release the camera view on surface view destruction
    override fun surfaceDestroyed(surfaceHolder: SurfaceHolder) {
        releaseCamera()
    }

    // Function for releasing the camera view
    private fun releaseCamera() {
        camera!!.stopPreview()
        camera!!.release()
        camera = null
    }

    /* On picture taken in "capture" mode,
        - Store the bytes of image captured in temporary variable
        - Change the button text to "confirm" mode
        - Pause the camera view
     */
    override fun onPictureTaken(bytes: ByteArray, camera: Camera) {
        // Store bytes of picture in temporary variable
        tempSelfieByte = bytes

        // Set text of button to "confirm" mode
        binding.buttonCheckInConfirm.text = "Confirm"

        // Stop the camera view
        camera!!.stopPreview()
    }

    // Function to write out the image from a byte array
    private fun saveImage(bytes: ByteArray) {
        val outStream: FileOutputStream
        try {
            val fileName = "CheckInSelfie_" + System.currentTimeMillis() + ".jpg"
            val file = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                fileName
            )
            outStream = FileOutputStream(file)
            outStream.write(bytes)
            outStream.close()
            Toast.makeText(this, "Picture Saved: $fileName", Toast.LENGTH_LONG).show()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        const val REQUEST_CODE = 100
    }
}