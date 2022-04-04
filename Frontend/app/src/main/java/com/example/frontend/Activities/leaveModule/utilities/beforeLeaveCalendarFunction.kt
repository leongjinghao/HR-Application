package com.example.frontend.Activities.leaveModule.utilities

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.lifecycle.Observer
import com.example.frontend.Activities.leaveModule.LeaveCalendarActivity
import com.example.frontend.login.UserIdRepo
import com.example.frontend.retroAPI.api.model.leaveInformationModel
import com.example.frontend.retroAPI.api.repository.Repository
import com.example.frontend.retroAPI.api.viewModel.apiViewModel
import com.example.frontend.retroAPI.api.viewModel.apiViewModelFactory
import com.skyhope.eventcalenderlibrary.helper.TimeUtil
import com.skyhope.eventcalenderlibrary.model.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

@RequiresApi(Build.VERSION_CODES.P)
fun beforeLeaveCalendar (
    context: Context,
    owner: ViewModelStoreOwner,
    lifeCycleOwner : LifecycleOwner,
    userId : String,
) {
    var leaveInfoData: leaveInformationModel
    var apiCall: apiViewModel

    val repository = Repository()
    val apiModelFactory = apiViewModelFactory(repository)
    apiCall = ViewModelProvider(owner, apiModelFactory).get(apiViewModel::class.java)

    apiCall.getUserLeavesCalendar(userId, "Calendar")
    apiCall.leaveInformationCalendarRes.observe(lifeCycleOwner, Observer { response ->
        leaveInfoData = response
        leaveInfoData.Items?.size.toString()
        var i = 0;
        val calendar: Calendar = Calendar.getInstance()
        while (i < leaveInfoData.Items?.size!!) {
            var startDay = checkRemoveZeroFromDate(
                leaveInfoData.Items!![i].SK.S.toString().substring(6, 8)
            )
            var startMonth = checkRemoveZeroFromDate(
                leaveInfoData.Items!![i].SK.S.toString().substring(8, 10)
            ) - 1
            var startYear = leaveInfoData.Items!![i].SK.S.toString().substring(10, 14).toInt()

            var endDay = checkRemoveZeroFromDate(
                leaveInfoData.Items!![i].SK.S.toString().substring(15, 17)
            )
            var endMonth = checkRemoveZeroFromDate(
                leaveInfoData.Items!![i].SK.S.toString().substring(17, 19)
            ) - 1
            var endYear = leaveInfoData.Items!![i].SK.S.toString().substring(19, 23).toInt()

            var leaveType = leaveInfoData.Items!![i].LeaveType.S

            var color = 0

            if (leaveType == "AL") {
                color = Color.parseColor("#35D660")
            } else if (leaveType == "MC") {
                color = Color.parseColor("#56A7F1")
            } else if (leaveType == "OIL") {
                color = Color.parseColor("#E37451")
            }
            while (startYear <= endYear) {
                while (startMonth <= endMonth) {
                    while (startDay <= endDay) {
                        calendar.set(startYear, startMonth, startDay)
                        var event = Event(calendar.timeInMillis, leaveType, color)
                        val date = TimeUtil.getDate(event.time)
                        val textKey = date + " TEXT"
                        val colorKey = date + " COLOR"
                        write(textKey, event.eventText, context)
                        write(colorKey, event.eventColor, context)
                        startDay++
                    }
                    startMonth++
                }
                startYear++
            }
            i++;
        }
        val intent = Intent(context, LeaveCalendarActivity::class.java)
        context.startActivity(intent);
    })
}


fun write(key: String?, value: String?,context: Context) {
    var mSharedPreferences: SharedPreferences? = null
    mSharedPreferences = context.getSharedPreferences("EventCalender", MODE_PRIVATE)
    val mEditor = mSharedPreferences!!.edit()
    mEditor.putString(key, value)
    mEditor.apply()
}

fun write(key: String?, value: Int,context: Context) {
    var mSharedPreferences: SharedPreferences? = null
    mSharedPreferences = context.getSharedPreferences("EventCalender", MODE_PRIVATE)
    val mEditor = mSharedPreferences!!.edit()
    mEditor.putInt(key, value)
    mEditor.apply()
}

fun checkRemoveZeroFromDate(dayOrMonth: String): Int {
    if (dayOrMonth.substring(0, 1) == "0") {
        return dayOrMonth.substring(1).toInt()
    } else {
        return dayOrMonth.toInt()
    }
}
