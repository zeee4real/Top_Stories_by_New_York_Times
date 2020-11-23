package com.zayd.topstoriesbynewyorktimes.repository.bookmarks

import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BookmarksDao {
    @Query("SELECT * FROM bookmarks_table")
    fun getAllBookmarks(): LiveData<List<Bookmarks>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertIntoBookmarksTable(@NonNull item: Bookmarks): Long

    @Query("DELETE FROM bookmarks_table WHERE url =:url")
    fun deleteFromBookmarksTable(@NonNull url: String): Int
}