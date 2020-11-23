package com.zayd.topstoriesbynewyorktimes.service

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat.getSystemService
import com.zayd.topstoriesbynewyorktimes.utils.AppUtils
import com.zayd.topstoriesbynewyorktimes.utils.Extensions.isNetworkAvailable
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class NetworkClient private constructor(context: Context) {

    private val retrofit: Retrofit
    private val okHttpClient: OkHttpClient.Builder = OkHttpClient.Builder()

    companion object {
        private val BASE_URL = "https://api.nytimes.com/"
        private val key = "fy3PHkbvcm66bHr56JPFbXJ5cjA2gdEy"
        private var networkClient: NetworkClient? = null

        @Synchronized
        private fun getInstance(context: Context): NetworkClient {
            if (networkClient == null) {
                networkClient = NetworkClient(context)
            }
            return networkClient as NetworkClient
        }

        @Synchronized
        fun get(context: Context): ApiService? {
            return if (context.isNetworkAvailable())
                getInstance(context).retrofit.create(ApiService::class.java)
            else {
                AppUtils.showNetworkErrorDialog(context)
                null
            }
        }
    }

    init {
        okHttpClient.addInterceptor { chain ->
            val request: Request = chain.request()
            chain.proceed(request)
        }
        retrofit = Retrofit.Builder()

            .baseUrl(BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}