package com.example.frontend.leaveModule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R

class LeaveSummaryRecycler(
    private val leaves: Array<String>,
) : RecyclerView.Adapter<LeaveSummaryRecycler.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaveSummaryRecycler.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.activity_leaves_summary_card,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: LeaveSummaryRecycler.ViewHolder, position: Int) {
        holder.leaveTitle.text =leaves[position]
        holder.leaveDate.text =leaves[position]
        holder.leaveStatus.text =leaves[position]
    }

    override fun getItemCount(): Int {
        return leaves.size
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
}