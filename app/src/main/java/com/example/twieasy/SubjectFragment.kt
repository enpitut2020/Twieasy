package com.example.twieasy

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_subject.view.*


class SubjectFragment : Fragment() {

    lateinit var subjectView : SubjectViewModel
    lateinit var listView: ListView


    private val subject: MutableCollection<Button> = mutableListOf()//講義ボタンのリスト

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        // adapterを作成します
    ): View? {
        // Inflate the layout for this fragment
        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)

        val view =  inflater.inflate(R.layout.fragment_subject, container, false)
        val rl : LinearLayout = view.findViewById(R.id.fragment_subject_linear)

        listView = ListView(requireContext())
        // 科目名の配列
        // リストではなく配列にしているのはListViewのadapterの引数の型がArrayだから
        var subjectArray: Array<String> = subjectView.subjects.map{
            it.name
        }.toTypedArray()

        // 予測結果を格納

        // SearchViewのイベントリスナ
        view.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // ユーザによって文字列が変更されたときに呼ばれる
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                Log.d("change",newText)
                if(!newText.isEmpty()) {
                    val regex = Regex(newText)
                    val result = subjectArray.filter { regex.containsMatchIn(it) }
                    // 検索結果にものが格納されているとき
                    if (!result.isEmpty()) {
                        result.map { Log.d("input", it) }

                        val arrayAdapter = ArrayAdapter(
                            requireContext(),
                            R.layout.fragment_subject,
                            result
                        )
                        listView.adapter = arrayAdapter
                    }
                }
                return false
            }
            // ユーザがクエリを送信(検索ボタンをクリック)したときに呼ばれる
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                return false
            }
        })


            for (i in 1..subjectView.subjects.size) {//i = sizeも処理される
            val r: Button = Button(requireActivity())
            r.id = i
            r.text = subjectView.subjects[i - 1].name

            if (subjectView.subjects[i - 1].easiness >= 50)
                r.setBackgroundResource(R.drawable.frame_style_finalraku)
            else
                r.setBackgroundResource(R.drawable.frame_style_finalpien)
            val r2: TextView = TextView(context)
            r2.text = "楽単率 " + subjectView.subjects[i-1].easiness.toString() + "%"

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