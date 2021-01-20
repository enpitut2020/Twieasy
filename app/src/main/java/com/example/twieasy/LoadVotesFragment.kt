package com.example.twieasy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController


class LoadVotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_load_votes, container, false)
        val subjectView = ViewModelProvider(requireActivity()).get(SubjectViewModel::class.java)

        while(!subjectView.votesLoaded){
            continue
        }

        findNavController().navigate(R.id.action_loadVotesFragment_to_subjectFragment)

        return view
    }
}