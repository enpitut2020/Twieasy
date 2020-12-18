package com.example.twieasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.tutorial.view.*


class TutorialFragment : Fragment() {

    lateinit var subjectView : SubjectViewModel

    lateinit var vii: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        subjectView = ViewModelProvider(this).get(SubjectViewModel::class.java)
        vii = inflater.inflate(R.layout.fragment_tutorial, container, false)

        vii.okButton.setOnClickListener{
            findNavController().navigate(R.id.action_tutorialFragment_to_flickFragment2)
        }
        return vii
    }

}