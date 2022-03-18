package com.example.frontend.leaveModule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityLeavesAddBinding

class LeaveAddActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityLeavesAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeavesAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}