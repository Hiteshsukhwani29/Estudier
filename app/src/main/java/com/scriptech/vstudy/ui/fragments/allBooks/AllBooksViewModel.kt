package com.scriptech.vstudy.ui.fragments.allBooks

import androidx.lifecycle.ViewModel
import com.scriptech.vstudy.repository.BooksRepository

class AllBooksViewModel(val booksRepository: BooksRepository) :
    ViewModel() {

    fun getAllTrendingBooks() = booksRepository.getAllTrendingBooks()

    fun getAllBooks() = booksRepository.getAllBooks()

}