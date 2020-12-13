package com.example.twieasy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.twieasy.databinding.FragmentFlickBinding
import kotlinx.android.synthetic.main.first_boot.*
import kotlinx.android.synthetic.main.first_boot.view.*
import org.jsoup.Jsoup

class FlickFragment : Fragment() {

    //lateinit var binding:FragmentFlickBinding
    lateinit var vii : View
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vii = inflater.inflate(R.layout.fragment_flick, container, false)
        jmpToFlick()

        return vii
    }


    data class Subject(
        var name: String,
        var info: String,
        var easiness: Int,
        var reviews: MutableCollection<String>
    )//構造体みたいなクラス

    val flickAttribute = mutableMapOf<Int, String>()
    var swipedCount = 0
    var res :String = ""
    val subjectInfo = mutableListOf<String>(
        "知能情報メディア実験B\n金曜日\n5,6時限",
        "数理メディア情報学\n水曜日\n1,2時限",
        "パターン認識\n木曜日\n3,4時限",
        "オペレーティングシステム\n月曜日\n5,6時限"
    )

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
    val subjects: List<Subject?> = kdbRawData.map {
        Log.i("info", it)
        getTextFromWeb(it)
    }


    private fun jmpToFlick(){
        //setContentView(R.layout.first_boot)
        vii.center.setOnTouchListener(FlickListener(flickListener))
    }

    private val flickListener = object : FlickListener.Listener {

        override fun onButtonPressed() {
            vii.center.setBackgroundButtonColor(R.color.pressedButtonColor)
            toggleVisible()
        }

        override fun onSwipingOnCenter() = vii.center.settingSwipe()
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
            Log.i("Flicked", label)

            // 画面遷移
            // 1.フリック情報:labelを保持しておく
            flickAttribute.put(flickAttribute.count(), label)
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
            val subjectView : TextView = subject_info
            subjectView.text = subjects[swipedCount]?.name
            //if (swipedCount < subjectInfo.size-1){
            swipedCount += 1

            // ------------

            // 3.全部終わったら履修科目一覧に遷移
            if(swipedCount == subjectInfo.size) {
                // 画面遷移
                //setContentView(R.layout.activity_main_copy)
                //jumpToLoginPage()
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
            Log.i("Swiped", "")
        }

        private fun View.setBackgroundButtonColor(@ColorRes resId: Int) =
            setBackgroundColor(ContextCompat.getColor(requireActivity().applicationContext, resId))

        private fun showToast(msg: String) = Toast.makeText(
            context,
            msg,
            Toast.LENGTH_LONG
        ).show()
    }

    private val subject: MutableCollection<Button> = mutableListOf()//講義ボタンのリスト


    private var reviews1: MutableCollection<String> = mutableListOf(
        "楽単!",
        "落単!",
        "普通!",
        "Easy!",
        "楽単!",
        "落単!",
        "楽単!",
        "落単!",
        "普通!",
        "Easy!",
        "楽単!",
        "落単!",
        "楽単!",
        "落単!",
        "普通!",
        "Easy!",
        "楽単!",
        "落単!",
        "楽単!",
        "落単!",
        "普通!",
        "Easy!",
        "楽単!",
        "落単!",
        "楽単!",
        "落単!",
        "普通!",
        "Easy!",
        "楽単!",
        "落単!",
        "楽単!",
        "落単!",
        "普通!",
        "Easy!",
        "楽単!",
        "落単!"
    )
    private var reviews2: MutableCollection<String> = mutableListOf(
        "楽単!(人工知能)",
        "落単!(人工知能)",
        "普通!(人工知能)",
        "Easy!(人工知能)"
    )
    private var reviews3: MutableCollection<String> = mutableListOf(
        "楽単!(人工知能)",
        "落単!(人工知能)",
        "普通!(人工知能)",
        "Easy!(人工知能)"
    )
    private var reviews4: MutableCollection<String> = mutableListOf(
        "楽単!(人工知能)",
        "落単!(人工知能)",
        "普通!(人工知能)",
        "Easy!(人工知能)"
    )
    private var reviews5: MutableCollection<String> = mutableListOf(
        "楽単!(人工知能)",
        "落単!(人工知能)",
        "普通!(人工知能)",
        "Easy!(人工知能)"
    )
    private var reviews6: MutableCollection<String> = mutableListOf(
        "楽単!(人工知能)",
        "落単!(人工知能)",
        "普通!(人工知能)",
        "Easy!(人工知能)"
    )
    private var reviews7: MutableCollection<String> = mutableListOf(
        "楽単!(人工知能)",
        "落単!(人工知能)",
        "普通!(人工知能)",
        "Easy!(人工知能)"
    )
    private var reviews8: MutableCollection<String> = mutableListOf(
        "楽単!(人工知能)",
        "落単!(人工知能)",
        "普通!(人工知能)",
        "Easy!(人工知能)"
    )
    private var reviewList = mutableListOf(
        reviews1,
        reviews2,
        reviews3,
        reviews4,
        reviews5,
        reviews6,
        reviews7,
        reviews8
    )
    private var subjectsInfo  = mutableListOf(
        Subject(
            "知能情報メディア実験B",
            "開講日時　秋ABC 水3,4 金5,6\n授業形態 オンライン オンデマンド 同時双方向 対面\n評価方法　レポートn割 出席m割\n単位数 3",
            40,
            reviewList[0]
        ),
        Subject(
            "人工知能",
            "開講日時　秋AB 火3,4\n授業形態 オンライン オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2",
            20,
            reviewList[1]
        ),
        Subject(
            "分散システム",
            "開講日時　秋AB 月3\n授業形態 対面 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 1",
            20,
            reviewList[2]
        ),
        Subject(
            "画像メディア工学",
            "開講日時　秋AB 火5,6\n授業形態 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2",
            20,
            reviewList[3]
        ),
        Subject("視覚情報科学", "開講日時　秋AB 木1,2\n授業形態 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2", 60, reviewList[4]),
        Subject(
            "パターン認識",
            "開講日時　秋AB 木3,4\n授業形態 オンライン オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2",
            40,
            reviewList[5]
        ),
        Subject(
            "数理アルゴリズムとシミュレーション",
            "開講日時　秋AB 金1,2\n授業形態 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2",
            30,
            reviewList[6]
        ),
        Subject(
            "データベース概論",
            "開講日時　秋AB 金3,4\n授業形態 オンデマンド\n評価方法　レポートn割 出席m割\n単位数 2",
            40,
            reviewList[7]
        )
    )


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
            } catch (ex: Exception) {
                Log.d("Exception", ex.toString())
            }
        }).start()

        return subject
    }






}