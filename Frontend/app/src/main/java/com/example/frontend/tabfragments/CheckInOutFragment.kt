package com.example.frontend.tabfragments

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.frontend.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


class CheckInOutFragment : Fragment() {

    // create the ViewModel
    private val historyViewModel: HistoryViewModel by viewModels {
        HistoryViewModelFactory((activity?.applicationContext as HRApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        view?.let { onViewCreated(it, savedInstanceState = null) }
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
        val checkInButton = view.findViewById<Button>(R.id.buttonCheckInOut)
        val checkInButtonText = view.findViewById<TextView>(R.id.textViewCheckInOut)
        val checkInButtonTimeText = view.findViewById<TextView>(R.id.textViewCheckInOutTime)

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

        checkInButton.setOnClickListener{
            // if check in and out status is "Clock out", display check in button/option
            if (historyViewModel.checkInOutStatus == "Clock out") {
                val intent = Intent(activity, CheckInDetailActivity::class.java)
                activity?.startActivity(intent)
            }
            else {
                // TODO: additional check out logic on aws db

                // Insert check out record on room DB
                historyViewModel.insert(History(
                    0,
                    LocalDate.now().toString(),
                    LocalDate.now().dayOfWeek.name,
                    "Clock out",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
                ))

                // Go back to previous page on successful check in process
                activity?.onBackPressed();
            }
        }
    }
}