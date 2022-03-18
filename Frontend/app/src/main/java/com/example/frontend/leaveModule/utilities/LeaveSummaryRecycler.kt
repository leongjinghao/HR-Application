package com.example.frontend.leaveModule.utilities

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.retroAPI.api.model.leaveInformationModel
import java.text.SimpleDateFormat
import java.util.*

class LeaveSummaryRecycler(
    private val leavesData: leaveInformationModel,
) : RecyclerView.Adapter<LeaveSummaryRecycler.ViewHolder>() {

    var dateFormat = SimpleDateFormat("ddMMyyyy", Locale.getDefault())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_leaves_summary_card,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var leaveType = leavesData.Items?.get(position)?.LeaveType?.S.toString()
        val status = leavesData.Items?.get(position)?.Status?.S.toString()

        if(leaveType == "AL"){
            leaveType = "Annual Leave"
        } else if (leaveType == "MC"){
            leaveType = "Medical Leave"
        } else if (leaveType == "OIL"){
            leaveType = "Off In Lieu"
        }

        holder.leaveTitle.text = leaveType
        holder.leaveDate.text =convertDateFormat(leavesData.Items?.get(position)?.SK?.S.toString())
        holder.leaveStatus.text = status

        if(status == "Approved"){
            holder.leaveStatus.setTextColor(Color.parseColor("#35D660"))
        } else if(status == "Pending"){
            holder.leaveStatus.setTextColor(Color.parseColor("#D6B235"))
        }

        if(status == "Approved" &&
            (dateFormat.format(Date()) >
            leavesData.Items?.get(position)?.SK?.S?.substring(15,23).toString()))
        {
            holder.leaveStatusButton.text = "Remove"
        }
    }

    override fun getItemCount(): Int {
        return leavesData.Items!!.size
    }

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder (itemView){
        var leaveTitle: TextView
        var leaveDate: TextView
        var leaveStatus: TextView
        var leaveStatusButton: Button

        init {
            leaveTitle = itemView.findViewById(R.id.leaveTitle)
            leaveDate = itemView.findViewById(R.id.leaveDate)
            leaveStatus = itemView.findViewById(R.id.leaveStatus)
            leaveStatusButton = itemView.findViewById(R.id.leaveStatusButton)
        }
    }

    fun convertDateFormat(date : String): String {
        var startDate = date.substring(6,8) + " " + convertNumberToMonth(date.substring(8,10)) + " " + date.substring(10,14)
        var endDate =date.substring(15,17) + " " + convertNumberToMonth(date.substring(17,19)) + " " + date.substring(19,23)

        if(startDate == endDate)
        {
            return startDate
        }
        else{
            return startDate + " to " + endDate
        }
    }

    fun convertNumberToMonth(number : String): String{
        var month = ""
        if(number == "01"){
            month = "Jan"
        } else if (number == "02"){
            month = "Feb"
        } else if (number == "03"){
            month = "Mar"
        } else if (number == "04"){
            month = "Apr"
        } else if (number == "05"){
            month = "May"
        } else if (number == "06"){
            month = "June"
        } else if (number == "07"){
            month = "Jul"
        } else if (number == "08"){
            month = "Aug"
        } else if (number == "09"){
            month = "Sep"
        } else if (number == "10"){
            month = "Oct"
        } else if (number == "11"){
            month = "Nov"
        } else if (number == "12"){
            month = "Dec"
        }
        return month
    }
}