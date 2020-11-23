package com.zayd.topstoriesbynewyorktimes.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import java.text.SimpleDateFormat
import java.util.*

object Extensions {
    fun String.toDate(
        dateInFormat: String = "yyyy-MM-dd'T'HH:mm:ssXXX",
        dateOutFormat: String = "dd MMM yyyy, hh:mm a"
    ): String {
        return try {
            val inputFormatter = SimpleDateFormat(dateInFormat, Locale.getDefault())
            val outputFormatter = SimpleDateFormat(dateOutFormat, Locale.getDefault())

            val date: Date = inputFormatter.parse(this)!!
            outputFormatter.format(date)
        } catch (ex: Exception) {
            ""
        }
    }

    fun Context.isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            val nwInfo = connectivityManager.activeNetworkInfo ?: return false
            return nwInfo.isConnected
        }
    }

}