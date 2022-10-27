package com.scriptech.vstudy.ui.fragments.subject

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.NotesRepository
import com.scriptech.vstudy.repository.VideosRepository

class SubjectViewModelFactory(val notesRepository: NotesRepository, val booksRepository: BooksRepository, val videosRepository: VideosRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubjectViewModel::class.java)) {
            return SubjectViewModel(notesRepository, booksRepository, videosRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}