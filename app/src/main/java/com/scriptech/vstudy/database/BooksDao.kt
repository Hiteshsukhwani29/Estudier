package com.scriptech.vstudy.database

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.*
import com.scriptech.vstudy.model.Books
import com.scriptech.vstudy.model.Notes

@Dao
interface BooksDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBook(book: Books): Long

    @Query("SELECT * FROM books")
    fun getSavedBooks(): LiveData<List<Books>>

    @Query("SELECT EXISTS (SELECT 1 FROM books WHERE id = :id)")
    fun isAlreadySaved(id: Int) {
        Log.d("already saved",id.toString())
    }

    @Query("SELECT * FROM books WHERE book_name LIKE :query")
    suspend fun searchSavedBooks(query: String): List<Books>

    @Delete
    suspend fun deleteNote(book: Books)
}