package com.scriptech.vstudy.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.scriptech.vstudy.database.BooksDatabase
import com.scriptech.vstudy.model.Books
import kotlinx.coroutines.tasks.await

class BooksRepository(private val database: BooksDatabase) {

    val db = FirebaseFirestore.getInstance()

    var _trendingBooksList: MutableList<Books> = mutableListOf()

    suspend fun getAllTrendingBooks(): MutableList<Books> {
        val result = db.collection("Books_trending")
            .get()
            .await()
        for (document in result) {
            document.toObject(Books::class.java).let{
                _trendingBooksList.add(it)
            }
        }
        return _trendingBooksList
    }

}