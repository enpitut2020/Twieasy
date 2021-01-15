package com.example.twieasy

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.beardedhen.androidbootstrap.BootstrapButton
import com.toridge.kotlintest.EncryptionUtils
import kotlinx.android.synthetic.main.fragment_review.view.*

class ReviewFragment : Fragment() {
    private var ID: Int? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ID = it.getInt("ID")
        }
    }
    lateinit var vii : View
    lateinit var subjectView : SubjectViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        vii = inflater.inflate(R.layout.fragment_review, container, false)
        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)
        jumpToReview()
        return vii
    }

    private fun jumpToReview() {
        //getTextFromWeb("https://kdb.tsukuba.ac.jp/syllabi/2020/BC12893/jpn/") //??????
        createReview(
            subjectView.subjects[ID!! - 1].name,
            subjectView.subjects[ID!! - 1].info,
            subjectView.subjects[ID!! - 1].easiness
        )
        for (i in subjectView.subjects[ID!! - 1].reviews) {
            val r: TextView = TextView(context)

            val key: String = "toridge"
            val review = i
            val decryptionReview: String? = EncryptionUtils.decryptAES128(key, review)

            Log.i("review", decryptionReview)
            r.text = if(decryptionReview != null) decryptionReview else ""
            r.text = decryptionReview
            r.height = 200
            r.setPaddingRelative(30, 30, 30, 30)
            r.setBackgroundColor(Color.parseColor("#f5f5f5"))

            val layout = vii.findViewById<LinearLayout>(R.id.linearLayout)
            layout.addView(r)
        }

        val writeReview: BootstrapButton = vii.findViewById(R.id.writeReview)

        writeReview.setOnClickListener {
            val bundle : Bundle = Bundle()
            bundle.putInt("ID",ID!!)
            findNavController().navigate(R.id.action_reviewFragment_to_postFragment, bundle)
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun createReview(subname: String, subinfo: String, easiness: Int) {//easiness[%] : 楽単度合い
        //"落%"部分生成
        val textView1 = vii.findViewById<TextView>(R.id.difficulty)
        (textView1.getLayoutParams() as LinearLayout.LayoutParams).weight = (100 - easiness.toFloat())
        if (70 < easiness)//文字がはみ出る
            textView1.text = ""
        else//はみ出ない
            textView1.text = "落" + (100 - easiness) + "%"

        //"楽%"部分生成
        val textView2 = vii.findViewById<TextView>(R.id.easiness)
        (textView2.getLayoutParams() as LinearLayout.LayoutParams).weight = easiness.toFloat()
        if (easiness < 30)
            textView2.text = ""
        else
            textView2.text = "楽" + easiness + "%"

        val textview3 = vii.findViewById<TextView>(R.id.subject_name)//講義名変更
        textview3.text = subname
        val textview4 = vii.findViewById<TextView>(R.id.subject_info)//基本情報変更
        textview4.text = subinfo
    }



}