package com.scriptech.vstudy.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.scriptech.vstudy.model.Notes

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note: Notes): Long

    @Query("SELECT * FROM notes")
    fun getSavedNotes(): LiveData<List<Notes>>

    @Query("SELECT EXISTS (SELECT 1 FROM notes WHERE id = :id)")
    fun isAlreadySaved(id: Int) {
        Log.d("already saved",id.toString())
    }

    @Query("SELECT * FROM notes WHERE name LIKE :query")
    suspend fun searchSavedNotes(query: String): List<Notes>

    @Delete
    suspend fun deleteNote(note: Notes)
}