package com.example.twieasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_select_department.view.*
import com.beardedhen.androidbootstrap.BootstrapButton


class DepartmentFragment : Fragment() {

    var button: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            button = it.getInt("buttonNum")
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {/*戻るボタンが押されても遷移しない*/}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view =  inflater.inflate(R.layout.fragment_select_department, container, false)

        val okButton: BootstrapButton = view.findViewById(R.id.departmentButton)
        okButton.setOnClickListener{
            val bundle:Bundle = Bundle()
            bundle.putInt("buttonNum", button!!)

            val spinner = view.findViewById<Spinner>(R.id.spDepartment)
            bundle.putString("department", spinner.selectedItem as String)

            findNavController().navigate(R.id.action_departmentFragment_to_loadFragment,bundle)
        }
        // Inflate the layout for this fragment
        return view
    }


}