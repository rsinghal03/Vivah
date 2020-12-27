package com.example.vivah.di

import com.example.vivah.ui.matches.MatchesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel { MatchesViewModel(get()) }
}