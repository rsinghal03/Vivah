package com.example.vivah.di

import com.example.vivah.repository.MatchesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { MatchesRepository(get(), get()) }
}