package com.example.twieasy

import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
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
        binding.loginButton.setOnClickListener { generateButton(4) }
    }

    private val reviews: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!")
    private var page: Int = 1
    private fun jumpToLogin() {
        setContentView(R.layout.review)
        val r1: TextView = findViewById(R.id.review1)
        val r2: TextView = findViewById(R.id.review2)
        val r3: TextView = findViewById(R.id.review3)
        val r4: TextView = findViewById(R.id.review4)
        val revColumn: List<TextView> = listOf(r1, r2, r3, r4)
        showReview(page, revColumn)
        val pre: Button = findViewById(R.id.pre)
        val next: Button = findViewById(R.id.next)
        val writeReview: Button = findViewById(R.id.writeReview)
        writeReview.setOnClickListener {
            jumpToWritePage()
        }

        next.setOnClickListener {
            if (page <= reviews.size / 4)
                showReview(++page, revColumn)
        }
        pre.setOnClickListener {
            if (page != 1)
                showReview(--page, revColumn)
        }
    }

    private fun showReview(page: Int, revColumn: List<TextView>) {
        for (num in (page - 1) * 4 until page * 4) {
            if (num >= reviews.size)
                revColumn[num % 4].text = ""
            else
                revColumn[num % 4].text = reviews.elementAt(num)
        }

    }

    private fun jumpToWritePage() {
        setContentView(R.layout.editform)
        val post: Button = findViewById(R.id.post)
        post.setOnClickListener { post() }
    }

    private fun post() {
        val postContent: TextView = findViewById(R.id.reviewContent)
        val postStr: String = postContent.text.toString()
        reviews.add(postStr)
        jumpToLogin()
    }

    private val subject: MutableCollection<Button> = mutableListOf()
    private fun generateButton(size: Int) {
        setContentView(R.layout.subject)
        for (i in 1 until size) {
            val r: Button = Button(this)
            r.id = i
            if (i == 1)
                r.text = "知能情報メディア実験B"
            else if (i == 2)
                r.text = "人工知能"
            else
                r.text = i.toString()
            subject.add(r)
            val layout = findViewById<LinearLayout>(R.id.layout)
            layout.addView(r)
            r.setOnClickListener { subjectJmp(r.id) }
        }
    }

    private fun subjectJmp(id: Int) {
        if (id == 2) {
            val subname: String = "人工知能"
            val subinfo: String = "開講日時　秋AB 火3,4\n授業形態 オンライン オンデマンド\n 評価方法　レポートn割 出席m割\n 単位数 2"
            val easiness: Int = 20
            jumpToLogin()
            createReview(easiness, subname, subinfo)
        }
        else
            jumpToLogin()
    }

    private fun createReview(easiness: Int, subname: String, subinfo: String) {//easiness[%] : 楽単度合い
        val textView1 = findViewById<TextView>(R.id.difficulty)
        (textView1.getLayoutParams() as LinearLayout.LayoutParams).weight = (100 - easiness).toFloat()
        if (70 < easiness)//文字がはみ出る
            textView1.text = ""
        else//はみ出ない
            textView1.text = "落" + (100 - easiness) + "%"

        val textView2 = findViewById<TextView>(R.id.easiness)
        (textView2.getLayoutParams() as LinearLayout.LayoutParams).weight = easiness.toFloat()
        if (easiness < 30)
            textView2.text = ""
        else
            textView2.text = "楽" + easiness + "%"

        val textview3 = findViewById<TextView>(R.id.subject_name)
        textview3.text = subname
        val textview4 = findViewById<TextView>(R.id.subject_info)
        textview4.text = subinfo
    }

}