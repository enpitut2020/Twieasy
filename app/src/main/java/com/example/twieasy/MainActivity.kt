package com.example.twieasy

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.twieasy.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.first_boot.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jsoup.Jsoup
import javax.net.ssl.HttpsURLConnection
import java.io.*
import java.util.Random

class MainActivity : AppCompatActivity(),MailSender.OnMailSendListener {

    private lateinit var binding: ActivityMainBinding
    data class Subject(var name: String, var info: String, var easiness: Int, var reviews: MutableCollection<String>)//構造体みたいなクラス

    val flickAttribute = mutableMapOf<Int, String>()
    var swipedCount = 0
    val subjectInfo = mutableListOf<String>(
        "知能情報メディア実験B\n金曜日\n5,6時限",
        "数理メディア情報学\n水曜日\n1,2時限",
        "パターン認識\n木曜日\n3,4時限",
        "オペレーティングシステム\n月曜日\n5,6時限")

    val subjectNumber = mutableListOf<String>(
        // 科目番号
        "GB22101", // 数理メディア
        "GB40201", // パターン認識
        "GB30411", // OS
        "BC12893", // enPiT
    )

    // KDBからとってきた生データ
    private val kdbRawData = subjectNumber.map{
        "https://kdb.tsukuba.ac.jp/syllabi/2020/$it/jpn/"
    }

    // KDBの情報を整形したもの
    val subjects: List<MainActivity.Subject?> = kdbRawData.map {
        Log.i("info", it)
        getTextFromWeb(it)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener{
            val mail = getMail()
            MailSender.getInstance().sendMail(mail, this)
        }
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.makeAccount.setOnClickListener { jmpToFlick() }
        binding.loginButton.setOnClickListener { jumpToLoginPage() }
    }

    private fun jmpToFlick(){
        setContentView(R.layout.first_boot)
        center.setOnTouchListener(FlickListener(flickListener))
    }

    private val flickListener = object : FlickListener.Listener {

        override fun onButtonPressed() {
            center.setBackgroundButtonColor(R.color.pressedButtonColor)
            toggleVisible()
        }

        override fun onSwipingOnCenter() = center.settingSwipe()
        override fun onSwipingOutside() {
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
            settingFlick("")
        }


        private fun settingFlick(label: String) {
            showToast(label)
            //Thread.sleep(100)
            toggleInvisible()
            clearAll()
            Log.i("Flicked",label)

            // 画面遷移
            // 1.フリック情報:labelを保持しておく
            flickAttribute.put(flickAttribute.count(),label)
            print(flickAttribute)

/*
            // 2.科目情報を変更
            // ---丹羽君---
            val subjectView : TextView = findViewById(R.id.subject_info)
            subjectView.text = subjectInfo[swipedCount]
            //if (swipedCount < subjectInfo.size-1){
            swipedCount += 1
            //}
            // ------------

            // 3.全部終わったら履修科目一覧に遷移
            if(swipedCount == subjectInfo.size) {
                // 画面遷移
                //setContentView(R.layout.activity_main_copy)
                jumpToLoginPage()
            }
 */
            val subjectView : TextView = findViewById(R.id.subject_info)
            subjectView.text = subjects[swipedCount]?.name
            //if (swipedCount < subjectInfo.size-1){
            swipedCount += 1

            // ------------

            // 3.全部終わったら履修科目一覧に遷移
            if(swipedCount == subjectInfo.size) {
                // 画面遷移
                //setContentView(R.layout.activity_main_copy)
                jumpToLoginPage()
            }


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
            Log.i("Swiped","")
        }

        private fun View.setBackgroundButtonColor(@ColorRes resId: Int) =
            setBackgroundColor(ContextCompat.getColor(applicationContext, resId))

        private fun showToast(msg: String) = Toast.makeText(this@MainActivity, msg, Toast.LENGTH_LONG).show()
    }


    override fun jumpToLoginPage() {
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
    private var reviews3: MutableCollection<String> = mutableListOf("楽単!(人工知能)", "落単!(人工知能)", "普通!(人工知能)", "Easy!(人工知能)")
    private var reviews4: MutableCollection<String> = mutableListOf("楽単!(人工知能)", "落単!(人工知能)", "普通!(人工知能)", "Easy!(人工知能)")
    private var reviews5: MutableCollection<String> = mutableListOf("楽単!(人工知能)", "落単!(人工知能)", "普通!(人工知能)", "Easy!(人工知能)")
    private var reviews6: MutableCollection<String> = mutableListOf("楽単!(人工知能)", "落単!(人工知能)", "普通!(人工知能)", "Easy!(人工知能)")
    private var reviews7: MutableCollection<String> = mutableListOf("楽単!(人工知能)", "落単!(人工知能)", "普通!(人工知能)", "Easy!(人工知能)")
    private var reviews8: MutableCollection<String> = mutableListOf("楽単!(人工知能)", "落単!(人工知能)", "普通!(人工知能)", "Easy!(人工知能)")
    private var reviewList = mutableListOf(reviews1, reviews2, reviews3, reviews4, reviews5, reviews6, reviews7, reviews8)
    private var subjectsInfo  = mutableListOf(
        Subject("知能情報メディア実験B", "開講日時　秋ABC 水3,4 金5,6\n授業形態 オンライン オンデマンド 同時双方向 対面\n評価方法　レポートn割 出席m割\n単位数 3", 40, reviewList[0]),
        Subject("人工知能", "開講日時　秋AB 火3,4\n授業形態 オンライン オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2", 20, reviewList[1]),
        Subject("分散システム", "開講日時　秋AB 月3\n授業形態 対面 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 1", 20, reviewList[2]),
        Subject("画像メディア工学", "開講日時　秋AB 火5,6\n授業形態 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2", 20, reviewList[3]),
        Subject("視覚情報科学", "開講日時　秋AB 木1,2\n授業形態 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2", 60, reviewList[4]),
        Subject("パターン認識", "開講日時　秋AB 木3,4\n授業形態 オンライン オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2", 40, reviewList[5]),
        Subject("数理アルゴリズムとシミュレーション", "開講日時　秋AB 金1,2\n授業形態 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2", 30, reviewList[6]),
        Subject("データベース概論", "開講日時　秋AB 金3,4\n授業形態 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2", 40, reviewList[7])
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

    private fun getTextFromWeb(urlString: String): Subject? {
        var subject: Subject? = null
        Thread(Runnable {
            try {
                val doc = Jsoup.connect(urlString).get();
                val title = doc.select("#course-title #title").first().text() //科目名
                val credit = doc.select("#credit-grade-assignments #credit").first().text() //単位数
                val timetable =
                    doc.select("#credit-grade-assignments #timetable").first().text() //開講日時
                val styleHeading = doc.select("#style-heading-style p").first().text() //授業形態
                val eval = doc.select("#assessment-heading-assessment p").first().text() //評価方法
                Log.i("title:", title.toString())
                Log.i("credit:", title.toString())
                Log.i("eval:", eval.toString())
                subject = Subject(
                    title,
                    "開講日時　" + timetable + "\n授業形態 " + styleHeading + "\n" + eval + "\n単位数 " + credit,
                    40,
                    reviewList[7]
                );
            }
            catch (ex: Exception) {
                Log.d("Exception", ex.toString())
            }
        }).start()

        return subject
    }

    private fun subjectJmp(id: Int){

        getTextFromWeb("https://kdb.tsukuba.ac.jp/syllabi/2020/BC12893/jpn/")
        jumpToReview(id)
    }

     private fun Very(min:Int,max:Int):Int{
        val rand = Random()
        val randomNum = rand.nextInt((max-min)+1) + min
        return randomNum
    }

    override fun getMail(): Mail{
        return Mail().apply {
            mailServerHost = "smtp.qq.com"
            mailServerPort = "587"
            fromAddress = "*****"
            password = "*****"
            toAddress = arrayListOf(mailAddress.text.toString())
            subject = "Twieasy messageSender Test"

            val message :String = Very(1000,9999).toString()
            content = SpanUtils(this@MainActivity)
                .appendLine(message).setFontSize(28, true)
                .create()
        }
    }

    override fun onSuccess() {
        Toast.makeText(this@MainActivity, "成功", Toast.LENGTH_SHORT).show()
    }

    override fun onError(e: Throwable) {
        Toast.makeText(this@MainActivity, ": $e.message", Toast.LENGTH_SHORT).show()
    }


}

