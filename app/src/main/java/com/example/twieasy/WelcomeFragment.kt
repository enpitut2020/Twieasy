package com.example.twieasy

import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.bouncycastle.util.Arrays.append
import org.jsoup.Jsoup


class WelcomeFragment : Fragment() {
    lateinit var subjectView : SubjectViewModel
    private lateinit var subjects : ArrayList<Subject>
    private var counter: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        val progress = view.findViewById<ProgressBar>(R.id.progress_welcome)
        progress.indeterminateDrawable.setColorFilter(ContextCompat.getColor(view.context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)//ProgressSpinnerの色を設定

        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)

        val load = Thread {
            if(times++ == 0) {//戻るボタンで遷移してきた際は再度科目情報を読み取らない
                // KDBの情報を整形したもの
                val ccnt = subjectView.coinsSubjectNumber.size
                val mcnt = subjectView.mastSubjectNumber.size
                val kcnt = subjectView.klisSubjectNumber.size

                for(i in 0 until ccnt) {
                    getTextFromWeb(i, subjectView.coinsSubjectNumber[i], 0)?.let {
                        subjectView.coinsSubjects.add(it)
                    }
                }
                for(i in 0 until mcnt) {
                    getTextFromWeb(i + ccnt, subjectView.mastSubjectNumber[i], 1)?.let {
                        subjectView.mastSubjects.add(it)
                    }
                }
                for(i in 0 until kcnt) {
                    getTextFromWeb(i + ccnt + mcnt, subjectView.klisSubjectNumber[i], 0)?.let {
                        subjectView.klisSubjects.add(it)
                    }
                }
            }

            subjectView.loaded = true

            subjects = (subjectView.coinsSubjects + subjectView.mastSubjects + subjectView.klisSubjects) as ArrayList<Subject>
            Log.i("subjects.size", subjects.size.toString())
            val tb : TestWeb3? = TestWeb3(activity, subjects)
            var subNames: Array<String> = arrayOf()
            for(i in 0 until subjects.size) {
                subNames = append(subNames, subjects[i].classNum)
            }
            tb?.getEasy(subjects.size, subNames)
            tb?.getDifficult(subjects.size, subNames)
            //Log.i("Votes", (i).toString() + subjects[i].name + " " + subjects[i].eVotes.toString() + " / " + subjects[i].dVotes.toString())

            subjectView.votesLoaded = true
        }

        load.start()

        //viewをreturnするためだけのスレッド
        val sleep = Thread{
            Thread.sleep(1000)
            findNavController().navigate(R.id.action_welcomeFragment_to_loadFragment)
        }

        sleep.start()

        return view
    }

    private fun getTextFromWeb(id: Int, classNum: String, command: Int): Subject? {
        var subject: Subject? = null
        val tr = Thread(Runnable {
            try{
                val doc = Jsoup.connect("https://kdb.tsukuba.ac.jp/syllabi/2020/$classNum/jpn/").get()
                val title = doc.select("#course-title #title").first().text() //科目名
                val assignments = doc.select("#credit-grade-assignments #assignments").first().text()
                val credit = doc.select("#credit-grade-assignments #credit").first().text() //単位数
                val timetable = doc.select("#credit-grade-assignments #timetable").first().text() //開講日
                lateinit var styleHeading: String //授業形態
                lateinit var eval: String //評価方法
                when(command){
                    0 -> {
                        styleHeading = doc.select("#style-heading-style p").first().text() //授業形態
                        eval = doc.select("#assessment-heading-assessment p").first().text() //評価方法
                    }
                    1 -> {
                        styleHeading = doc.select("#note-heading-note:contains(授業方法) p").first().text() //授業形態
                        eval = doc.select("#style-heading-style:contains(成績評価) p").first().text() //評価方法
                    }
                }
                Log.i("title:", title.toString())
                Log.i("assignments:", assignments.toString())
                Log.i("credit:", credit.toString())
                Log.i("eval:", eval)


                subject = Subject(
                    title,
                    "担当教員　" + assignments + "\n開講日時　" + timetable + "\n授業形態　" + styleHeading + "\n単位数　　" + credit + "\n" + eval,
                    1,
                    1,
                    subjectView.reviewList[counter++],
                    classNum
                )

            } catch (ex: Exception) {
                Log.d("Exception", ex.toString())
            }
        })

        tr.start()
        tr.join()

        return subject
    }
}