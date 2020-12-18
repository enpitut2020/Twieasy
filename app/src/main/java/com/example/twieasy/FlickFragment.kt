package com.example.twieasy

import android.os.Bundle
import android.os.Parcelable
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.twieasy.databinding.FragmentFlickBinding
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import kotlinx.android.synthetic.main.first_boot.*
import kotlinx.android.synthetic.main.first_boot.view.*
import org.jsoup.Jsoup
import java.util.ArrayList

class FlickFragment : Fragment() {

    //lateinit var binding:FragmentFlickBinding
    lateinit var vii : View
    lateinit var subjectView : SubjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        vii = inflater.inflate(R.layout.fragment_flick, container, false)

        subjectView = ViewModelProvider(this).get(SubjectViewModel::class.java)
        jmpToFlick()

        subjectView.subjectNumber = mutableListOf<String>(
            // 科目番号
            "GB22101", // 数理メディア
            "GB40201", // パターン認識
            "GB30411", // OS
            "BC12893", // enPiT
        )

        subjectView.kdbRawData = subjectView.subjectNumber.map{
            "https://kdb.tsukuba.ac.jp/syllabi/2020/$it/jpn/"
        } as MutableList<String>

        // KDBの情報を整形したもの
        subjectView.subjects = subjectView.kdbRawData.map{
            getTextFromWeb(it)
        } as MutableList<Subject>

        return vii
    }


    val flickAttribute = mutableMapOf<Int, String>()
    var swipedCount = 0
    var res :String = ""
    val subjectInfo = mutableListOf<String>(
        "知能情報メディア実験B\n金曜日\n5,6時限",
        "数理メディア情報学\n水曜日\n1,2時限",
        "パターン認識\n木曜日\n3,4時限",
        "オペレーティングシステム\n月曜日\n5,6時限"
    )





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
            val subject_View : TextView = subject_info
            subject_View.text =  subjectView.subjects[swipedCount]?.name
            //if (swipedCount < subjectInfo.size-1){
            swipedCount += 1

            // ------------

            // 3.全部終わったら履修科目一覧に遷移
            if(swipedCount == subjectInfo.size) {

                findNavController().navigate(R.id.action_flickFragment2_to_loginFragment)
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







    private fun getTextFromWeb(urlString: String): Subject? {
        var subject: Subject? = null
        val tr = Thread(Runnable {
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
                    subjectView.reviewList[7]
                );
            } catch (ex: Exception) {
                Log.d("Exception", ex.toString())
            }
        })

        tr.join()

        tr.start()


        return subject
    }






}