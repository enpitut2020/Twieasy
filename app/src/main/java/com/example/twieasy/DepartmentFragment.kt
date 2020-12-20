package com.example.twieasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_select_department.view.*


class DepartmentFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_select_department, container, false)
        view.departmentButton.setOnClickListener{
            findNavController().navigate(R.id.action_departmentFragment_to_tutorialFragment)
        }
        // Inflate the layout for this fragment
        return view
    }


}