package com.scriptech.vstudy.ui.fragments.allVideos

import androidx.lifecycle.ViewModel
import com.scriptech.vstudy.repository.VideosRepository

class AllVideosViewModel(val videosRepository: VideosRepository) :
    ViewModel() {

    fun getAllTrendingVideos() = videosRepository.getAllTrendingVideos()

    fun getAllVideos() = videosRepository.getAllVideos()

}