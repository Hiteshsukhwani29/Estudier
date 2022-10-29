package com.scriptech.vstudy.ui.fragments.notifications

import androidx.lifecycle.ViewModel
import com.scriptech.vstudy.repository.UserRepository

class NotificationsViewModel(val userRepository: UserRepository) :
    ViewModel() {

    suspend fun getAllNotifications() = userRepository.getAllNotifications()

}