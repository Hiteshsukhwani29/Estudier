package com.scriptech.vstudy.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.model.Videos
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.NotesRepository
import com.scriptech.vstudy.repository.VideosRepository

class HomeViewModel(val booksRepository: BooksRepository, val videosRepository: VideosRepository) : ViewModel() {

    var _trendingBooks: MutableLiveData<List<Books>>? = null
    var trendingBooks: LiveData<List<Books>>? = null
        get() = _trendingBooks

    var _trendingVideos: MutableLiveData<List<Videos>>? = null
    var trendingVideos: LiveData<List<Videos>>? = null
        get() = _trendingVideos

    fun getAllTrendingBooks() {
        booksRepository.getAllTrendingBooks()
        _trendingBooks?.postValue(booksRepository.trendingBooksList)
    }
    fun getAllTrendingVideos() {
        videosRepository.getAllTrendingVideos()
        _trendingVideos?.postValue(videosRepository.trendingVideosList)
    }

}