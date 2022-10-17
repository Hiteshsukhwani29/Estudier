package com.scriptech.vstudy.ui.fragments.allBooks

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.scriptech.vstudy.R
import com.scriptech.vstudy.adapters.BooksAdapter
import com.scriptech.vstudy.adapters.VideosAdapter
import com.scriptech.vstudy.adapters.sliderAdapter
import com.scriptech.vstudy.database.BooksDatabase
import com.scriptech.vstudy.database.VideosDatabase
import com.scriptech.vstudy.databinding.FragAllbooksBinding
import com.scriptech.vstudy.databinding.FragHomeBinding
import com.scriptech.vstudy.model.sliderModel
import com.scriptech.vstudy.repository.BooksRepository
//import com.scriptech.vstudy.repository.MyCallback
import com.scriptech.vstudy.repository.VideosRepository
//import com.scriptech.vstudy.repository.trendingBooksList
import kotlinx.coroutines.launch
import kotlin.math.abs

class AllBooks : Fragment() {
    private var _binding: FragAllbooksBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: AllBooksViewModel

    private lateinit var booksAdapter: BooksAdapter
    private lateinit var allBooksAdapter: BooksAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragAllbooksBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().window.statusBarColor = Color.WHITE
                requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
        }

        val booksRepository = BooksRepository(BooksDatabase(requireActivity()))

        val viewModelFactory = AllBooksViewModelFactory(booksRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AllBooksViewModel::class.java]

        booksAdapter = BooksAdapter()
        allBooksAdapter = BooksAdapter(2)

        binding.rvTrendingBook.apply {
            adapter = booksAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        }

        binding.rvNewBook.apply {
            adapter = allBooksAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                booksAdapter.differ.submitList(viewModel.getAllTrendingBooks())
                allBooksAdapter.differ.submitList(viewModel.getAllBooks())
            }
        }
        return root
    }
}