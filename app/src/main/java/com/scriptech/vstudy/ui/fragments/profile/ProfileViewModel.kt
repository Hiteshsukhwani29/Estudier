package com.scriptech.vstudy.ui.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.scriptech.vstudy.model.UserInfo
import com.scriptech.vstudy.repository.UserRepository

class ProfileViewModel(var userRepository: UserRepository) :
    ViewModel() {

    var _userInfo: MutableLiveData<UserInfo>? = null
    val userInfo: LiveData<UserInfo>?
        get() = _userInfo

    suspend fun getUserInfo(uid: String) {
        _userInfo?.postValue(userRepository.getUserInfo(uid))
    }

}