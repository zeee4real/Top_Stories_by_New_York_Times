package com.zayd.topstoriesbynewyorktimes.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zayd.topstoriesbynewyorktimes.repository.bookmarks.Bookmarks
import com.zayd.topstoriesbynewyorktimes.repository.bookmarks.BookmarksDao
import com.zayd.topstoriesbynewyorktimes.utils.Constants

@Database(entities = [Bookmarks::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract val bookmarksDao: BookmarksDao

    companion object {

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {
            if (instance == null) {
                synchronized(this) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java, Constants.DATABASE_NAME
                    )
                        .build()
                }
            }
            return instance
        }
    }
}