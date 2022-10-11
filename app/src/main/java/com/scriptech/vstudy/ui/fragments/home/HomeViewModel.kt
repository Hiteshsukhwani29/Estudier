package com.scriptech.vstudy.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.NotesRepository

class HomeViewModel(val booksRepository: BooksRepository) : ViewModel() {

    var _trendingBooks: MutableLiveData<List<Books>>? = null
    var trendingBooks: LiveData<List<Books>>? = null
        get() = _trendingBooks

    fun getAllTrendingBooks() {
        _trendingBooks?.postValue(booksRepository.trendingBooksList)
    }

}