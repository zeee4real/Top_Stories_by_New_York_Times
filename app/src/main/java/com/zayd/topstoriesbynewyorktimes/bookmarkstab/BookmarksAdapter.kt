package com.zayd.topstoriesbynewyorktimes.bookmarkstab

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.zayd.topstoriesbynewyorktimes.R
import com.zayd.topstoriesbynewyorktimes.repository.bookmarks.Bookmarks
import com.zayd.topstoriesbynewyorktimes.utils.Extensions.toDate

class BookmarksAdapter(
    private val context: Context,
    private val bookmarksAdapterInterface: BookmarksAdapterInterface
) : BaseAdapter() {

    private var layoutInflater: LayoutInflater? = null
    private var items: List<Bookmarks>? = null
    private lateinit var imageView: ImageView
    private lateinit var titleTV: TextView
    private lateinit var dateTV: TextView
    private lateinit var bookmarkSelection: ImageView

    interface BookmarksAdapterInterface {
        fun onItemClick(url: String?)
        fun onBookmarkClick(url: String?)
    }

    fun setData(items: List<Bookmarks>) {
        this.items = items
    }

    override fun getCount(): Int {
        items?.let {
            return it.size
        }
        return 0
    }

    override fun getItem(position: Int): Bookmarks? {
        items?.let {
            return it[position]
        }
        return null
    }

    override fun getItemId(p0: Int) = 0L

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (layoutInflater == null) {
            layoutInflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        if (view == null) {
            layoutInflater?.let {
                view = it.inflate(R.layout.list_item_bookmark, parent, false)
            }
        }
        view?.let { myView ->
            imageView = myView.findViewById(R.id.imageView)
            titleTV = myView.findViewById(R.id.title)
            dateTV = myView.findViewById(R.id.date)
            bookmarkSelection = myView.findViewById(R.id.bookmarkSelection)
            val currentItem = getItem(position)
            updateData(currentItem)
            myView.setOnClickListener {
                bookmarksAdapterInterface.onItemClick(currentItem?.url)
            }
            bookmarkSelection.setOnClickListener {
                bookmarksAdapterInterface.onBookmarkClick(currentItem?.url)
            }
        }
        return view!!
    }

    private fun updateData(item: Bookmarks?) {
        item?.let {
            titleTV.text = it.title
            dateTV.text = it.createdDate.toDate()
            val url = it.imageUrl
            Picasso.get().load(url).fit().into(imageView)
        }
    }
}