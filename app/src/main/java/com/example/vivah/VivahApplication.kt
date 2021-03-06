package com.example.vivah

import android.app.Application
import com.example.vivah.di.applicationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class VivahApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@VivahApplication)
            modules(modules = applicationModule)
        }

        Timber.plant(Timber.DebugTree())
    }
}