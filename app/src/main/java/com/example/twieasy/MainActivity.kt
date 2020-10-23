package com.example.twieasy

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
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

    private val reviews : List<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!","a","b","c","d","e")
    private var page : Int = 1
    private fun jumpToLogin(){
        setContentView(R.layout.review)
        val r1 : TextView = findViewById(R.id.review1)
        val r2 : TextView = findViewById(R.id.review2)
        val r3 : TextView = findViewById(R.id.review3)
        val r4 : TextView = findViewById(R.id.review4)
        val revColumn : List<TextView> = listOf(r1,r2,r3,r4)
        showReview(page,revColumn)
        val pre : Button = findViewById(R.id.pre)
        val next : Button = findViewById(R.id.next)
        next.setOnClickListener {
            if(page <= reviews.size / 4)
                showReview(++page, revColumn)
        }
        pre.setOnClickListener {
            if(page != 1)
                showReview(--page,revColumn)
        }
    }

    private fun showReview(page : Int, revColumn : List<TextView>){
        for(num in (page-1) * 4 until page*4) {
            if(num >= reviews.size)
                revColumn[num % 4].text = ""
            else
                revColumn[num % 4].text = reviews[num]
        }

    }

}