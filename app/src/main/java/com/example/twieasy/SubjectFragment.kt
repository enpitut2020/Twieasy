package com.example.twieasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController


class SubjectFragment : Fragment() {

    lateinit var subjectView : SubjectViewModel


    private val subject: MutableCollection<Button> = mutableListOf()//講義ボタンのリスト

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        subjectView = ViewModelProvider(this).get(SubjectViewModel::class.java)

        val view =  inflater.inflate(R.layout.fragment_subject, container, false)
        val rl : LinearLayout = view.findViewById(R.id.fragment_subject_linear)
        for (i in 1..subjectView.size()) {//i = sizeも処理される
            val r: Button = Button(requireActivity())
            r.id = i
            r.text = subjectView.subjectsInfo[i - 1].name

            if (subjectView.subjectsInfo[i - 1].easiness >= 50)
                r.setBackgroundResource(R.drawable.frame_style_finalraku)
            else
                r.setBackgroundResource(R.drawable.frame_style_finalpien)
            val r2: TextView = TextView(context)
            r2.text = "楽単率 " + subjectView.subjectsInfo[i-1].easiness.toString() + "%"

            subject.add(r)
            rl.addView(r)
            rl.addView(r2)
            r.setOnClickListener { //jumpToReview(r.id)
                val bundle : Bundle = Bundle()
                bundle.putInt("ID",i)

                findNavController().navigate(R.id.action_subjectFragment_to_reviewFragment,bundle)
            }
        }

        return view
    }


}