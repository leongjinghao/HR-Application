package com.example.frontend.tabfragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.frontend.CheckInDetailActivity
import com.example.frontend.R
import com.example.frontend.leaveModule.LeaveSummaryActivity

class CheckInOutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_in_out, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val checkinButton = view.findViewById<Button>(R.id.buttonCheckInOut)
        checkinButton.setOnClickListener{
            val intent = Intent (activity, CheckInDetailActivity::class.java)
            activity?.startActivity(intent)
        }
    }
}