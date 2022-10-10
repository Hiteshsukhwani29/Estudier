package com.scriptech.vstudy.ui.fragments.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.scriptech.vstudy.repository.NotesRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel(val notesRepository: NotesRepository) : ViewModel() {

}