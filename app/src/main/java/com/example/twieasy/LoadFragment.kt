package com.example.twieasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController


class LoadFragment : Fragment() {
   lateinit var subjectView : SubjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_load, container, false)
        subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)

        while(!subjectView.loaded){
            continue
        }

        findNavController().navigate(R.id.action_loadFragment_to_mainFragment)

        /*if(button == 0) {
            val bundle = Bundle()
            bundle.putString("department", department)
            findNavController().navigate(R.id.action_loadFragment_to_tutorialFragment, bundle)
        }
        else if(button == 1)
                findNavController().navigate(R.id.action_loadFragment_to_loginFragment)*/

        return view
    }


}