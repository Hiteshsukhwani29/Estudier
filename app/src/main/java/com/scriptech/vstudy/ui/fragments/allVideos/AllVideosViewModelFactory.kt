package com.scriptech.vstudy.ui.fragments.allVideos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.scriptech.vstudy.repository.VideosRepository

class AllVideosViewModelFactory(val videosRepository: VideosRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AllVideosViewModel::class.java)) {
            return AllVideosViewModel(videosRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}