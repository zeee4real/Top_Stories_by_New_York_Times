package com.zayd.topstoriesbynewyorktimes.hometab

import com.zayd.topstoriesbynewyorktimes.models.HomeResponseModel
import com.zayd.topstoriesbynewyorktimes.service.ApiService
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomeRepository(private val apiService: ApiService) {

    interface HomeRepositoryInterface {
        fun onApiSuccess(response: HomeResponseModel)
        fun onError()
    }

    fun getTopStoriesApiCall(callback: HomeRepositoryInterface){
        apiService.getTopStories("fy3PHkbvcm66bHr56JPFbXJ5cjA2gdEy")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<HomeResponseModel>{
                override fun onSubscribe(d: Disposable) {

                }

                override fun onSuccess(t: HomeResponseModel) {
                    callback.onApiSuccess(t)
                }

                override fun onError(e: Throwable) {
                    callback.onError()
                }

            })
    }
}