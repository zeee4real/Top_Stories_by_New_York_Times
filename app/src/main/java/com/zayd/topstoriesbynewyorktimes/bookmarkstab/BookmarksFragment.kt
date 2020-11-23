package com.zayd.topstoriesbynewyorktimes.bookmarkstab

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.zayd.topstoriesbynewyorktimes.MainActivity
import com.zayd.topstoriesbynewyorktimes.MainActivityViewModel
import com.zayd.topstoriesbynewyorktimes.R
import com.zayd.topstoriesbynewyorktimes.WebViewActivity
import com.zayd.topstoriesbynewyorktimes.utils.AppUtils
import com.zayd.topstoriesbynewyorktimes.utils.Constants

class BookmarksFragment : Fragment(R.layout.fragment_bookmarks) {

    private lateinit var bookmarksAdapter: BookmarksAdapter
    private var commonViewModel: MainActivityViewModel? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val defaultTextView: TextView = view.findViewById(R.id.noBookmarks)
        val gridView: GridView = view.findViewById(R.id.gridView)
        commonViewModel = MainActivity.getViewModel(this)

        context?.let { ctx ->
            commonViewModel?.let { viewModel ->
                viewModel.bookmarks?.observe(viewLifecycleOwner, {
                    AppUtils.setViewVisible(defaultTextView, it.isEmpty())

                    bookmarksAdapter = BookmarksAdapter(ctx, object :
                        BookmarksAdapter.BookmarksAdapterInterface {
                        override fun onItemClick(url: String?) {
                            url?.let {
                                startActivity(
                                    Intent(ctx, WebViewActivity::class.java).putExtra(
                                        Constants.EXTRA_WEB_VIEW_URL,
                                        url
                                    )
                                )
                            }
                        }

                        override fun onBookmarkClick(url: String?) {
                            url?.let {
                                viewModel.deleteBookmark(url)
                            }
                        }
                    })
                    gridView.adapter = bookmarksAdapter
                    bookmarksAdapter.setData(it)
                })
            }
        }
    }


}