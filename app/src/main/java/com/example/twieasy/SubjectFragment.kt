package com.example.twieasy

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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
        // adapterを作成します
    ): View? {
        // Inflate the layout for this fragment
        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)

        val view =  inflater.inflate(R.layout.fragment_subject, container, false)
        //val rl : LinearLayout = view.findViewById(R.id.fragment_subject_linear)


        val subjectList = mutableListOf<MutableMap<String, Any>>()
        for (i in 1..subjectView.subjects.size){
            var sub : MutableMap<String, Any> = mutableMapOf("name" to subjectView.subjects[i - 1].name, "easiness" to subjectView.subjects[i - 1].easiness.toString())
            subjectList.add(sub)
        }

        val from = arrayOf("name", "easiness")
        val to = intArrayOf(android.R.id.text1, android.R.id.text2)
        var adapter = SimpleAdapter(view.context, subjectList, android.R.layout.simple_list_item_2, from, to)
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
                if(!newText.isEmpty()) {
                    val regex = Regex(newText)
                    val result : MutableList<String> = searchList.filter { regex.containsMatchIn(it) }.toMutableList()
                    // 検索結果にものが格納されているとき
                    if (!result.isEmpty()) {
                        //result.map { Log.d("input", it) }

                        val arrayAdapter = ArrayAdapter(
                            view.context,
                            android.R.layout.simple_list_item_1,
                            result
                        )
                        val listView: ListView = view.findViewById<ListView>(R.id.lvSearch)
                        listView.adapter = arrayAdapter

                        // 講義一覧を消す
                        val subjectListEmpty = mutableListOf<MutableMap<String, Any>>()
                        adapter = SimpleAdapter(view.context, subjectListEmpty, android.R.layout.simple_list_item_2, from, to)
                        lvSubjects.adapter = adapter

                    }
                }
                else {
                    val result: MutableList<String> = mutableListOf()
                    val arrayAdapter = ArrayAdapter(
                        view.context,
                        android.R.layout.simple_list_item_1,
                        result
                    )
                    val listView: ListView = view.findViewById<ListView>(R.id.lvSearch)
                    listView.adapter = arrayAdapter

                    // もし消されてたら復活
                    adapter = SimpleAdapter(view.context, subjectList, android.R.layout.simple_list_item_2, from, to)
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
        override fun setViewValue(view: View, data: Any, textRepresentation: String): Boolean {
            when(view.id) {


                android.R.id.text1 ->
                    Log.d("dataa", data as String)

                android.R.id.text2 -> {
                    Log.d("data", data as String)
                    val easiness = data.toInt()
                    if (easiness >= 50)
                        //view.setBackgroundColor(Color.parseColor("##00acFF"))
                    view.setBackgroundResource(R.drawable.frame_style_finalraku)
                    else
                        //view.setBackgroundColor(Color.parseColor("#f28a2f"))
                    view.setBackgroundResource(R.drawable.frame_style_finalpien)

                    //val r2: TextView = TextView(context)
                    //r2.text = "楽単率 " + subjectView.subjects[i-1].easiness.toString() + "%"
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
}