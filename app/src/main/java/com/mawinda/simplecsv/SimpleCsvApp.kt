package com.mawinda.simplecsv

import android.app.Application
import timber.log.Timber

class SimpleCsvApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}