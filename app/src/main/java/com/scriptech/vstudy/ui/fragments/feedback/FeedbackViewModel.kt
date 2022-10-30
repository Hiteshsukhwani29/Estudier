package com.scriptech.vstudy.ui.fragments.feedback

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.model.Videos
import com.scriptech.vstudy.repository.*
import com.scriptech.vstudy.ui.activities.mainActivity2.Main2Activity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FeedbackViewModel(val userRepository: UserRepository) :
    ViewModel() {

    suspend fun getUserInfo(uid: String) = userRepository.getUserInfo(uid)

    suspend fun contributeToEstudier() {
        userRepository.contributeToEstudier()
    }

}