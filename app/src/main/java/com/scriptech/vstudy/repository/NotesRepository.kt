package com.scriptech.vstudy.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.scriptech.vstudy.database.NotesDatabase
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.model.DepartmentModel
import com.scriptech.vstudy.model.Notes
import kotlinx.coroutines.tasks.await

class NotesRepository(private val database: NotesDatabase) {

    val db = FirebaseFirestore.getInstance()

    var _SubjectsList: MutableList<DepartmentModel> = mutableListOf()
    var _NotesList: MutableList<Notes> = mutableListOf()

    suspend fun getAllSubjects(dept_link: String): MutableList<DepartmentModel> {
        val result = db.collection(dept_link)
            .get()
            .await()
        for (document in result) {
            document.toObject(DepartmentModel::class.java).let{
                _SubjectsList.add(it)
            }
        }
        return _SubjectsList
    }

    suspend fun getSubjectNotes(sub_link: String): MutableList<Notes> {
        val result = db.collection(sub_link)
            .get()
            .await()
        for (document in result) {
            document.toObject(Notes::class.java).let{
                _NotesList.add(it)
            }
        }
        return _NotesList
    }

}