package com.scriptech.vstudy.ui.fragments.intros

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.scriptech.vstudy.R
import com.scriptech.vstudy.databinding.FragIntro2Binding

class Intro2: Fragment() {

    private var _binding: FragIntro2Binding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragIntro2Binding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().window.statusBarColor = requireActivity().getColor(R.color.white)
                requireActivity().window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }

        binding.intro2Next.setOnClickListener {
            it.findNavController().navigate(Intro2Directions.actionIntro2ToIntro3())
        }
        return root
    }

}