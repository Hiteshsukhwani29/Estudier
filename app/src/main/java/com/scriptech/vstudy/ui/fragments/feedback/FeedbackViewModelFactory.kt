package com.scriptech.vstudy.ui.fragments.feedback

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.UserRepository
import com.scriptech.vstudy.repository.VideosRepository

class FeedbackViewModelFactory(val userRepository: UserRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FeedbackViewModel::class.java)) {
            return FeedbackViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}