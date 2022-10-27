package com.scriptech.vstudy.ui.fragments.subject

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.scriptech.vstudy.adapters.BooksAdapter
import com.scriptech.vstudy.adapters.NotesAdapter
import com.scriptech.vstudy.adapters.VideosAdapter
import com.scriptech.vstudy.database.BooksDatabase
import com.scriptech.vstudy.database.NotesDatabase
import com.scriptech.vstudy.database.VideosDatabase
import com.scriptech.vstudy.databinding.FragSubjectsBinding
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.NotesRepository
import com.scriptech.vstudy.repository.VideosRepository
import kotlinx.coroutines.launch

class Subject : Fragment() {
    private var _binding: FragSubjectsBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: SubjectViewModel

    private lateinit var videoAdapter: VideosAdapter
    private lateinit var bookAdapter: BooksAdapter
    private lateinit var notesAdapter: NotesAdapter

    private val args by navArgs<SubjectArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragSubjectsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requireActivity().window.statusBarColor = Color.parseColor("#20111111")
                requireActivity().window.decorView.systemUiVisibility = (
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            }
        }

        val notesRepository = NotesRepository(NotesDatabase(requireActivity()))
        val booksRepository = BooksRepository(BooksDatabase(requireActivity()))
        val videosRepository = VideosRepository(VideosDatabase(requireActivity()))

        val viewModelFactory =
            SubjectViewModelFactory(notesRepository, booksRepository, videosRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[SubjectViewModel::class.java]

        notesAdapter = NotesAdapter()
        bookAdapter = BooksAdapter(4)
        videoAdapter = VideosAdapter(4)

        binding.rvSubNotes.apply {
            adapter = notesAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity)
        }

        binding.rvSubBooks.apply {
            adapter = bookAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        binding.rvSubVideos.apply {
            adapter = videoAdapter
            hasFixedSize()
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                notesAdapter.differ.submitList(viewModel.getSubjectNotes(args.subLink))
                bookAdapter.differ.submitList(viewModel.getSubjectBooks(args.subLink))
                videoAdapter.differ.submitList(viewModel.getSubjectVideos(args.subLink))
            }
        }

        return root
    }
}