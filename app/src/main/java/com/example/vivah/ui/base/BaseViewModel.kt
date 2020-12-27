package com.example.vivah.ui.base

import androidx.lifecycle.ViewModel
import com.example.vivah.util.SingleLiveEvent
import kotlinx.coroutines.CoroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named

abstract class BaseViewModel : ViewModel(), KoinComponent {

    val showProgressBar = SingleLiveEvent<Boolean>()

    val ioDispatcher: CoroutineScope by inject(named("IO"))
}