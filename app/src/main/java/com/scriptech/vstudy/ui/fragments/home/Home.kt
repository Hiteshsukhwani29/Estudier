package com.scriptech.vstudy.ui.fragments.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.scriptech.vstudy.database.NotesDatabase
import com.scriptech.vstudy.databinding.FragHomeBinding
import com.scriptech.vstudy.repository.NotesRepository

class Home : Fragment() {
    private var _binding: FragHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val newsRepository = NotesRepository(NotesDatabase(requireActivity()))

        val viewModelFactory = HomeViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        return root
    }
}