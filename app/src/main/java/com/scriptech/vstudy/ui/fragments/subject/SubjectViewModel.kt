package com.scriptech.vstudy.ui.fragments.subject

import androidx.lifecycle.ViewModel
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.model.Notes
import com.scriptech.vstudy.model.Videos
import com.scriptech.vstudy.repository.BooksRepository
import com.scriptech.vstudy.repository.NotesRepository
import com.scriptech.vstudy.repository.VideosRepository

class SubjectViewModel(val notesRepository: NotesRepository, val booksRepository: BooksRepository, val videosRepository: VideosRepository) :
    ViewModel() {

    suspend fun getSubjectNotes(subLink: String): MutableList<Notes> = notesRepository.getSubjectNotes(subLink)

    suspend fun getSubjectBooks(subLink: String): MutableList<Books> = booksRepository.getSubjectBooks(subLink)

    suspend fun getSubjectVideos(subLink: String): MutableList<Videos> = videosRepository.getSubjectVideos(subLink)

}