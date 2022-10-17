package com.scriptech.vstudy.ui.fragments.allBooks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.VideosRepository

class AllBooksViewModelFactory(val booksRepository: BooksRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllBooksViewModel::class.java)) {
            return AllBooksViewModel(booksRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}