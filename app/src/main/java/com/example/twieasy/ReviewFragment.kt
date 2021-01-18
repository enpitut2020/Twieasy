package com.example.twieasy

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginBottom
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.beardedhen.androidbootstrap.BootstrapButton

class ReviewFragment : Fragment() {
    private var ID: Int? = null
    lateinit var vii : View
    lateinit var subjectView : SubjectViewModel
    private lateinit var subjects : ArrayList<Subject>//3つの学類すべての講義番号の配列


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            ID = it.getInt("ID")
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        vii = inflater.inflate(R.layout.fragment_review, container, false)
        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)
        subjects = (subjectView.coinsSubjects + subjectView.mastSubjects + subjectView.klisSubjects) as ArrayList<Subject>//3つの学類すべての講義番号の配列
        jumpToReview()
        return vii
    }

    private fun jumpToReview() {
        //getTextFromWeb("https://kdb.tsukuba.ac.jp/syllabi/2020/BC12893/jpn/") //??????
        createReview(
            subjects[ID!! - 1].name,
            subjects[ID!! - 1].info,
            subjects[ID!! - 1].easiness
        )
        val tb : TestWeb3? = TestWeb3(requireActivity(), subjects)//val tb : TestWeb3? = TestWeb3(requireActivity(), subjectView.subjects)
        tb?.getReview(ID!! - 1)
        for (i in 1 until subjects[ID!! - 1].reviews.size) {
            val r: TextView = TextView(context)

            val key: String = "toridge"
            val liReview = subjects[ID!! - 1].reviews as MutableList
            val review = liReview[i]
            val decryptionReview: String? = EncryptionWrapper.decryptAES128(key, review)

            //Log.i("review", decryptionReview)
            r.text = if(decryptionReview != null) decryptionReview else ""
            r.ellipsize = TextUtils.TruncateAt.END
            r.height = ViewGroup.LayoutParams.WRAP_CONTENT

            r.setPaddingRelative(30, 30, 30, 30)
            r.setBackgroundColor(Color.parseColor("#f5f5f5"))
            r.maxLines = 6
            r.setBackgroundResource(R.drawable.review_space)

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