package com.example.twieasy

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_subject.view.*

class SubjectFragment : Fragment() {

    lateinit var subjectView : SubjectViewModel

    private lateinit var subjects : ArrayList<Subject>//3つの学類すべての講義番号の配列
    lateinit var subjectList : MutableList<MutableMap<String, Any>>
    lateinit var result : MutableList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        times = 0//※これがないと戻るボタンでアプリを落とした後講義を読み込まない(表示されない)
        // Inflate the layout for this fragment
        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)
        subjects = (subjectView.coinsSubjects + subjectView.mastSubjects + subjectView.klisSubjects) as ArrayList<Subject>

        val view =  inflater.inflate(R.layout.fragment_subject, container, false)

        subjectList = mutableListOf<MutableMap<String, Any>>()
        for (i in 1..subjects.size){
            var ratio = subjects[i-1].eVotes.toFloat() * 100.0F / (subjects[i-1].dVotes.toFloat() + subjects[i-1].eVotes.toFloat())
            if (subjects[i-1].eVotes == 0 && subjects[i-1].dVotes == 0) ratio = 50.0F
            Log.i("eVotes", subjects[i-1].eVotes.toString())
            Log.i("dVotes", subjects[i-1].dVotes.toString())
            var sub : MutableMap<String, Any> = mutableMapOf("name" to subjects[i - 1].name, "easiness" to ratio.toInt().toString())
            subjectList.add(sub)
        }

        val from = arrayOf("name", "easiness")
        val to = intArrayOf(R.id.lv_subject_name, R.id.lv_easiness)
        var adapter = SimpleAdapter(view.context, subjectList, R.layout.lv_subject, from, to)
        adapter.viewBinder = CustomViewBinder()
        val lvSubjects = view.findViewById<ListView>(R.id.lvSubjects)
        lvSubjects.adapter = adapter
        lvSubjects.onItemClickListener = ListItemClickListener()


        // 検索予測処理
        // 科目名のList
        var searchList: MutableList<String> = subjects.map{
            it.name
        }.toMutableList()

        // SearchViewのイベントリスナ
        view.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            // ユーザによって文字列が変更されたときに呼ばれる
            override fun onQueryTextChange(newText: String): Boolean {
                // text changed
                Log.d("change",newText)
                if(!newText.isEmpty()) {
                    val regex = Regex(newText)
                    result = searchList.filter { regex.containsMatchIn(it) }.toMutableList()
                    // 検索結果に文字が格納されているとき
                    if (!result.isEmpty()) {
                        val resultMap = mutableListOf<MutableMap<String, Any>>()

                        for (i in 1..result.size){
                            val subjectName = result[i - 1]
                            var index = 0

                            for(i in 1..subjects.size) {
                                if (subjects[i-1].name == subjectName) {
                                    index = i - 1
                                    break
                                }
                            }
                            var ratio = subjects[i-1].eVotes.toFloat() * 100.0F / (subjects[i-1].dVotes.toFloat() + subjects[i-1].eVotes.toFloat())
                            if (subjects[i-1].eVotes == 0 && subjects[i-1].dVotes == 0) ratio = 50.0F
                            Log.i("eVotes", subjects[i-1].eVotes.toString())
                            Log.i("dVotes", subjects[i-1].dVotes.toString())
                            var res : MutableMap<String, Any> = mutableMapOf("name" to subjects[index].name, "easiness" to ratio.toInt().toString())
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
        requireActivity().onBackPressedDispatcher.addCallback(this) { activity?.finish() }
        return view
    }

    private inner class CustomViewBinder : SimpleAdapter.ViewBinder {
        override fun setViewValue(view: View, data: Any, textRepresentation: String): Boolean {
            when(view.id) {
                R.id.lv_easiness -> {
                    val easiness = (data as String).toInt()
                    val textView = view as TextView
                    textView.text = "楽単率" + data.toString() + "%"
                    if (easiness >= 50) {
                        textView.setBackgroundColor(Color.parseColor("#00acFF"))
                        textView.setBackgroundResource(R.drawable.frame_style_finalraku)
                    } else {
                        textView.setBackgroundColor(Color.parseColor("#f28a2f"))
                        textView.setBackgroundResource(R.drawable.frame_style_finalpien)
                    }
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
            for(i in 1..subjects.size) {
                if (subjects[i-1].name == subjectName) {
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