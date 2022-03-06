package com.example.frontend.adapters

import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.viewmodels.HistoryViewModel

class HistoryAdapter(val historyList: HistoryViewModel): RecyclerView.Adapter<HistoryAdapter.historyViewHolder>() {

    val list = listOf<String>("test","test","test","test","test")

    class historyViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
        val textViewHistoryHeader = itemView.findViewById<TextView>(R.id.textViewHistoryHeader)
        val imageViewArrow = itemView.findViewById<ImageView>(R.id.imageViewHistoryArrow)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): historyViewHolder {
        val history = LayoutInflater.from(parent.context).inflate(R.layout.list_history,parent,false)
        return historyViewHolder(history)
    }

    override fun onBindViewHolder(holder: historyViewHolder, position: Int) {
        val historyItem = list.get(position)
        holder.textViewHistoryHeader.setText(historyItem.toString())
//        holder.imageViewArrow.setImageResource()
    }

    override fun getItemCount(): Int {
        return list.size
    }

}