package com.example.frontend.Activities

import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.frontend.R
import com.example.frontend.login.UserIdRepo
import com.example.frontend.retroAPI.api.repository.Repository
import com.example.frontend.retroAPI.api.viewModel.apiViewModel
import com.example.frontend.retroAPI.api.viewModel.apiViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import java.util.*
import kotlin.concurrent.schedule

class LoginActivity : AppCompatActivity()  {

    val TAG : String = "Login"
    var userId : String = ""
    private lateinit var apiCall : apiViewModel

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
                    if(userId == ""){
                        authMsg("Use Username and Password for first login")
                    }else{
                        authMsg("Authentication successful")
                        startActivity(Intent(this@LoginActivity, HomePageMainActivity::class.java))
                    }

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
        toast.show()
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val repository = Repository()
        val apiModelFactory = apiViewModelFactory(repository)
        apiCall = ViewModelProvider(this,apiModelFactory).get(apiViewModel::class.java)

        checkBiometricSupport()
        lifecycleScope.launch {
            UserIdRepo.getInstance(context = this@LoginActivity).userPreferencesFlow.collect { settings -> settings.id
                if(settings.id != ""){
                    userId = settings.id
                }
            }
        }
        // findViewById for UI elements
        val userEmailEditText = findViewById<EditText>(R.id.editTextUserEmailLogin)
        val passwordEditText = findViewById<EditText>(R.id.editTextPasswordLogin)
        val recoverClickableText = findViewById<TextView>(R.id.textViewRecoverLogin)
        val loginButton = findViewById<Button>(R.id.buttonLogin)
        val fingerprintLoginButton = findViewById<Button>(R.id.buttonFingerprintLogin)
        var authenticateStatus: Boolean = false
        var userId: String = ""

        recoverClickableText.setOnClickListener {
            val intent = Intent(this, ForgetPasswordActivity::class.java)
            startActivity(intent)
        }

        // user authentication API call
        loginButton.setOnClickListener {
            apiCall.authenticateUserLogin(userEmailEditText.text.toString(), passwordEditText.text.toString())
            apiCall.authenticateUserLoginRes.observe(this, Observer  { response ->
                authenticateStatus = response.Result
                userId = response.UserId
            })

            // check if authentication successful
            if (authenticateStatus) {
                // store userId in preference store
                lifecycleScope.launch {
                    UserIdRepo.getInstance(context = this@LoginActivity)
                        .update(userId)
                }

                // clear error message if visible
                userEmailEditText.error = null
                passwordEditText.error = null

                // reset authenticate status flag
                authenticateStatus = false

                // start intent to home page
                val homeIntent = Intent(this@LoginActivity, HomePageMainActivity::class.java)
                startActivity(homeIntent)
            } else {
                // display error message to signify invalid credentials entered
                userEmailEditText.error = "Invalid Credentials!"
                passwordEditText.error = "Invalid Credentials!"
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