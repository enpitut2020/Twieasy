package com.example.twieasy

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.twieasy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener { jumpToLogin() }
    }

    private fun jumpToLogin(){
        setContentView(R.layout.login_page)
    }
}