package com.example.vivah.di

import androidx.room.Room
import com.example.vivah.db.VivahDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {

    single {
        val builder = Room.databaseBuilder(androidContext(), VivahDatabase::class.java, "vivahDb")
        builder.build()
    }

    single { get<VivahDatabase>().matchesDao }
}