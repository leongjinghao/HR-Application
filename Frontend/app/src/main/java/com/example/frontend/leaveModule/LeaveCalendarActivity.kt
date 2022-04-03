package com.example.frontend.leaveModule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.frontend.databinding.ActivityCalendarBinding

class LeaveCalendarActivity : AppCompatActivity()  {

    private lateinit var binding: ActivityCalendarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }
}