package com.zayd.topstoriesbynewyorktimes

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.zayd.topstoriesbynewyorktimes.hometab.HomeRepository
import com.zayd.topstoriesbynewyorktimes.models.HomeResponseModel
import com.zayd.topstoriesbynewyorktimes.models.ResultResponse
import com.zayd.topstoriesbynewyorktimes.repository.AppDatabase
import com.zayd.topstoriesbynewyorktimes.repository.bookmarks.Bookmarks
import com.zayd.topstoriesbynewyorktimes.repository.bookmarks.BookmarksRepository
import com.zayd.topstoriesbynewyorktimes.service.ApiService
import kotlinx.coroutines.*

class MainActivityViewModel(private val apiService: ApiService, context: Context) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private val bookmarksRepository: BookmarksRepository
    var bookmarks: LiveData<List<Bookmarks>>? = null

    init {
        val databaseInstance: AppDatabase = AppDatabase.getInstance(context)!!
        bookmarksRepository = BookmarksRepository(databaseInstance.bookmarksDao)
        bookmarks = bookmarksRepository.getAllBookmarks()
    }

    val bookmarksUrls: LiveData<List<String>?>? = bookmarks?.let { bookmarks ->
        Transformations.switchMap(bookmarks) { list ->
            val tempList = ArrayList<String>()
            list.forEach {
                tempList.add(it.url)
            }
            val data = MutableLiveData<List<String>>()
            data.value = tempList
            data
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private var _topStoriesLiveData = MutableLiveData<HomeResponseModel>()
    val topStoriesLiveData: LiveData<HomeResponseModel>
        get() = _topStoriesLiveData

    fun getTopStories() {
        HomeRepository(apiService).getTopStoriesApiCall(object :
            HomeRepository.HomeRepositoryInterface {
            override fun onApiSuccess(response: HomeResponseModel) {
                _topStoriesLiveData.value = response
            }

            override fun onError() {
                Log.d("done", "error")
            }
        })
    }

    fun bookmarkClickOperation(item: ResultResponse, bookmarked: Boolean) {
        val data = getExtractedData(item)
        if (bookmarked) {
            uiScope.launch {
                withContext(Dispatchers.IO) {
                    bookmarksRepository.insertBookmark(data)
                }
            }
        } else {
            deleteBookmark(item.url)
        }
    }

    fun deleteBookmark(url: String) {
        uiScope.launch {
            withContext(Dispatchers.IO) {
                bookmarksRepository.deleteBookmark(url)
            }
        }
    }

    private fun getExtractedData(item: ResultResponse): Bookmarks {
        return Bookmarks(
            0,
            item.title,
            item.created_date,
            item.url,
            item.multimedia?.get(3)?.url ?: ""
        )
    }
}