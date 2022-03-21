package com.example.frontend.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.dataclass.CheckInOutData
import com.example.frontend.viewmodels.HistoryViewModel

class HistoryAdapter(val historyList: HistoryViewModel): RecyclerView.Adapter<HistoryAdapter.historyViewHolder>() {

//    val list = listOf<String>("test","test","test","test","test")
    val list = arrayListOf<CheckInOutData>(
    CheckInOutData("20th Mar 2022","08:00AM","05:00PM"),
    CheckInOutData("21st Mar 2022","08:00AM","05:00PM"),
    CheckInOutData("22nd Mar 2022","08:00AM","05:00PM"),
    CheckInOutData("23rd Mar 2022","08:00AM","05:00PM"),
    CheckInOutData("24th Mar 2022","08:00AM","05:00PM"))

    class historyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val textViewHistoryHeader = itemView.findViewById<TextView>(R.id.textViewHistoryHeader)
        val imageViewArrow = itemView.findViewById<ImageView>(R.id.imageViewHistoryArrow)
        val expandedLayout = itemView.findViewById<ConstraintLayout>(R.id.expandedLayout)
        val textViewCheckInTiming = itemView.findViewById<TextView>(R.id.textViewCheckInTiming)
        val textViewCheckOutTiming = itemView.findViewById<TextView>(R.id.textViewCheckOutTiming)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): historyViewHolder {
        val history = LayoutInflater.from(parent.context).inflate(R.layout.list_history,parent,false)
        return historyViewHolder(history)
    }

    override fun onBindViewHolder(holder: historyViewHolder, position: Int) {
        val historyItem = list.get(position)
        val isVisible = historyItem.visibility
        val isToggled = historyItem.toggled
        holder.textViewHistoryHeader.text = historyItem.date.toString()
        if(isToggled) holder.imageViewArrow.setImageResource(R.drawable.dropdown_arrow_up) else holder.imageViewArrow.setImageResource(R.drawable.dropdown_arrow_down)
        holder.textViewCheckInTiming.text = "Check In: ${historyItem.checkInTiming}"
        holder.textViewCheckOutTiming.text = "Check Out: ${historyItem.checkOutTiming}"
        holder.expandedLayout.visibility = if(isVisible) View.VISIBLE else View.GONE
        holder.textViewHistoryHeader.setOnClickListener{
            historyItem.visibility = !historyItem.visibility
            historyItem.toggled = !historyItem.toggled
            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}