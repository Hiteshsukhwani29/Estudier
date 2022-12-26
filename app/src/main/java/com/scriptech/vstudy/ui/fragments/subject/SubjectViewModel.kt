package com.scriptech.vstudy.ui.fragments.subject

import androidx.lifecycle.ViewModel
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.NotesRepository
import com.scriptech.vstudy.repository.VideosRepository

class SubjectViewModel(val notesRepository: NotesRepository, val booksRepository: BooksRepository, val videosRepository: VideosRepository) :
    ViewModel() {

    fun getSubjectNotes(subLink: String) = notesRepository.getSubjectNotes(subLink)

    fun getSubjectBooks(subLink: String) = booksRepository.getSubjectBooks(subLink)

    fun getSubjectVideos(subLink: String) = videosRepository.getSubjectVideos(subLink)


}