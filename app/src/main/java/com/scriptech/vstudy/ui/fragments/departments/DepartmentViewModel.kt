package com.scriptech.vstudy.ui.fragments.departments

import androidx.lifecycle.ViewModel
import com.scriptech.vstudy.repository.NotesRepository

class DepartmentViewModel(val notesRepository: NotesRepository) :
    ViewModel() {

    fun getAllSubjects(deptLink: String) = notesRepository.getAllSubjects(deptLink)

}