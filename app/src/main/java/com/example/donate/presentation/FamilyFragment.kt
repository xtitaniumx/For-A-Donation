package com.example.donate.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.donate.R
import com.example.donate.databinding.FragmentFamilyBinding

class FamilyFragment : Fragment() {
    private lateinit var binding: FragmentFamilyBinding

    companion object {
        @JvmStatic
        fun newInstance() = FamilyFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFamilyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() = with(binding) {

    }
}