package com.example.twieasy

import android.annotation.SuppressLint
import android.graphics.Color
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

    private val subject: MutableCollection<Button> = mutableListOf()//講義ボタンのリスト
    lateinit var subjectList : MutableList<MutableMap<String, Any>>
    lateinit var result : MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)

        val view =  inflater.inflate(R.layout.fragment_subject, container, false)

        subjectList = mutableListOf<MutableMap<String, Any>>()
        for (i in 1..subjectView.subjects.size){
            var sub : MutableMap<String, Any> = mutableMapOf("name" to subjectView.subjects[i - 1].name, "difficulty" to (100 - subjectView.subjects[i - 1].easiness).toString(), "easiness" to subjectView.subjects[i - 1].easiness.toString())
            subjectList.add(sub)
        }

        val from = arrayOf("name", "difficulty", "easiness")
        val to = intArrayOf(R.id.lv_subject_name, R.id.lv_difficulty, R.id.lv_easiness)
        var adapter = SimpleAdapter(view.context, subjectList, R.layout.lv_subject, from, to)
        adapter.viewBinder = CustomViewBinder()
        val lvSubjects = view.findViewById<ListView>(R.id.lvSubjects)
        lvSubjects.adapter = adapter
        lvSubjects.onItemClickListener = ListItemClickListener()


        // 検索予測処理
        // 科目名のList
        var searchList: MutableList<String> = subjectView.subjects.map{
            it.name
        }.toMutableList()

        // SearchViewのイベントリスナ
        view.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // ユーザによって文字列が変更されたときに呼ばれる
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                Log.d("change",newText)
                //検索欄に入力があるとき
                if(!newText.isEmpty()) {
                    val regex = Regex(newText)
                    result = searchList.filter { regex.containsMatchIn(it) }.toMutableList()
                    // 検索に科目が引っ掛かったとき
                    if (!result.isEmpty()) {
                        val resultMap = mutableListOf<MutableMap<String, Any>>()

                        for (i in 1..result.size){
                            val subjectName = result[i - 1]
                            var index = 0

                            for(i in 1..subjectView.subjects.size) {
                                if (subjectView.subjects[i-1].name == subjectName) {
                                    index = i - 1
                                    break
                                }
                            }

                            var res : MutableMap<String, Any> = mutableMapOf("name" to subjectView.subjects[index].name, "difficulty" to (100 - subjectView.subjects[index].easiness).toString(), "easiness" to subjectView.subjects[index].easiness.toString())
                            resultMap.add(res)
                        }

                        adapter = SimpleAdapter(view.context, resultMap, R.layout.lv_subject, from, to)
                        adapter.viewBinder = CustomViewBinder()
                        lvSubjects.adapter = adapter
                        lvSubjects.onItemClickListener = SearchListItemClickListener()
                    }
                    // 一致する検索結果が存在しないとき
                    else {
                        var resultEmpty = mutableListOf<MutableMap<String, Any>>()
                        adapter = SimpleAdapter(view.context, resultEmpty, R.layout.lv_subject, from, to)
                        lvSubjects.adapter = adapter
                    }
                }
                // 検索欄に入力がないとき
                else {
                    adapter = SimpleAdapter(view.context, subjectList, R.layout.lv_subject, from, to)
                    adapter.viewBinder = CustomViewBinder()
                    lvSubjects.adapter = adapter
                    lvSubjects.onItemClickListener = ListItemClickListener()
                }
                return false
            }
            // ユーザがクエリを送信(検索ボタンをクリック)したときに呼ばれる
            override fun onQueryTextSubmit(query: String): Boolean {
                // submit button pressed
                return false
            }
        })
        return view
    }

    private inner class CustomViewBinder : SimpleAdapter.ViewBinder {
        @SuppressLint("SetTextI18n")
        override fun setViewValue(view: View, data: Any, textRepresentation: String): Boolean {
            when(view.id) {
                R.id.lv_subject_name -> {
                    Log.d("data1", data as String)
                    val textView = view as TextView
                    textView.text = textRepresentation

                    return true//これがないと.textにdataの値を上書きされる(ここ↑で書き込んでいることを知らせている)
                }

                R.id.lv_difficulty -> {
                    val difficulty = (data as String).toInt()
                    val difficultyFloat = difficulty.toFloat()
                    Log.d("data2", "$difficulty/$difficultyFloat")
                    val textView = view as TextView
                    (textView.layoutParams as LinearLayout.LayoutParams).weight = difficultyFloat
                    if (difficulty < 20)
                        textView.text = ""
                    else
                        textView.text = "落$textRepresentation%"

                    return true
                }

                R.id.lv_easiness -> {
                    val easiness = (data as String).toInt()
                    val easinessFloat = easiness.toFloat()
                    Log.d("data3", "$easiness/$easinessFloat")
                    val textView = view as TextView
                    (textView.layoutParams as LinearLayout.LayoutParams).weight = easinessFloat
                    if (easiness < 20)
                        textView.text = ""
                    else
                        textView.text = "楽$textRepresentation%"

                    return true
                }
            }
            return false
        }
    }

    private inner class ListItemClickListener : AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val bundle : Bundle = Bundle()
            bundle.putInt("ID",position + 1)
            findNavController().navigate(R.id.action_subjectFragment_to_reviewFragment,bundle)
        }
    }

    private inner class SearchListItemClickListener : AdapterView.OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val subjectName = result[position]
            var index = 0
            for(i in 1..subjectView.subjects.size) {
                if (subjectView.subjects[i-1].name == subjectName) {
                    index = i
                    break
                }
            }
            val bundle : Bundle = Bundle()
            bundle.putInt("ID",index)
            findNavController().navigate(R.id.action_subjectFragment_to_reviewFragment,bundle)
        }
    }
}