package com.example.twieasy

import android.app.Application
import com.beardedhen.androidbootstrap.TypefaceProvider

class BootStrap : Application() {
    override fun onCreate() {
        super.onCreate()
        TypefaceProvider.registerDefaultIconSets()
    }
}