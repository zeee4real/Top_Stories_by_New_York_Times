package com.zayd.topstoriesbynewyorktimes

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.card.MaterialCardView
import com.squareup.picasso.Picasso
import com.zayd.topstoriesbynewyorktimes.utils.Constants
import com.zayd.topstoriesbynewyorktimes.utils.Constants.EXTRA_ABSTRACT
import com.zayd.topstoriesbynewyorktimes.utils.Constants.EXTRA_IMAGE_URL
import com.zayd.topstoriesbynewyorktimes.utils.Constants.EXTRA_TITLE
import com.zayd.topstoriesbynewyorktimes.utils.Constants.EXTRA_WEB_VIEW_URL

class ArticleDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_details)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        val imageIV: ImageView = findViewById(R.id.imageView)
        val abstractTV: TextView = findViewById(R.id.abstact)
        val titleTV: TextView = findViewById(R.id.title)
        val card: MaterialCardView = findViewById(R.id.card)

        intent?.apply {
            val abstract = getStringExtra(EXTRA_ABSTRACT)
            val title = getStringExtra(EXTRA_TITLE) ?: ""
            titleTV.text = title
            abstractTV.text = abstract
            val imageUrl = getStringExtra(EXTRA_IMAGE_URL) ?: ""
            if (imageUrl.isEmpty()) {
                imageIV.setBackgroundResource(R.drawable.ic_broken_image)
            } else {
                Picasso.get().load(imageUrl).fit().error(R.drawable.ic_broken_image).into(imageIV)
            }

            val url = getStringExtra(EXTRA_WEB_VIEW_URL)
            card.setOnClickListener {
                startActivity(
                    Intent(this@ArticleDetailsActivity, WebViewActivity::class.java).putExtra(
                        Constants.EXTRA_WEB_VIEW_URL,
                        url
                    )
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

}