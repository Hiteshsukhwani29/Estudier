package com.scriptech.vstudy.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.scriptech.vstudy.database.BooksDatabase
import com.scriptech.vstudy.database.NotesDatabase
import com.scriptech.vstudy.database.VideosDatabase
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.model.Videos
import kotlinx.coroutines.tasks.await

class VideosRepository(private val database: VideosDatabase) {

    val db = FirebaseFirestore.getInstance()

    var _trendingVideosList: MutableList<Videos> = mutableListOf()

    suspend fun getAllTrendingVideos(): MutableList<Videos> {
        val result = db.collection("Videos_trending")
            .get()
            .await()
        for (document in result) {
            document.toObject(Videos::class.java).let{
                _trendingVideosList.add(it)
            }
        }
        return _trendingVideosList
    }

}