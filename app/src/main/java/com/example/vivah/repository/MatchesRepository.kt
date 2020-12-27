package com.example.vivah.repository

import com.example.vivah.db.dao.MatchesDao
import com.example.vivah.db.entity.Matches
import com.example.vivah.networking.VivahApiService
import com.example.vivah.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Matches repository is created to abstract out the backend call. ViewModel does not know from where data is coming.
 * From local database or from web service.
 *
 */
class MatchesRepository(
    private val vivahApiService: VivahApiService,
    private val matchesDao: MatchesDao,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    // Single source of data to flow from repository
    val flow = MutableSharedFlow<Resource<List<Matches>?>>(replay = 1)

    @Volatile
    private var isStatusSuccess = false

    fun getMatches() {
        coroutineScope.launch { fetchFromNetwork() }
        coroutineScope.launch { subscribeToDb() }
    }

    /**
     * Data to the UI is only flowing from db.
     *
     */
    private fun subscribeToDb() {
        coroutineScope.launch { getFromLocalDb() }
    }


    /**
     * subscribe to the db and update single source of data [flow]
     *
     */
    private suspend fun getFromLocalDb() {
        Timber.d("flow fetchFromLocalDb")
        matchesDao.getDistinctLiveListOfMatches().collect {
            if (it?.isEmpty() == true) {
                Timber.d("flow loading")
                flow.emit(Resource.Loading(null))
            } else {
                Timber.d("flow success")
                isStatusSuccess = true
                flow.emit(Resource.Success(it))
            }
        }
    }

    /**
     * make network call, update single source of data [flow]
     * on success insert data into db and on failure if there is no local data available
     * emit error else do nothing
     *
     */
    private suspend fun fetchFromNetwork() {
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
                Timber.d("flow insert into db")
                matchesDao.insertAllMatches(it)
            }
        } catch (exception: Exception) {
            Timber.d("flow $exception")
            if (!isStatusSuccess)
                flow.emit(Resource.Error(exception.message ?: "error"))
        }
    }

    suspend fun updateMatchesStatus(id: String, status: Boolean) {
        matchesDao.updateStatus(id, status)
    }
}