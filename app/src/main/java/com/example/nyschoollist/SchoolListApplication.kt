package com.example.nyschoollist

import android.app.Application
import com.example.nyschoollist.data.AppContainer
import com.example.nyschoollist.data.DefaultAppContainer

class SchoolListApplication : Application() {
    /** AppContainer instance used by the rest of classes to obtain dependencies */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}