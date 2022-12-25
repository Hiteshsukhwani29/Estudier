package com.scriptech.vstudy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.scriptech.vstudy.database.NotesDatabase
import com.scriptech.vstudy.model.DepartmentModel
import com.scriptech.vstudy.model.Notes


class NotesRepository(private val database: NotesDatabase) {

    val db = FirebaseFirestore.getInstance()

    fun getAllSubjects(deptLink: String): LiveData<List<DepartmentModel>> {
        val items = MutableLiveData<List<DepartmentModel>>()
        db.collection(deptLink)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                val itemsList = mutableListOf<DepartmentModel>()
                for (doc in snapshot!!) {
                    val item = doc.toObject(DepartmentModel::class.java)
                    itemsList.add(item)
                }
                items.value = itemsList
            }
        return items
    }

    fun getSubjectNotes(sub_link: String): LiveData<List<Notes>> {
        val items = MutableLiveData<List<Notes>>()
        db.collection(sub_link)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                val itemsList = mutableListOf<Notes>()
                for (doc in snapshot!!) {
                    val item = doc.toObject(Notes::class.java)
                    itemsList.add(item)
                }
                items.value = itemsList
            }
        return items
    }

}