package com.example.twieasy

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_post.view.*


class PostFragment : Fragment() {

    lateinit var  vii : View
    lateinit var subjectView : SubjectViewModel
    private lateinit var subjects : ArrayList<Subject>

    private var ID: Int? = null
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
        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)
        subjects = (subjectView.coinsSubjects + subjectView.mastSubjects + subjectView.klisSubjects) as ArrayList<Subject>
        // Inflate the layout for this fragment
        vii = inflater.inflate(R.layout.fragment_post, container, false)

        val editText = vii.findViewById<EditText>(R.id.reviewContent)

        //改行禁止！
        editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable) {
                for (i in s.length - 1 downTo 0) {
                    if (s[i] == '\n') {
                        s.delete(i, i + 1)
                        return
                    }
                }
            }
        })


        vii.postButton.setOnClickListener {
            post()
        }

        return vii
    }

    private fun post() {
        val postContent: TextView = vii.findViewById(R.id.reviewContent)
        val postStr: String = postContent.text.toString()

        if(postStr.isBlank())
            Toast.makeText(vii.context, "レビューを入力してください", Toast.LENGTH_SHORT).show()
        else{
            subjectView.reviewList[ID!! - 1].add(postStr)
            // 暗号化
            val key: String = "toridge"
            val encryptionPostStr: String? = EncryptionWrapper.encryptAES128(key, postStr)
            if(encryptionPostStr != null && !encryptionPostStr?.isEmpty()) {
                subjectView.reviewList[ID!! - 1].add(encryptionPostStr)
                val tb: TestWeb3? = TestWeb3(requireActivity(), null)
                tb?.sendReview(subjects[ID!!-1].classNum , encryptionPostStr)
                findNavController().navigateUp()
            }
        }
    }
}