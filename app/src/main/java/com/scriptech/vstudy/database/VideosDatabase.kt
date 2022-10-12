package com.scriptech.vstudy.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.scriptech.vstudy.model.Notes
import com.scriptech.vstudy.model.Videos

@Database(entities = [Videos::class], version = 1)
abstract class VideosDatabase : RoomDatabase() {

    abstract fun getVideosDao(): VideosDao

    companion object {
        @Volatile
        private var instance: VideosDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also { instance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                VideosDatabase::class.java,
                "videos_database"
            )
                .fallbackToDestructiveMigration()
                .build()
    }
}