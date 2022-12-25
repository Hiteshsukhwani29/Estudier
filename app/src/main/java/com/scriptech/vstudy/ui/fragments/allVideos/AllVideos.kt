package com.scriptech.vstudy.ui.fragments.allVideos

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
import com.scriptech.vstudy.adapters.VideosAdapter
import com.scriptech.vstudy.database.VideosDatabase
import com.scriptech.vstudy.databinding.FragAllvideosBinding
import com.scriptech.vstudy.repository.VideosRepository

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
                requireActivity().window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
        }

        val videosRepository = VideosRepository(VideosDatabase(requireActivity()))

        val viewModelFactory = AllVideosViewModelFactory(videosRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[AllVideosViewModel::class.java]

        videosAdapter = VideosAdapter()
        allVideosAdapter = VideosAdapter(2)

        binding.rvTrendingVideo.apply {
            adapter = videosAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvNewVideo.apply {
            adapter = allVideosAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity)
        }

        viewModel.getAllTrendingVideos().observe(viewLifecycleOwner) {
            videosAdapter.differ.submitList(it)
        }

        viewModel.getAllVideos().observe(viewLifecycleOwner) {
            allVideosAdapter.differ.submitList(it)
        }

        return root
    }
}