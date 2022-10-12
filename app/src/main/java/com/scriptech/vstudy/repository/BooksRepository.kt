package com.scriptech.vstudy.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.scriptech.vstudy.database.BooksDatabase
import com.scriptech.vstudy.database.NotesDatabase
import com.scriptech.vstudy.model.Books

class BooksRepository(private val database: BooksDatabase) {

    val db = FirebaseFirestore.getInstance()

    lateinit var trendingBooksList: MutableList<Books>

    fun getAllTrendingBooks(): MutableList<Books> {
        db.collection("Books_trending")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    trendingBooksList.add(document.toObject(Books::class.java))
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Trending Books", "Error getting documents: ", exception)
            }
        return trendingBooksList
    }

}