package com.zayd.topstoriesbynewyorktimes.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

object AppUtils {

    fun setViewVisible(view: View, visible: Boolean) {
        if (visible)
            view.visibility = View.VISIBLE
        else
            view.visibility = View.GONE
    }

    fun showNetworkErrorDialog(context: Context) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Network Error")
            .setMessage("Check your internet connection")
            .setPositiveButton("OK", { _, _ ->
            })
        alertDialog.show()
    }
}