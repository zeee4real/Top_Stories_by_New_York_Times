package com.zayd.topstoriesbynewyorktimes.repository.bookmarks

import androidx.lifecycle.LiveData

class BookmarksRepository(private val bookmarksDao: BookmarksDao) {

    fun insertBookmark(bookmarks: Bookmarks): Long =
        bookmarksDao.insertIntoBookmarksTable(bookmarks)

    fun getAllBookmarks(): LiveData<List<Bookmarks>> =
        bookmarksDao.getAllBookmarks()

    fun deleteBookmark(url: String): Int =
        bookmarksDao.deleteFromBookmarksTable(url)
}