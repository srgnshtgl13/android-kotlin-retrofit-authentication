package com.example.training.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.training.MainActivity

import com.example.training.R
import com.example.training.sharedpreff.Prefs


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    lateinit var prefs:Prefs
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        prefs = Prefs(requireContext())
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        textView.setOnClickListener {  Toast.makeText(activity, prefs.userEmail, Toast.LENGTH_SHORT).show()}


        return root
    }
}
