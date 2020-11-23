package com.zayd.topstoriesbynewyorktimes

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.zayd.topstoriesbynewyorktimes.service.ApiService
import java.lang.IllegalArgumentException

class MainActivityViewModelFactory(private val apiService: ApiService, private val context: Context)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainActivityViewModel::class.java))
            return MainActivityViewModel(apiService, context) as T
        throw IllegalArgumentException("Unknown View Model Class")
    }
}