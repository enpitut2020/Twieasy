package com.example.twieasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_post.view.*
import com.toridge.kotlintest.EncryptionUtils


class PostFragment : Fragment() {

    lateinit var  vii : View
    lateinit var subjectView : SubjectViewModel

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
        // Inflate the layout for this fragment
        vii = inflater.inflate(R.layout.fragment_post, container, false)
        subjectView = ViewModelProvider(this).get(SubjectViewModel::class.java)

        vii.postButton.setOnClickListener {
            post()
        }
        return vii
    }

    private fun post() {
        val postContent: TextView = vii.findViewById(R.id.reviewContent)
        val postStr: String = postContent.text.toString()

        // 暗号化
        val key: String = "toridge"
        val encryptionPostStr: String? = EncryptionUtils.encryptAES128(key, postStr)
        if(encryptionPostStr != null && !encryptionPostStr?.isEmpty()) {
            subjectView.reviewList[ID!! - 1].add(encryptionPostStr)
            findNavController().navigateUp()
        }
    }

}