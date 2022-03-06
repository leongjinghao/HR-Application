package com.example.frontend.tabfragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.frontend.R
import com.example.frontend.adapters.HistoryAdapter
import com.example.frontend.databinding.FragmentHistoryBinding
import com.example.frontend.viewmodels.HistoryViewModel

class HistoryFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    )
    : View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val historyViewModel = HistoryViewModel()
        historyViewModel.loadHistoryList()
        val recyclerViewHistory = view.findViewById<RecyclerView>(R.id.recyclerViewHistory).apply{
            layoutManager = LinearLayoutManager(activity)
            adapter = HistoryAdapter(historyViewModel)
        }
        recyclerViewHistory.addItemDecoration(//DIVIDER
            DividerItemDecoration(
                recyclerViewHistory.context,
                DividerItemDecoration.VERTICAL
            )
        )
//        val historyViewModel = HistoryViewModel()
//        historyViewModel.loadHistoryList()
//        val historyList = historyViewModel.historyList.value
//        val adapter = historyList?.let { HistoryAdapter(it) }
//        val historyRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewHistory)
//        historyRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
//        historyRecyclerView.adapter = adapter
    }
}