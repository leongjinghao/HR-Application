package com.example.frontend.Activities.leaveModule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.frontend.databinding.ActivityCalendarBinding
import com.example.frontend.login.UserIdRepo
import com.example.frontend.retroAPI.api.model.userInformationModel
import com.example.frontend.retroAPI.api.repository.Repository
import com.example.frontend.retroAPI.api.viewModel.apiViewModel
import com.example.frontend.retroAPI.api.viewModel.apiViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;

class LeaveCalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var apiCall: apiViewModel
    private lateinit var userInfoData: userInformationModel
    var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Retrieve User Id
        lifecycleScope.launch {
            UserIdRepo.getInstance(this@LeaveCalendarActivity).userPreferencesFlow.collect { settings ->
                userId = settings.id
            }
        }
    try {
        userInfoData = userInformationModel(null)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository()
        val apiModelFactory = apiViewModelFactory(repository)
        apiCall = ViewModelProvider(this, apiModelFactory).get(apiViewModel::class.java)

        //Retrieve User Information and display on the bar graph
        apiCall.getUserInformation(userId)
        apiCall.userInformationRes.observe(this, Observer { response ->
            userInfoData = response
            setBarGraph();
        })
        }
    catch (err:Exception){}
    }

    private fun setBarGraph() {

        //Extract used and unused AL from the data
        var arrayOfPosition =
            userInfoData.Items?.get(0)?.AL?.S?.length?.let { checkPosition(it.toInt()) }
        var availableAL =
            userInfoData.Items?.get(0)?.AL?.S.toString().substring(0, arrayOfPosition!![0])
                .toFloat()
        var usedAL = userInfoData.Items?.get(0)?.AL?.S.toString()
            .substring(arrayOfPosition!![1], arrayOfPosition!![2]).toFloat() - availableAL

        //Extract used and unused MC from the data
        arrayOfPosition =
            userInfoData.Items?.get(0)?.MC?.S?.length?.let { checkPosition(it.toInt()) }
        var availableMC =
            userInfoData.Items?.get(0)?.MC?.S.toString().substring(0, arrayOfPosition!![0])
                .toFloat()
        var usedMC = userInfoData.Items?.get(0)?.MC?.S.toString()
            .substring(arrayOfPosition!![1], arrayOfPosition!![2]).toFloat() - availableMC

        //Extract used and unused OIL from the data
        arrayOfPosition =
            userInfoData.Items?.get(0)?.OIL?.S?.length?.let { checkPosition(it.toInt()) }
        var availableOIL =
            userInfoData.Items?.get(0)?.OIL?.S.toString().substring(0, arrayOfPosition!![0])
                .toFloat()
        var usedOIL = userInfoData.Items?.get(0)?.OIL?.S.toString()
            .substring(arrayOfPosition!![1], arrayOfPosition!![2]).toFloat() - availableOIL

        //Set AL text at the bar graph
        binding.leaveALTextView.text =
            "Annual Leave : " + (availableAL.toInt() + usedAL.toInt()).toString() + " Total"
        if (availableAL.toInt() != 0) {
            binding.leaveALIndicatorUnused.text = availableAL.toInt().toString() + " unused"
        }
        if (usedAL.toInt() != 0) {
            binding.leaveALIndicatorUsed.text = usedAL.toInt().toString() + " used"
        }
        var size =
            (binding.leaveALIndicatorUnused.width.toInt() / (availableAL.toInt() + usedAL.toInt())) * usedAL.toInt()
        if (size < 200 && size != 0) {
            size = 200
        }
        binding.leaveALIndicatorUsed.width = size

        if (size >= 300 && size <= 400) {
            binding.leaveALIndicatorUnused.setPadding(0, 15, 100, 15)
        } else if (size >= 400 && size <= 500) {
            binding.leaveALIndicatorUnused.setPadding(0, 15, 300, 15)
        } else if (size >= 500 && size <= 600) {
            binding.leaveALIndicatorUnused.setPadding(0, 15, 500, 15)
        } else if (size >= 600 && size <= 700) {
            binding.leaveALIndicatorUnused.setPadding(0, 15, 700, 15)
        }

        //Set OIL text at the bar graph
        binding.leaveOILTextView.text =
            "Off-In-Liue Leave : " + (availableOIL.toInt() + usedOIL.toInt()).toString() + " Total"
        if (availableOIL.toInt() != 0) {
            binding.leaveOILIndicatorUnused.text = availableOIL.toInt().toString() + " unused"
        }
        if (usedOIL.toInt() != 0) {
            binding.leaveOILIndicatorUsed.text = usedOIL.toInt().toString() + " used"
        }
        size =
            (binding.leaveOILIndicatorUnused.width.toInt() / (availableOIL.toInt() + usedOIL.toInt())) * usedOIL.toInt()
        if (size < 200 && size != 0) {
            size = 200
        }
        binding.leaveOILIndicatorUsed.width = size

        if (size >= 300 && size <= 400) {
            binding.leaveOILIndicatorUnused.setPadding(0, 15, 100, 15)
        } else if (size >= 400 && size <= 500) {
            binding.leaveOILIndicatorUnused.setPadding(0, 15, 300, 15)
        } else if (size >= 500 && size <= 600) {
            binding.leaveOILIndicatorUnused.setPadding(0, 15, 500, 15)
        } else if (size >= 600 && size <= 700) {
            binding.leaveOILIndicatorUnused.setPadding(0, 15, 700, 15)
        }

        //Set ML text at the bar graph
        binding.leaveMLTextView.text =
            "Medical Leave : " + (availableMC.toInt() + usedMC.toInt()).toString() + " Total"
        if (availableMC.toInt() != 0) {
            binding.leaveMLIndicatorUnused.text = availableMC.toInt().toString() + " unused"
        }
        if (usedMC.toInt() != 0) {
            binding.leaveMLIndicatorUsed.text = usedMC.toInt().toString() + " used"
        }
        size =
            (binding.leaveMLIndicatorUnused.width.toInt() / (availableMC.toInt() + usedMC.toInt())) * usedMC.toInt()
        if (size < 200 && size != 0) {
            size = 200
        }
        binding.leaveMLIndicatorUsed.width = size

        if (size >= 300 && size <= 400) {
            binding.leaveMLIndicatorUnused.setPadding(0, 15, 100, 15)
        } else if (size >= 400 && size <= 500) {
            binding.leaveMLIndicatorUnused.setPadding(0, 15, 300, 15)
        } else if (size >= 500 && size <= 600) {
            binding.leaveMLIndicatorUnused.setPadding(0, 15, 500, 15)
        } else if (size >= 600 && size <= 700) {
            binding.leaveMLIndicatorUnused.setPadding(0, 15, 700, 15)
        }
    }

    // Check the position of Sk date retrieve from the database
    // Combined with substring to retrieve the start date and end date
    // E.G LEAVE#02022022/03022022 to 02022022 and 03022022
    private fun checkPosition(size: Int): Array<Int> {
        var tempData = arrayOf(0, 0, 0)
        if (size == 3) {
            tempData = arrayOf(1, 2, 3)
        } else if (size == 4) {
            tempData = arrayOf(1, 2, 4)
        } else if (size == 5) {
            tempData = arrayOf(2, 3, 5)
        }
        return tempData
    }

}