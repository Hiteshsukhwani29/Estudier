package com.scriptech.vstudy.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.scriptech.vstudy.database.BooksDatabase
import com.scriptech.vstudy.database.NotesDatabase
import com.scriptech.vstudy.database.VideosDatabase
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.model.Videos

class VideosRepository(private val database: VideosDatabase) {

    val db = FirebaseFirestore.getInstance()

    lateinit var trendingVideosList: MutableList<Videos>

    fun getAllTrendingVideos(): MutableList<Videos> {
        db.collection("Videos_trending")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    trendingVideosList.add(document.toObject(Videos::class.java))
                }
            }
            .addOnFailureListener { exception ->
                Log.d("Trending Books", "Error getting documents: ", exception)
            }
        return trendingVideosList
    }

}