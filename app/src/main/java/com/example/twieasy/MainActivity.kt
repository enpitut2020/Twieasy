package com.example.twieasy

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.twieasy.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //binding.loginButton.setOnClickListener { jumpToLogin() }
        center.setOnTouchListener(FlickListener(flickListener))
    }

    private val flickListener = object : FlickListener.Listener {

        override fun onButtonPressed() {
            center.setBackgroundButtonColor(R.color.pressedButtonColor)
            toggleVisible()
        }

        override fun onSwipingOnCenter() = center.settingSwipe()
        override fun onSwipingOutside() {
            TODO("Not yet implemented")
        }

        override fun onSwipingOnLeft() = left.settingSwipe()
        override fun onSwipingOnRight() = right.settingSwipe()
        override fun onSwipingOnUp() = top.settingSwipe()
        override fun onSwipingOnDown() = bottom.settingSwipe()

        override fun onButtonReleased() = settingFlick("中")
        override fun onFlickToLeft() = settingFlick("落単")
        override fun onFlickToRight() = settingFlick("楽単")
        override fun onFlickToUp() = settingFlick("知らん！！！")
        override fun onFlickToDown() = settingFlick("下")
        override fun onFlickOutside() {
            TODO("Not yet implemented")
        }


        private fun settingFlick(label: String) {
            showToast(label)
            //Thread.sleep(100)
            toggleInvisible()
            clearAll()
        }

        private fun toggleVisible() {
            top.visibility = View.VISIBLE
            left.visibility = View.VISIBLE
            right.visibility = View.VISIBLE
            bottom.visibility = View.VISIBLE
        }

        private fun toggleInvisible() {
            top.visibility = View.INVISIBLE
            left.visibility = View.INVISIBLE
            right.visibility = View.INVISIBLE
            bottom.visibility = View.INVISIBLE
        }

        private fun clearAll() {
            center.setBackgroundButtonColor(R.color.baseButtonColor)
            left.setBackgroundButtonColor(R.color.lightgray)
            right.setBackgroundButtonColor(R.color.lightgray)
            top.setBackgroundButtonColor(R.color.lightgray)
            bottom.setBackgroundButtonColor(R.color.baseButtonColor)
        }

        private fun View.settingSwipe() {
            clearAll()
            setBackgroundButtonColor(R.color.pressedButtonColor)
        }

        private fun View.setBackgroundButtonColor(@ColorRes resId: Int) =
            setBackgroundColor(ContextCompat.getColor(applicationContext, resId))

        private fun showToast(msg: String) = Toast.makeText(this@MainActivity, msg, Toast.LENGTH_LONG).show()
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