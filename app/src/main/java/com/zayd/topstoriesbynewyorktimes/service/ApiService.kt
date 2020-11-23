package com.zayd.topstoriesbynewyorktimes.service

import com.zayd.topstoriesbynewyorktimes.models.HomeResponseModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/svc/topstories/v2/home.json")
    fun getTopStories(
        @Query("api-key") key:String)
            : Single<HomeResponseModel>
}