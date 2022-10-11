package com.scriptech.vstudy.ui.fragments.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.NotesRepository
import com.scriptech.vstudy.repository.VideosRepository

class HomeViewModelFactory( val booksRepository: BooksRepository, val videosRepository: VideosRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(booksRepository, videosRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}