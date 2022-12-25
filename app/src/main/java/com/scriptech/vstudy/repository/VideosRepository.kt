package com.scriptech.vstudy.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.scriptech.vstudy.database.VideosDatabase
import com.scriptech.vstudy.model.Videos

class VideosRepository(private val database: VideosDatabase) {

    val db = FirebaseFirestore.getInstance()

    fun getAllVideos(): LiveData<List<Videos>> {
        val items = MutableLiveData<List<Videos>>()
        db.collection("Videos_all")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                val itemsList = mutableListOf<Videos>()
                for (doc in snapshot!!) {
                    val item = doc.toObject(Videos::class.java)
                    itemsList.add(item)
                }
                items.value = itemsList
            }
        return items
    }

    fun getAllTrendingVideos(): LiveData<List<Videos>> {
        val items = MutableLiveData<List<Videos>>()
        db.collection("Videos_trending")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                val itemsList = mutableListOf<Videos>()
                for (doc in snapshot!!) {
                    val item = doc.toObject(Videos::class.java)
                    itemsList.add(item)
                }
                items.value = itemsList
            }
        return items
    }

    fun getSubjectVideos(sub_link: String): LiveData<List<Videos>> {
        val items = MutableLiveData<List<Videos>>()
        db.collection(sub_link+"_videos")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                val itemsList = mutableListOf<Videos>()
                for (doc in snapshot!!) {
                    val item = doc.toObject(Videos::class.java)
                    itemsList.add(item)
                }
                items.value = itemsList
            }
        return items
    }

}