package com.scriptech.vstudy.ui.fragments.departments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.scriptech.vstudy.adapters.BooksAdapter
import com.scriptech.vstudy.adapters.DepartmentAdapter
import com.scriptech.vstudy.database.NotesDatabase
import com.scriptech.vstudy.databinding.FragDepartmentsBinding
import com.scriptech.vstudy.databinding.FragHomeBinding
import com.scriptech.vstudy.repository.NotesRepository
import com.scriptech.vstudy.ui.fragments.home.HomeViewModel
import com.scriptech.vstudy.ui.fragments.home.HomeViewModelFactory

class Departments: Fragment() {
    private var _binding: FragDepartmentsBinding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: DepartmentViewModel

    private lateinit var deptAdapter: DepartmentAdapter

    private val args by navArgs<DepartmentsArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragDepartmentsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val notesRepository = NotesRepository(NotesDatabase(requireActivity()))

        val viewModelFactory = DepartmentViewModelFactory(notesRepository)
        viewModel = ViewModelProvider(this, viewModelFactory)[DepartmentViewModel::class.java]

        binding.deptName.text = args.deptName

        viewModel.getAllSubjects(args.deptLink)

        binding.recyclerProfile.apply {
            adapter = deptAdapter
            layoutManager = LinearLayoutManager(activity)
        }

        viewModel.allSubjects?.observe(viewLifecycleOwner) {
            Log.d("final result search", it.toString())
            deptAdapter.differ.submitList(it)
        }

        return root
    }

}