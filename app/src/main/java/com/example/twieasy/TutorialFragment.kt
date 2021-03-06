package com.example.twieasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.beardedhen.androidbootstrap.BootstrapButton
import kotlinx.android.synthetic.main.tutorial.view.*



class TutorialFragment : Fragment() {

    lateinit var subjectView : SubjectViewModel
    lateinit var vii: View
    private var department: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            department = it.getString("department")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        subjectView = ViewModelProvider(this).get(SubjectViewModel::class.java)
        vii = inflater.inflate(R.layout.fragment_tutorial, container, false)

        val okButton: BootstrapButton = vii.findViewById(R.id.okButton)

        okButton.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("department", department)
            findNavController().navigate(R.id.action_tutorialFragment_to_flickFragment2, bundle)
        }

        requireActivity().onBackPressedDispatcher.addCallback(this) {/*戻るボタンが押されても遷移しない*/}

        return vii
    }

}