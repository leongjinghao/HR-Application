package com.example.frontend.leaveModule

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.databinding.ActivityLeavesSummaryBinding
import com.example.frontend.leaveModule.utilities.LeaveSummaryRecycler
import com.example.frontend.retroAPI.api.model.leaveInformationModel
import com.example.frontend.retroAPI.api.model.userInformationModel
import com.example.frontend.retroAPI.api.repository.Repository
import com.example.frontend.retroAPI.api.viewModel.apiViewModel
import com.example.frontend.retroAPI.api.viewModel.apiViewModelFactory
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry

class LeaveSummaryActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityLeavesSummaryBinding
    private lateinit var apiCall : apiViewModel
    private lateinit var userInfoData : userInformationModel
    private lateinit var leaveInfoData : leaveInformationModel
    private var leaveAdapter : RecyclerView.Adapter<LeaveSummaryRecycler.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeavesSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userInfoData = userInformationModel(null)

        val repository = Repository()
        val apiModelFactory = apiViewModelFactory(repository)
        apiCall = ViewModelProvider(this,apiModelFactory).get(apiViewModel::class.java)

        apiCall.getUserInformation("Ali456")
        apiCall.userInformationRes.observe(this, Observer { response ->
            userInfoData = response
            initPieChart()
            setDataToPieChart()
        })

        apiCall.getUserLeaves("Ali456")
        apiCall.leaveInformationRes.observe(this, Observer { response ->
            leaveInfoData = response
            leaveAdapter = LeaveSummaryRecycler(leaveInfoData,this,this,apiCall,intent)
            binding.recyclerViewLeaveSummary.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewLeaveSummary.adapter = leaveAdapter
        })

        binding.calenderButton.setOnClickListener{
            val intent = Intent(this, LeaveCalendarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initPieChart() {
            binding.pieChart.description.text = ""
            //Enable the char to be rotate
            binding.pieChart.setTouchEnabled(true)
            binding.pieChart.isRotationEnabled = true

            //Remove Labels from the Pie
            binding.pieChart.setDrawEntryLabels(false)

            //Legend
            binding.pieChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL
            binding.pieChart.legend.isWordWrapEnabled = true
            binding.pieChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
            binding.pieChart.legend.textSize = 15f
            binding.pieChart.legend.form = Legend.LegendForm.CIRCLE
            binding.pieChart.legend.maxSizePercent = 0.7f
            binding.pieChart.legend.formToTextSpace= 10f
            //Animation
            binding.pieChart.animateY(1400, Easing.EaseInOutQuad)
            //create hole in center
            binding.pieChart.holeRadius = 58f
            binding.pieChart.transparentCircleRadius = 58f
            binding.pieChart.isDrawHoleEnabled = true
            binding.pieChart.setHoleColor(Color.WHITE)
        }

    private fun setDataToPieChart() {

            var arrayOfPosition = userInfoData.Items?.get(0)?.AL?.S?.length?.let { checkPosition(it.toInt()) }
            var availableAL = userInfoData.Items?.get(0)?.AL?.S.toString().substring(0, arrayOfPosition!![0]).toFloat()
            var usedAL = userInfoData.Items?.get(0)?.AL?.S.toString().substring(arrayOfPosition!![1],arrayOfPosition!![2]).toFloat() - availableAL

            arrayOfPosition = userInfoData.Items?.get(0)?.MC?.S?.length?.let { checkPosition(it.toInt()) }
            var availableMC = userInfoData.Items?.get(0)?.MC?.S.toString().substring(0, arrayOfPosition!![0]).toFloat()
            var usedMC = userInfoData.Items?.get(0)?.MC?.S.toString().substring(arrayOfPosition!![1],arrayOfPosition!![2]).toFloat() - availableMC

            arrayOfPosition = userInfoData.Items?.get(0)?.OIL?.S?.length?.let { checkPosition(it.toInt()) }
            var availableOIL = userInfoData.Items?.get(0)?.OIL?.S.toString().substring(0, arrayOfPosition!![0]).toFloat()
            var usedOIL = userInfoData.Items?.get(0)?.OIL?.S.toString().substring(arrayOfPosition!![1],arrayOfPosition!![2]).toFloat() - availableOIL

            val dataEntries = ArrayList<PieEntry>()
            val colors: ArrayList<Int> = ArrayList()
            if(availableAL != 0f) {
                dataEntries.add(PieEntry(availableAL, "Available AL " + availableAL))
                colors.add(Color.parseColor("#4DD0E1"))
            }
            if(usedAL != 0f) {
                dataEntries.add(PieEntry(usedAL, "Unused AL " + usedAL))
                colors.add(Color.parseColor("#21AFC1"))
            }
            if(availableMC != 0f) {
                dataEntries.add(PieEntry(availableMC, "Available MC " + availableMC))
                colors.add(Color.parseColor("#FFF176"))
            }
            if(usedMC != 0f) {
                dataEntries.add(PieEntry(usedMC, "Unused MC " + usedMC))
                colors.add(Color.parseColor("#FFE500"))
            }
            if(availableOIL != 0f) {
                dataEntries.add(PieEntry(availableOIL, "Available OIL " + availableOIL))
                colors.add(Color.parseColor("#FF8A65"))
            }
            if(usedOIL!= 0f) {
                dataEntries.add(PieEntry(usedOIL, "Unused OIL " + usedOIL))
                colors.add(Color.parseColor("#FF5019"))
            }
            val dataSet = PieDataSet(dataEntries, "")
            val data = PieData(dataSet)
            data.setDrawValues(false)
            dataSet.colors = colors
            binding.pieChart.data = data
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
