package com.example.vivah

import com.example.vivah.di.DEFAULT
import com.example.vivah.di.IO
import com.example.vivah.repository.MatchesRepository
import com.example.vivah.ui.matches.MatchesViewModel
import io.mockk.mockkClass
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineScope
import org.koin.core.qualifier.named
import org.koin.dsl.module

@ExperimentalCoroutinesApi
val module = module {

    //mock repository
    single { mockkClass(MatchesRepository::class) }


    //test coroutine dispatcher
    single(named(IO)) { CoroutineScope(TestCoroutineScope().coroutineContext) }
    single(named(DEFAULT)) { CoroutineScope(TestCoroutineScope().coroutineContext) }

    // viewModel
    single { MatchesViewModel(get()) }

}