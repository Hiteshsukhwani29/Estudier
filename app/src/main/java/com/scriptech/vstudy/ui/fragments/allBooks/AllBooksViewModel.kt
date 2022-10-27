package com.scriptech.vstudy.ui.fragments.allBooks

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.model.Videos
import com.scriptech.vstudy.repository.*
import com.scriptech.vstudy.ui.activities.mainActivity2.Main2Activity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AllBooksViewModel(val booksRepository: BooksRepository) :
    ViewModel() {

    suspend fun getAllTrendingBooks() = booksRepository.getAllTrendingBooks()

    suspend fun getAllBooks() = booksRepository.getAllBooks()

}