package com.scriptech.vstudy.ui.fragments.allBooks

//import com.scriptech.vstudy.repository.MyCallback
//import com.scriptech.vstudy.repository.trendingBooksList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.scriptech.vstudy.adapters.AllBooksAdapter
import com.scriptech.vstudy.adapters.BooksAdapter
import com.scriptech.vstudy.database.BooksDatabase
import com.scriptech.vstudy.databinding.FragAllbooksBinding
import com.scriptech.vstudy.repository.BooksRepository

class AllBooks : Fragment() {
    private var _binding: FragAllbooksBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: AllBooksViewModel

    private lateinit var booksAdapter: BooksAdapter
    private lateinit var allBooksAdapter: AllBooksAdapter

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
                requireActivity().window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
        }

        val booksRepository = BooksRepository(BooksDatabase(requireActivity()))

        val viewModelFactory = AllBooksViewModelFactory(booksRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AllBooksViewModel::class.java]

        booksAdapter = BooksAdapter(3)
        allBooksAdapter = AllBooksAdapter()

        binding.rvTrendingBook.apply {
            adapter = booksAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvNewBook.apply {
            adapter = allBooksAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity)
        }

        viewModel.getAllBooks().observe(viewLifecycleOwner) {
            allBooksAdapter.differ.submitList(it)
        }

        viewModel.getAllTrendingBooks().observe(viewLifecycleOwner) {
            booksAdapter.differ.submitList(it)
        }

        return root
    }
}