package com.scriptech.vstudy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.scriptech.vstudy.database.BooksDatabase
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.model.Notes
import kotlinx.coroutines.tasks.await

class BooksRepository(private val database: BooksDatabase) {

    val db = FirebaseFirestore.getInstance()

    fun getAllBooks(): LiveData<List<Books>> {
        val items = MutableLiveData<List<Books>>()
        db.collection("Books_all")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                val itemsList = mutableListOf<Books>()
                for (doc in snapshot!!) {
                    val item = doc.toObject(Books::class.java)
                    itemsList.add(item)
                }
                items.value = itemsList
            }
        return items
    }

    fun getAllTrendingBooks(): LiveData<List<Books>> {
        val items = MutableLiveData<List<Books>>()
        db.collection("Books_trending")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                val itemsList = mutableListOf<Books>()
                for (doc in snapshot!!) {
                    val item = doc.toObject(Books::class.java)
                    itemsList.add(item)
                }
                items.value = itemsList
            }
        return items
    }

    fun getSubjectBooks(sub_link: String): LiveData<List<Books>> {
        val items = MutableLiveData<List<Books>>()
        db.collection(sub_link+"_books")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                val itemsList = mutableListOf<Books>()
                for (doc in snapshot!!) {
                    val item = doc.toObject(Books::class.java)
                    itemsList.add(item)
                }
                items.value = itemsList
            }
        return items
    }

}