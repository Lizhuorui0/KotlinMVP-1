package com.letty7.kotlinmvp.api

import com.letty7.kotlinmvp.bean.Gank
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiService {

    companion object {
        const val BASE_URL = "http://gank.io/"
    }

    @GET("api/data/福利/20/{page}")
    fun getData(@Path("page") page: Int): Observable<Gank>

}