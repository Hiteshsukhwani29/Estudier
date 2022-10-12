package com.scriptech.vstudy.ui.fragments.departments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.NotesRepository
import com.scriptech.vstudy.repository.VideosRepository

class DepartmentViewModelFactory(val notesRepository: NotesRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DepartmentViewModel::class.java)) {
            return DepartmentViewModel(notesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}