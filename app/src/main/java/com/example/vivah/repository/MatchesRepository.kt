package com.example.vivah.repository

import com.example.vivah.db.dao.MatchesDao
import com.example.vivah.db.entity.Matches
import com.example.vivah.networking.VivahApiService
import com.example.vivah.util.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import timber.log.Timber

class MatchesRepository(
    private val vivahApiService: VivahApiService,
    private val matchesDao: MatchesDao,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    val flow = MutableSharedFlow<Resource<List<Matches>?>>()

    fun getMatches() {
        coroutineScope.launch { refresh() }
        coroutineScope.launch {
            matchesDao.getDistinctLiveListOfMatches().collect {
                Timber.d("flow collect")
                if (it?.isEmpty() == true) {
                    Timber.d("flow loading")
                    flow.emit(Resource.Loading(null))
                } else {
                    Timber.d("flow success")
                    flow.emit(Resource.Success(it))
                }
            }
        }

    }

    private suspend fun refresh() {
        try {
            Timber.d("flow fetch from server")
            val results = vivahApiService.getMatches().results
            results.map {
                Matches(
                    it.id.name,
                    it.name,
                    null,
                    it.picture.large,
                    it.dob.age,
                    it.location.city,
                    it.location.state
                )
            }.also {
                matchesDao.insertAllMatches(it)
            }
        } catch (exception: Exception) {
            Timber.d("flow $exception")
            flow.emit(Resource.Error(exception.message ?: "error"))
            currentCoroutineContext().cancel(null)
        } catch (cancellationException: CancellationException) {
            Timber.e("flow, cancellation exception $cancellationException")
        }
    }

    suspend fun updateMatchesStatus(id: String, status: Boolean) {
        matchesDao.updateStatus(id, status)
    }
}