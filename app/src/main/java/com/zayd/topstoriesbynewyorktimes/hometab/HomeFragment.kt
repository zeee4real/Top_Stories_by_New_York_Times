package com.zayd.topstoriesbynewyorktimes.hometab

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.zayd.topstoriesbynewyorktimes.*
import com.zayd.topstoriesbynewyorktimes.models.ResultResponse
import com.zayd.topstoriesbynewyorktimes.utils.Constants.EXTRA_ABSTRACT
import com.zayd.topstoriesbynewyorktimes.utils.Constants.EXTRA_IMAGE_URL
import com.zayd.topstoriesbynewyorktimes.utils.Constants.EXTRA_TITLE
import com.zayd.topstoriesbynewyorktimes.utils.Constants.EXTRA_WEB_VIEW_URL

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var commonViewModel: MainActivityViewModel? = null
    private var homeAdapter: HomeAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commonViewModel = MainActivity.getViewModel(this)
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerView)
        val refreshLayout: SwipeRefreshLayout = view.findViewById(R.id.refreshLayout)

        commonViewModel?.let { viewModel ->
            refreshLayout.setOnRefreshListener {
                viewModel.getTopStories()
            }
            viewModel.getTopStories()

            viewModel.topStoriesLiveData.observe(viewLifecycleOwner, {
                if (refreshLayout.isRefreshing) {
                    refreshLayout.isRefreshing = false
                }
                recyclerView.apply {
                    homeAdapter = HomeAdapter(object : HomeAdapter.HomeAdapterInterface {
                        override fun onBookmarkClick(item: ResultResponse, bookmarked: Boolean) {
                            viewModel.bookmarkClickOperation(item, bookmarked)
                        }

                        override fun onItemClick(item: ResultResponse) {
                            val intent = Intent(context, ArticleDetailsActivity::class.java).apply {
                                putExtra(EXTRA_TITLE, item.title)
                                putExtra(EXTRA_WEB_VIEW_URL, item.url)
                                putExtra(EXTRA_ABSTRACT, item.abstract)
                                putExtra(EXTRA_IMAGE_URL, item.multimedia?.get(3)?.url ?: "")
                            }
                            startActivity(intent)


//                            startActivity(
//                                Intent(context, ArticleDetailsActivity::class.java)
//                                        Intent (context, WebViewActivity::class.java).putExtra(
//                                EXTRA_WEB_VIEW_URL,
//                                url
//                            )
//
//                            )
//
                        }
                    })
                    adapter = homeAdapter
                    homeAdapter?.setData(it.results)
                }

                viewModel.bookmarksUrls?.observe(viewLifecycleOwner, { list ->
                    list?.let {
                        homeAdapter?.bookmarkData(it)
                    }
                    homeAdapter?.notifyDataSetChanged()
                })
            })
        }
    }
}