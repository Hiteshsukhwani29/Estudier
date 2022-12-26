package com.scriptech.vstudy.ui.fragments.home

import androidx.lifecycle.ViewModel
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.VideosRepository

class HomeViewModel(val booksRepository: BooksRepository, val videosRepository: VideosRepository) :
    ViewModel() {

    fun getAllTrendingBooks() = booksRepository.getAllTrendingBooks()

    fun getAllTrendingVideos() = videosRepository.getAllTrendingVideos()

}