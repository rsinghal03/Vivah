package com.example.vivah.di

import com.example.vivah.ui.matches.MatchesViewModel
import org.koin.dsl.module

val viewModelModule = module {

    single { MatchesViewModel(get()) }
}