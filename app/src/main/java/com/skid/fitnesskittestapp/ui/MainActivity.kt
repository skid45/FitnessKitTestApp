package com.skid.fitnesskittestapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.skid.fitnesskittestapp.R
import com.skid.fitnesskittestapp.databinding.ActivityMainBinding
import com.skid.fitnesskittestapp.utils.addFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            addFragment(R.id.fragment_container, ScheduleFragment())
        }
    }
}