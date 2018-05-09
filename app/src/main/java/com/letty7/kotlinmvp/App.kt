package com.letty7.kotlinmvp

import android.app.Application

class App : Application() {

    companion object {

        lateinit var sContext: App

    }

    override fun onCreate() {
        super.onCreate()
        sContext = this
    }

}