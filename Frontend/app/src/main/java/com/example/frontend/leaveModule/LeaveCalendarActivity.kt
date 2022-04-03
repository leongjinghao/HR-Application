package com.example.frontend.leaveModule

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginRight
import androidx.core.view.setPadding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.frontend.databinding.ActivityCalendarBinding
import com.example.frontend.retroAPI.api.model.leaveInformationModel
import com.example.frontend.retroAPI.api.model.userInformationModel
import com.example.frontend.retroAPI.api.repository.Repository
import com.example.frontend.retroAPI.api.viewModel.apiViewModel
import com.example.frontend.retroAPI.api.viewModel.apiViewModelFactory

class LeaveCalendarActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityCalendarBinding
    private lateinit var apiCall : apiViewModel
    private lateinit var userInfoData : userInformationModel
    private lateinit var leaveInfoData : leaveInformationModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userInfoData = userInformationModel(null)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val repository = Repository()
        val apiModelFactory = apiViewModelFactory(repository)
        apiCall = ViewModelProvider(this,apiModelFactory).get(apiViewModel::class.java)

        apiCall.getUserInformation("Ali456")
        apiCall.userInformationRes.observe(this, Observer { response ->
            userInfoData = response
            setBarGraph();
        })
        apiCall.getUserLeaves("Ali456")
        apiCall.leaveInformationRes.observe(this, Observer { response ->
            leaveInfoData = response
        })
    }

    private fun setBarGraph() {

        var arrayOfPosition =
            userInfoData.Items?.get(0)?.AL?.S?.length?.let { checkPosition(it.toInt()) }
        var availableAL =
            userInfoData.Items?.get(0)?.AL?.S.toString().substring(0, arrayOfPosition!![0])
                .toFloat()
        var usedAL = userInfoData.Items?.get(0)?.AL?.S.toString()
            .substring(arrayOfPosition!![1], arrayOfPosition!![2]).toFloat() - availableAL

        arrayOfPosition =
            userInfoData.Items?.get(0)?.MC?.S?.length?.let { checkPosition(it.toInt()) }
        var availableMC =
            userInfoData.Items?.get(0)?.MC?.S.toString().substring(0, arrayOfPosition!![0])
                .toFloat()
        var usedMC = userInfoData.Items?.get(0)?.MC?.S.toString()
            .substring(arrayOfPosition!![1], arrayOfPosition!![2]).toFloat() - availableMC

        arrayOfPosition =
            userInfoData.Items?.get(0)?.OIL?.S?.length?.let { checkPosition(it.toInt()) }
        var availableOIL =
            userInfoData.Items?.get(0)?.OIL?.S.toString().substring(0, arrayOfPosition!![0])
                .toFloat()
        var usedOIL = userInfoData.Items?.get(0)?.OIL?.S.toString()
            .substring(arrayOfPosition!![1], arrayOfPosition!![2]).toFloat() - availableOIL

        binding.leaveALTextView.text =
            "Annual Leave : " + (availableAL.toInt() + usedAL.toInt()).toString() + " Total"
        if (availableAL.toInt() != 0) {
            binding.leaveALIndicatorUnused.text = availableAL.toInt().toString() + " unused"
        }
        if (usedAL.toInt() != 0) {
            binding.leaveALIndicatorUsed.text = usedAL.toInt().toString() + " used"
        }
        var size =   (binding.leaveALIndicatorUnused.width.toInt() / (availableAL.toInt() + usedAL.toInt())) * usedAL.toInt()
        if (size < 200 && size != 0) {
            size = 200
        }
        binding.leaveALIndicatorUsed.width = size

        if(size >= 300 && size <=400) {
            binding.leaveALIndicatorUnused.setPadding(0,15,100,15)
        }
        else if(size >=400 && size <= 500){
            binding.leaveALIndicatorUnused.setPadding(0,15,300,15)
        }
        else if(size >=500 && size <= 600){
            binding.leaveALIndicatorUnused.setPadding(0,15,500,15)
        }
        else if(size >=600 && size <= 700){
            binding.leaveALIndicatorUnused.setPadding(0,15,700,15)
        }

        binding.leaveOILTextView.text =
            "Off-In-Liue Leave : " + (availableOIL.toInt() + usedOIL.toInt()).toString() + " Total"
        if (availableOIL.toInt() != 0) {
            binding.leaveOILIndicatorUnused.text = availableOIL.toInt().toString() + " unused"
        }
        if (usedOIL.toInt() != 0) {
            binding.leaveOILIndicatorUsed.text = usedOIL.toInt().toString() + " used"
        }
        size = (binding.leaveOILIndicatorUnused.width.toInt() / (availableOIL.toInt() + usedOIL.toInt())) * usedOIL.toInt()
        if (size < 200 && size != 0) {
            size = 200
        }
        binding.leaveOILIndicatorUsed.width = size

        if(size >= 300 && size <=400) {
            binding.leaveOILIndicatorUnused.setPadding(0,15,100,15)
        }
        else if(size >=400 && size <= 500){
            binding.leaveOILIndicatorUnused.setPadding(0,15,300,15)
        }
        else if(size >=500 && size <= 600){
            binding.leaveOILIndicatorUnused.setPadding(0,15,500,15)
        }
        else if(size >=600 && size <= 700){
            binding.leaveOILIndicatorUnused.setPadding(0,15,700,15)
        }


        binding.leaveMLTextView.text =
            "Medical Leave : " + (availableMC.toInt() + usedMC.toInt()).toString() + " Total"
        if (availableMC.toInt() != 0) {
            binding.leaveMLIndicatorUnused.text = availableMC.toInt().toString() + " unused"
        }
        if (usedMC.toInt() != 0) {
            binding.leaveMLIndicatorUsed.text = usedMC.toInt().toString() + " used"
        }
        size = (binding.leaveMLIndicatorUnused.width.toInt() / (availableMC.toInt() + usedMC.toInt())) * usedMC.toInt()
        if (size < 200 && size != 0) {
            size = 200
        }
        binding.leaveMLIndicatorUsed.width = size

        if(size >= 300 && size <=400) {
            binding.leaveMLIndicatorUnused.setPadding(0,15,100,15)
        }
        else if(size >=400 && size <= 500){
            binding.leaveMLIndicatorUnused.setPadding(0,15,300,15)
        }
        else if(size >=500 && size <= 600){
            binding.leaveMLIndicatorUnused.setPadding(0,15,500,15)
        }
        else if(size >=600 && size <= 700){
            binding.leaveMLIndicatorUnused.setPadding(0,15,700,15)
        }
    }

    private fun checkPosition(size : Int): Array<Int> {
        var tempData = arrayOf(0,0,0)
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