package com.scriptech.vstudy.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.scriptech.vstudy.model.Notes
import com.scriptech.vstudy.model.Videos

@Dao
interface VideosDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(videos: Videos): Long

    @Query("SELECT * FROM notes")
    fun getSavedVideo(): LiveData<List<Videos>>

    @Query("SELECT EXISTS (SELECT 1 FROM notes WHERE id = :id)")
    fun isAlreadySaved(id: Int) {
        Log.d("already saved",id.toString())
    }

    @Delete
    suspend fun deleteNote(videos: Videos)
}