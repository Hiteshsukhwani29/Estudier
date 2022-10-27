package com.scriptech.vstudy.ui.fragments.allVideos

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.scriptech.vstudy.adapters.BooksAdapter
import com.scriptech.vstudy.adapters.VideosAdapter
import com.scriptech.vstudy.database.BooksDatabase
import com.scriptech.vstudy.database.VideosDatabase
import com.scriptech.vstudy.databinding.FragAllbooksBinding
import com.scriptech.vstudy.databinding.FragAllvideosBinding
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.VideosRepository
//import com.scriptech.vstudy.repository.MyCallback
//import com.scriptech.vstudy.repository.trendingBooksList
import kotlinx.coroutines.launch

class AllVideos : Fragment() {
    private var _binding: FragAllvideosBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: AllVideosViewModel

    private lateinit var videosAdapter: VideosAdapter
    private lateinit var allVideosAdapter: VideosAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragAllvideosBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().window.statusBarColor = Color.WHITE
                requireActivity().window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
        }

        val videosRepository = VideosRepository(VideosDatabase(requireActivity()))

        val viewModelFactory = AllVideosViewModelFactory(videosRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AllVideosViewModel::class.java]

        videosAdapter = VideosAdapter(2)
        allVideosAdapter = VideosAdapter(3)

        binding.rvTrendingVideo.apply {
            adapter = videosAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        }

        binding.rvNewVideo.apply {
            adapter = allVideosAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                videosAdapter.differ.submitList(viewModel.getAllTrendingVideos())
                allVideosAdapter.differ.submitList(viewModel.getAllVideos())
            }
        }
        return root
    }
}