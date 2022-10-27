package com.scriptech.vstudy.ui.fragments.home

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
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
import com.scriptech.vstudy.databinding.FragHomeBinding
import com.scriptech.vstudy.model.sliderModel
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.VideosRepository
import kotlinx.coroutines.launch
import kotlin.math.abs

class Home : Fragment() {
    private var _binding: FragHomeBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    private lateinit var videoAdapter: VideosAdapter
    private lateinit var bookAdapter: BooksAdapter

    private var sliderItems: MutableList<sliderModel> = mutableListOf()

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

        addDepartmentCards()

//        val notesRepository = NotesRepository(NotesDatabase(requireActivity()))
        val booksRepository = BooksRepository(BooksDatabase(requireActivity()))
        val videosRepository = VideosRepository(VideosDatabase(requireActivity()))

        val viewModelFactory = HomeViewModelFactory(booksRepository, videosRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]

        bookAdapter = BooksAdapter()
        videoAdapter = VideosAdapter()

        binding.homeRvAllbooks.apply {
            adapter = bookAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        }

        binding.homeRvAllvideos.apply {
            adapter = videoAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL ,false)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                bookAdapter.differ.submitList(viewModel.getAllTrendingBooks())
                videoAdapter.differ.submitList(viewModel.getAllTrendingVideos())
            }
        }

        sliderItems.add(sliderModel("https://firebasestorage.googleapis.com/v0/b/edu-project-2423e.appspot.com/o/IMP_FEATURED%2Fpicture1.jpg?alt=media"))
        sliderItems.add(sliderModel("https://firebasestorage.googleapis.com/v0/b/edu-project-2423e.appspot.com/o/IMP_FEATURED%2Fpicture2.jpg?alt=media"))
        sliderItems.add(sliderModel("https://firebasestorage.googleapis.com/v0/b/edu-project-2423e.appspot.com/o/IMP_FEATURED%2Fpicture3.jpg?alt=media"))
        sliderItems.add(sliderModel("https://firebasestorage.googleapis.com/v0/b/edu-project-2423e.appspot.com/o/IMP_FEATURED%2Fpicture4.jpg?alt=media"))


        binding.viewPagerImageSlider.adapter =
            sliderAdapter(sliderItems, binding.viewPagerImageSlider)
        binding.viewPagerImageSlider.clipToPadding = false
        binding.viewPagerImageSlider.clipChildren = false
        binding.viewPagerImageSlider.offscreenPageLimit = 3
        binding.viewPagerImageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.55f + r * 0.45f
        }

        binding.viewPagerImageSlider.setPageTransformer(compositePageTransformer)

        binding.viewPagerImageSlider.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                val sliderHandler = Handler()
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, 5000) // slide duration 5 seconds
            }
        })

        binding.videosViewall.setOnClickListener {
            it.findNavController().navigate(HomeDirections.actionHome2ToAllVideos())
        }

        binding.booksViewall.setOnClickListener {
            it.findNavController().navigate(HomeDirections.actionHome2ToAllBooks())
        }

        return root
    }

    private fun addDepartmentCards() {
        binding.cardCS.deptName.text = "CS"
        binding.cardCS.deptImg.setImageResource(R.drawable.comp)
        binding.cardCS.deptCard.setOnClickListener {
            it.findNavController()
                .navigate(HomeDirections.actionHome2ToDepartments2("Computer Science", "cs"))
        }

        binding.cardIT.deptName.text = "IT"
        binding.cardIT.deptImg.setImageResource(R.drawable.it)
        binding.cardIT.deptCard.setOnClickListener {
            it.findNavController()
                .navigate(HomeDirections.actionHome2ToDepartments2("Information Technology", "it"))
        }

        binding.cardENTC.deptName.text = "ENTC"
        binding.cardENTC.deptImg.setImageResource(R.drawable.entc)
        binding.cardENTC.deptCard.setOnClickListener {
            it.findNavController().navigate(
                HomeDirections.actionHome2ToDepartments2(
                    "Electronics & Telecommunications",
                    "entc"
                )
            )
        }

        binding.cardEE.deptName.text = "EE"
        binding.cardEE.deptImg.setImageResource(R.drawable.elec)
        binding.cardEE.deptCard.setOnClickListener {
            it.findNavController()
                .navigate(HomeDirections.actionHome2ToDepartments2("Electrical Engineering", "ee"))
        }

        binding.cardME.deptName.text = "ME"
        binding.cardME.deptImg.setImageResource(R.drawable.mech)
        binding.cardME.deptCard.setOnClickListener {
            it.findNavController()
                .navigate(HomeDirections.actionHome2ToDepartments2("Mechanical Engineering", "me"))
        }

        binding.cardCSBS.deptName.text = "CSBS"
        binding.cardCSBS.deptImg.setImageResource(R.drawable.csbs)
        binding.cardCSBS.deptCard.setOnClickListener {
            it.findNavController().navigate(
                HomeDirections.actionHome2ToDepartments2(
                    "Computer Science with\nBusiness Studies",
                    "csbs"
                )
            )
        }
    }

    private val sliderRunnable =
        Runnable {
            binding.viewPagerImageSlider.currentItem = binding.viewPagerImageSlider.currentItem + 1
        }
}