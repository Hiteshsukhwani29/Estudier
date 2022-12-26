package com.scriptech.vstudy.ui.fragments.feedback

import androidx.lifecycle.ViewModel
import com.scriptech.vstudy.repository.UserRepository

class FeedbackViewModel(val userRepository: UserRepository) :
    ViewModel() {

    suspend fun getUserInfo(uid: String) = userRepository.getUserInfo(uid)

    suspend fun contributeToEstudier() {
        userRepository.contributeToEstudier()
    }

}