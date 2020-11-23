package com.zayd.topstoriesbynewyorktimes

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.zayd.topstoriesbynewyorktimes.bookmarkstab.BookmarksFragment
import com.zayd.topstoriesbynewyorktimes.hometab.HomeFragment
import com.zayd.topstoriesbynewyorktimes.service.NetworkClient
import com.zayd.topstoriesbynewyorktimes.utils.FragmentUtils

class MainActivity : AppCompatActivity() {
    private var fragmentUtils = FragmentUtils()
    private val homeFragment = HomeFragment()
    private val bookmarksFragment = BookmarksFragment()
    private lateinit var homeIV: ImageView
    private lateinit var bookmarksIV: ImageView

    companion object {
        private var viewModel: MainActivityViewModel? = null

        fun getViewModel(fragment: Fragment): MainActivityViewModel? {
            if (fragment is HomeFragment || fragment is BookmarksFragment)
                return viewModel
            return null
        }
    }

    private fun setupViewModel() {
        try {
            NetworkClient.get(this)?.let {
                val viewModelFactory = MainActivityViewModelFactory(it, this)
                viewModel = ViewModelProvider(this, viewModelFactory)
                    .get(MainActivityViewModel::class.java)
            }
        } catch (ex: IllegalArgumentException) {
            Log.e("HomeFragment", "ViewModel Class Error", ex)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViewModel()
        homeIV = findViewById(R.id.homeIV)
        bookmarksIV = findViewById(R.id.bookmarksIV)

        homeIV.setOnClickListener {
            loadFragment(homeFragment, bookmarksFragment)
        }
        bookmarksIV.setOnClickListener {
            loadFragment(bookmarksFragment, homeFragment)
        }

        loadFragment(homeFragment)
    }

    private fun loadFragment(fragment: Fragment, activeFragment: Fragment? = null) {
        if (fragment is BookmarksFragment) {
            fragmentUtils.addFragment(this, bookmarksFragment, activeFragment, addToStack = false)
            homeIV.setColorFilter(
                ContextCompat.getColor(this, R.color.black),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            bookmarksIV.setColorFilter(
                ContextCompat.getColor(this, R.color.secondaryText),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        } else if (fragment is HomeFragment) {
            fragmentUtils.addFragment(this, homeFragment, activeFragment, addToStack = false)
            homeIV.setColorFilter(
                ContextCompat.getColor(this, R.color.secondaryText),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            bookmarksIV.setColorFilter(
                ContextCompat.getColor(this, R.color.black),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
        }
    }


}