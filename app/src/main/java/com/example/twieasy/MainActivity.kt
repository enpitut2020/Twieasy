package com.example.twieasy

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.twieasy.databinding.ActivityMainBinding
import org.jsoup.Jsoup
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener { jumpToLoginPage() }
    }

    private fun jumpToLoginPage() {
        setContentView(R.layout.login_page)
        val login: Button = findViewById(R.id.login_login)
        login.setOnClickListener{ jumpToSubjects(subjectsInfo.size) }
    }

    private val subject: MutableCollection<Button> = mutableListOf()//講義ボタンのリスト
    private fun jumpToSubjects(size: Int) {//講義一覧ページへ移動、講義ボタン生成
        setContentView(R.layout.subject)
        for (i in 1..size) {//i = sizeも処理される
            val r: Button = Button(this)
            r.id = i
            r.text = subjectsInfo[i - 1].name
            subject.add(r)
            val layout = findViewById<LinearLayout>(R.id.layout)
            layout.addView(r)
            r.setOnClickListener { jumpToReview(r.id) }
        }
    }

    private var reviews1: MutableCollection<String> = mutableListOf("楽単!", "落単!", "普通!", "Easy!", "楽単!", "落単!")
    private var reviews2: MutableCollection<String> = mutableListOf("楽単!(人工知能)", "落単!(人工知能)", "普通!(人工知能)", "Easy!(人工知能)")
    private var reviewList = mutableListOf(reviews1, reviews2)
    data class Subject(var name: String, var info: String, var easiness: Int, var reviews: MutableCollection<String>)//構造体みたいなクラス
    private var subjectsInfo  = mutableListOf(
        Subject("知能情報メディア実験B", "開講日時　秋ABC 水3,4 金5,6\n授業形態 オンライン オンデマンド 同時双方向 対面\n評価方法　レポートn割 出席m割\n単位数 3", 40, reviewList[0]),
        Subject("人工知能", "開講日時　秋AB 火3,4\n授業形態 オンライン オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2", 20, reviewList[1])
    )
    private var page = 1
    private fun jumpToReview(id: Int) {
        getTextFromWeb("https://kdb.tsukuba.ac.jp/syllabi/2020/BC12893/jpn/") //??????
        setContentView(R.layout.review)
        createReview(subjectsInfo[id - 1].name, subjectsInfo[id - 1].info, subjectsInfo[id - 1].easiness)
        val r1: TextView = findViewById(R.id.review1)
        val r2: TextView = findViewById(R.id.review2)
        val r3: TextView = findViewById(R.id.review3)
        val r4: TextView = findViewById(R.id.review4)
        val revColumn: List<TextView> = listOf(r1, r2, r3, r4)
        showReview(1, revColumn, id)

        val goBackToSubjects: Button = findViewById(R.id.back_button)
        val writeReview: Button = findViewById(R.id.writeReview)
        val pre: Button = findViewById(R.id.pre)
        val next: Button = findViewById(R.id.next)

        goBackToSubjects.setOnClickListener { jumpToSubjects(subjectsInfo.size) }
        writeReview.setOnClickListener { jumpToWritePage(id) }
        next.setOnClickListener {
            if (page <= (reviewList[id - 1].size - 1) / 4)
                showReview(++page, revColumn, id)
        }
        pre.setOnClickListener {
            if (page != 1)
                showReview(--page, revColumn, id)
        }
    }

    private fun createReview(subname: String, subinfo: String, easiness: Int) {//easiness[%] : 楽単度合い
        //"落%"部分生成
        val textView1 = findViewById<TextView>(R.id.difficulty)
        (textView1.getLayoutParams() as LinearLayout.LayoutParams).weight = (100 - easiness).toFloat()
        if (70 < easiness)//文字がはみ出る
            textView1.text = ""
        else//はみ出ない
            textView1.text = "落" + (100 - easiness) + "%"

        //"楽%"部分生成
        val textView2 = findViewById<TextView>(R.id.easiness)
        (textView2.getLayoutParams() as LinearLayout.LayoutParams).weight = easiness.toFloat()
        if (easiness < 30)
            textView2.text = ""
        else
            textView2.text = "楽" + easiness + "%"

        val textview3 = findViewById<TextView>(R.id.subject_name)//講義名変更
        textview3.text = subname
        val textview4 = findViewById<TextView>(R.id.subject_info)//基本情報変更
        textview4.text = subinfo
    }

    private fun showReview(page: Int, revColumn: List<TextView>, id: Int) {
        for (num in (page - 1) * 4 until page * 4) {
            if (num >= reviewList[id - 1].size)
                revColumn[num % 4].text = ""
            else
                revColumn[num % 4].text = reviewList[id - 1].elementAt(num)
        }

    }

    private fun jumpToWritePage(id: Int) {
        setContentView(R.layout.editform)
        val post: Button = findViewById(R.id.post)
        post.setOnClickListener { post(id) }
    }

    private fun post(id: Int) {
        val postContent: TextView = findViewById(R.id.reviewContent)
        val postStr: String = postContent.text.toString()
        reviewList[id - 1].add(postStr)
        jumpToReview(id)
    }

    private fun getTextFromWeb(urlString: String) {
        Thread(Runnable {
            try {
                val doc = Jsoup.connect(urlString).get();
                val title = doc.select("#course-title #title").first().text() //科目名
                val credit = doc.select("#credit-grade-assignments span").first().text() //単位数
                val eval = doc.select("#assessment-heading-assessment p").first().text() //評価方法
                Log.i("eval:", eval.toString())


            } catch (ex: Exception) {
                Log.d("Exception", ex.toString())
            }
        }).start()
    }

    private fun subjectJmp(id: Int){

        getTextFromWeb("https://kdb.tsukuba.ac.jp/syllabi/2020/BC12893/jpn/")
        jumpToReview(id)
    }


}

