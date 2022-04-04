package com.example.frontend.Activities.leaveModule

import android.R
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.frontend.databinding.ActivityLeavesAddBinding
import com.example.frontend.login.UserIdRepo
import com.example.frontend.retroAPI.api.repository.Repository
import com.example.frontend.retroAPI.api.viewModel.apiViewModel
import com.example.frontend.retroAPI.api.viewModel.apiViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule


class LeaveAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLeavesAddBinding
    private lateinit var apiCall: apiViewModel
    private var toggle: Int = 0
    private var userId = ""
    val tempName: ArrayList<String> = ArrayList()
    val tempUserId: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeavesAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository()
        val apiModelFactory = apiViewModelFactory(repository)
        apiCall = ViewModelProvider(this, apiModelFactory).get(apiViewModel::class.java)

        val arrayList: ArrayList<String> = ArrayList()
        arrayList.add("Annual Leave")
        arrayList.add("Medical Leave")
        arrayList.add("Off In Lieu")
        val arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, arrayList)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        binding.leaveTypeSpinner.adapter = arrayAdapter

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonht ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonht)
            updateLabel(myCalendar)
        }

        binding.startDateCalendarPicker.setOnClickListener {
            toggle = 1
            DatePickerDialog(
                this, datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.endDateCalendarPicker.setOnClickListener {
            toggle = 2
            DatePickerDialog(
                this, datePicker,
                myCalendar.get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        lifecycleScope.launch {
            UserIdRepo.getInstance(this@LeaveAddActivity).userPreferencesFlow.collect { settings ->
                userId = settings.id
            }
        }
        apiCall.retrieveApprovers(userId)
        apiCall.userInformationRes.observe(this, Observer { response ->
            var count = 0
            while (count < response.Items?.size!!) {
                tempName.add(response.Items[count].EmployeeName.S)
                tempUserId.add(response.Items[count].PK.S.substring(5))
                count++
            }

            val arrayAdapter = ArrayAdapter(this, R.layout.simple_spinner_item, tempName)
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
            binding.approverSpinner.adapter = arrayAdapter
        })

        binding.submitButton.setOnClickListener {

            var errorMsg = ""

            var startDate = binding.startDateTextView.text.toString()
            var endDate = binding.endDateTextView.text.toString()

            if (startDate == "Start Date") {
                errorMsg += "Please chose Start Date \n"
            }

            if (endDate == "End Date") {
                errorMsg += "Please chose End Date \n"
            }

            if (binding.remarks.text.toString() == "") {
                errorMsg += "Please fill in the remarks \n"
            }

            if (startDate != "Start Date" && endDate != "End Date") {
                val dateCompare = startDate.compareTo(endDate)
                if (dateCompare > 0) {
                    errorMsg += startDate + " cannot be more than " + endDate
                }
            }
            if (errorMsg == "") {

                var SK =
                    startDate.substring(0, 2) + startDate.substring(3, 5) + startDate.substring(
                        6,
                        10
                    ) +
                            "/" + endDate.substring(0, 2) + endDate.substring(
                        3,
                        5
                    ) + endDate.substring(6, 10)

                var leaveType = binding.leaveTypeSpinner.selectedItem.toString()

                if (leaveType == "Annual Leave") {
                    leaveType = "AL"
                } else if (leaveType == "Medical Leave") {
                    leaveType = "ML"
                } else if (leaveType == "Off In Lieu") {
                    leaveType = "OIL"
                }
                apiCall.createUserLeave(
                    userId,
                    SK,
                    leaveType,
                    tempUserId[binding.approverSpinner.selectedItemPosition],
                    binding.remarks.text.toString()
                )
                apiCall.returnRespondModelRes.observe(this, Observer { response ->
                    Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
                    if (response.result == true) {
                        val intent = Intent(this, LeaveSummaryActivity::class.java)
                        Timer().schedule(1500) {
                            startActivity(intent)
                        }
                    }
                })
            } else {
                errorMsg = errorMsg.substring(0, errorMsg.length - 2)
                Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        if (toggle == 1) {
            binding.startDateTextView.text = sdf.format(myCalendar.time)
        } else if (toggle == 2) {
            binding.endDateTextView.text = sdf.format(myCalendar.time)
        }
    }

}