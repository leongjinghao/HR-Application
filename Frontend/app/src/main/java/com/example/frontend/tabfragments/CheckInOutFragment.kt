package com.example.frontend.tabfragments

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.frontend.*
import com.example.frontend.Activities.CheckInDetailActivity
import com.example.frontend.CheckInOutHistory.History
import com.example.frontend.CheckInOutHistory.HistoryViewModel
import com.example.frontend.CheckInOutHistory.HistoryViewModelFactory
import com.example.frontend.Utilities.HRApplication
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class CheckInOutFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // create the ViewModel
    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory((activity?.applicationContext as HRApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_in_out, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val checkInLocationTextView = view.findViewById<TextView>(R.id.textViewCurrentLocation)
        val checkInButton = view.findViewById<Button>(R.id.buttonCheckInOut)
        val checkInButtonText = view.findViewById<TextView>(R.id.textViewCheckInOut)
        val checkInButtonTimeText = view.findViewById<TextView>(R.id.textViewCheckInOutTime)
        val dateTextView = view.findViewById<TextView>(R.id.textViewCheckInOutDate)
        val shiftTextView = view.findViewById<TextView>(R.id.textViewCheckInOutWorkShift)
        val locationTextView = view.findViewById<TextView>(R.id.textViewCheckInOutLocation)

        // Handle location permissions
        if ( Build.VERSION.SDK_INT >= 23 &&
            ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission( requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }

        // Check for permission to use location
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                } else -> {
                // No location access granted.
            }
            }
        }
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

        // Configure location details on location text view
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    checkInLocationTextView.text = "Longitute: " + location.longitude.toString() +
                            "\nLatitute: " +  location.latitude.toString()
                }
            }

        // Configure current time on button
        var df = SimpleDateFormat("HH:mm a")
        var formattedTime = df.format(Calendar.getInstance().time)
        checkInButtonTimeText.text = formattedTime

        // Configure button text according to the check in and out status
        if (historyViewModel.checkInOutStatus == "Clock out") {
            checkInButtonText.text = "Check In"
        }
        else {
            checkInButtonText.text = "Check Out"

            // TODO: change colour of drawable button

        }

        // Configure work schedule details
        df = SimpleDateFormat("dd-MM-yyyy")
        dateTextView.text = df.format(Calendar.getInstance().time)
        shiftTextView.text = "9am to 6pm"
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    locationTextView.text = "Long: " + location.longitude.toString() +
                            " Lat: " +  location.latitude.toString()
                }
            }


        checkInButton.setOnClickListener{
            // if check in and out status is "Clock out", display check in button/option
            if (historyViewModel.checkInOutStatus == "Clock out") {
                val intent = Intent(activity, CheckInDetailActivity::class.java)
                activity?.startActivity(intent)

                // Go back to previous page on successful check in process
                activity?.onBackPressed()
            }
            else {
                // TODO: additional check out logic on aws db

                // Insert check out record on room DB
                historyViewModel.insert(
                    History(
                    0,
                    LocalDate.now().toString(),
                    LocalDate.now().dayOfWeek.name,
                    "Clock out",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                )
                )

                // Go back to previous page on successful check out process
                activity?.onBackPressed()
            }
        }
    }
}