package com.example.frontend

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.os.PersistableBundle
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

class LoginActivity : AppCompatActivity()  {

    val TAG : String = "Login"

    private var cancellationSignal: CancellationSignal? = null

    //Handles fingerprint authentication
    private var authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() =
            @RequiresApi(Build.VERSION_CODES.P)
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                    super.onAuthenticationError(errorCode, errString)
                    authMsg("Authentication error: $errString")
                }

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                    super.onAuthenticationSucceeded(result)
                    authMsg("Authentication successful")
                    startActivity(Intent(this@LoginActivity, HomePageMainActivity::class.java))
                }
            }
        set(value) {}

    //Check if user cancel the fingerprint authentication
    private fun getCancellationSignal():CancellationSignal{
        cancellationSignal = CancellationSignal()
        cancellationSignal?.setOnCancelListener {
            authMsg("Authentication cancelled")
        }
        return cancellationSignal as CancellationSignal
    }

    private fun checkBiometricSupport():Boolean{
        //Check if smartphone supports fingerprint
        val keyguardManager:KeyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
        if(!keyguardManager.isKeyguardSecure){
            authMsg("Fingerprint authentication not enabled")
            return false
        }
        // Check if fingerprint permisson is enabled
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED){
            authMsg("Fingerprint authentication permission is not enabled")
            return false
        }
        return if(packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            return true
        }
        else true
    }

    //Create toast with message
    private fun authMsg(message:String){
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        checkBiometricSupport()

        // findViewById for UI elements
        val usernameEditText = findViewById<EditText>(R.id.editTextUsernameLogin)
        val passwordEditText = findViewById<EditText>(R.id.editTextPasswordLogin)
        val recoverClickableText = findViewById<TextView>(R.id.textViewRecoverLogin)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val fingerprintLoginButton = findViewById<Button>(R.id.buttonFingerprintLogin)

        recoverClickableText.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            // TODO implement authentication logic
            // place holder authentication, to be replaced by actual implementation
            if (usernameEditText.text.toString() == "admin" &&
                passwordEditText.text.toString() == "password") {

                // TODO create intent to Home page
                val homeIntent = Intent(this, HomePageMainActivity::class.java)
                startActivity(homeIntent)
            }
            else {
                authMsg("Invalid Credentials")
            }
        }

        //Note: Have to manually add a fingerprint to the emulator
        //To emulate fingerprint
        //Open CMD at Appdata->Local->Android->platformtools
        //Command: adb -e emu finger touch <fingerprint id>
        //<fingerprint id> can be any value
        //Example usuage adb -e emu finger touch 123abc
        fingerprintLoginButton.setOnClickListener {
            //Create Fingerprint drawer on button click
            val biometricPrompt = BiometricPrompt.Builder(this)
                .setTitle("Fingerprint Login")
                .setSubtitle("Fingerprint Authentication is required")
                .setDescription("Please scan your fingerprint below")
                .setNegativeButton("Cancel", this.mainExecutor,DialogInterface.OnClickListener{dialog, which ->
                    authMsg("Authentication cancelled")
                }).build()

            biometricPrompt.authenticate(getCancellationSignal(),mainExecutor, authenticationCallback)
        }

    }
}