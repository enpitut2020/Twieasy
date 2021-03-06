package com.example.twieasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController


class LoadFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var button: Int? = null
    private lateinit var department: String
    lateinit var subjectView : SubjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            button = it.getInt("buttonNum")
            if(button == 0) {
                department = it.getString("department")
            }
        }
    }

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

        if(button == 0) {
            val bundle = Bundle()
            bundle.putString("department", department)
            findNavController().navigate(R.id.action_loadFragment_to_tutorialFragment, bundle)
        }
        else if(button == 1)
                findNavController().navigate(R.id.action_loadFragment_to_loginFragment)

        return view
    }


}