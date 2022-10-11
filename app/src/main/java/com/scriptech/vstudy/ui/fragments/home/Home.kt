package com.scriptech.vstudy.ui.fragments.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.scriptech.vstudy.adapters.BooksAdapter
import com.scriptech.vstudy.adapters.videoAdapter
import com.scriptech.vstudy.database.NotesDatabase
import com.scriptech.vstudy.databinding.FragHomeBinding
import com.scriptech.vstudy.repository.NotesRepository

class Home : Fragment() {
    private var _binding: FragHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    private lateinit var videoAdapter: videoAdapter
    private lateinit var bookAdapter: BooksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().window.statusBarColor = Color.parseColor("#20111111")
                requireActivity().window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            }
        }

        val newsRepository = NotesRepository(NotesDatabase(requireActivity()))

        val viewModelFactory = HomeViewModelFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        bookAdapter = BooksAdapter(viewModel)

        binding.homeRvAllbooks.apply {
            adapter = bookAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        viewModel._trendingBooks?.observe(viewLifecycleOwner) {
            Log.d("final result search", it.toString())
            bookAdapter.differ.submitList(it)
        }

        return root
    }
}