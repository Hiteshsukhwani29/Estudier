package com.scriptech.vstudy.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.scriptech.vstudy.database.NotesDatabase
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.model.DepartmentModel

class NotesRepository(private val database: NotesDatabase) {

    val db = FirebaseFirestore.getInstance()

    lateinit var SubjectsList: MutableList<DepartmentModel>

    fun getAllSubjects(dept_link: String): MutableList<DepartmentModel> {
        db.collection(dept_link)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    SubjectsList.add(document.toObject(DepartmentModel::class.java))
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Trending Books", "Error getting documents: ", exception)
            }
        return SubjectsList
    }

}