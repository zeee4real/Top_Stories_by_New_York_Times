package com.zayd.topstoriesbynewyorktimes.hometab

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import com.zayd.topstoriesbynewyorktimes.R
import com.zayd.topstoriesbynewyorktimes.models.ResultResponse
import com.zayd.topstoriesbynewyorktimes.utils.Extensions.toDate

class HomeAdapter(
    private val homeAdapterInterface: HomeAdapterInterface
) : RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {

    interface HomeAdapterInterface {
        fun onBookmarkClick(item: ResultResponse, bookmarked: Boolean)
        fun onItemClick(item: ResultResponse)
    }

    private lateinit var items: List<ResultResponse>
    private var bookmarkItems: List<String>? = null

    fun setData(items: List<ResultResponse>) {
        this.items = items
    }

    fun bookmarkData(items: List<String>) {
        this.bookmarkItems = items
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        HomeViewHolder.from(parent)

    private fun getItem(position: Int) = items[position]

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)

        bookmarkItems?.let { list ->
            if (list.contains(currentItem.url)) {
                holder.bookmarkIV.setBackgroundResource(R.drawable.ic_bookmark_selected)
                items[position].bookmarked = true
            } else {
                holder.bookmarkIV.setBackgroundResource(R.drawable.ic_bookmark_unselected)
                items[position].bookmarked = false
            }
        }

        holder.bookmarkIV.setOnClickListener {
            val bookmarked = !currentItem.bookmarked
            homeAdapterInterface.onBookmarkClick(currentItem, bookmarked)
            items[position].bookmarked = bookmarked
            if (bookmarked) {
                holder.bookmarkIV.setBackgroundResource(R.drawable.ic_bookmark_selected)
            } else {
                holder.bookmarkIV.setBackgroundResource(R.drawable.ic_bookmark_unselected)
            }
        }

        holder.listItem.setOnClickListener {
            homeAdapterInterface.onItemClick(currentItem)
        }
    }

    class HomeViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val titleTV: TextView = itemView.findViewById(R.id.title)
        private val dateTV: TextView = itemView.findViewById(R.id.date)
        private val imageIV: ImageView = itemView.findViewById(R.id.image)
        val bookmarkIV: ImageView = itemView.findViewById(R.id.bookmark)
        val listItem: MaterialCardView = itemView.findViewById(R.id.list_item)

        companion object {
            fun from(parent: ViewGroup) =
                HomeViewHolder(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.list_item_home, parent, false)
                )
        }

        fun bind(item: ResultResponse?) {
            item?.let {
                titleTV.text = item.title
                dateTV.text = item.created_date.toDate()
                val url = item.multimedia?.get(3)?.url?:"" //using 3rd image only
                if(url.isEmpty()){
                  imageIV.setBackgroundResource(R.drawable.ic_broken_image)
                } else {
                    Picasso.get().load(url).error(R.drawable.ic_broken_image).into(imageIV)
                }
                if (item.bookmarked)
                    bookmarkIV.setBackgroundResource(R.drawable.ic_bookmark_selected)
                else
                    bookmarkIV.setBackgroundResource(R.drawable.ic_bookmark_unselected)
            }
        }
    }

    override fun getItemCount() =
        items.size
}
