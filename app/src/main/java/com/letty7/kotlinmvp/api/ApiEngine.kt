package com.letty7.kotlinmvp.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiEngine private constructor() {

    private var mApiService: ApiService

    init {

        val client: OkHttpClient = OkHttpClient.Builder()
                .connectTimeout(6, TimeUnit.SECONDS)
                .build()

        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        mApiService = retrofit.create(ApiService::class.java)
    }

    companion object {

        private val INSTANCE: ApiEngine by lazy { ApiEngine() }

        fun getApiService(): ApiService {
            return INSTANCE.mApiService
        }

    }

}