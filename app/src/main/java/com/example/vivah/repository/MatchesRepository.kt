package com.example.vivah.repository

import com.example.vivah.db.dao.MatchesDao
import com.example.vivah.db.entity.Matches
import com.example.vivah.db.model.MatchesResponse
import com.example.vivah.networking.VivahApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MatchesRepository(
    private val vivahApiService: VivahApiService,
    private val matchesDao: MatchesDao,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)
) {

    suspend fun getMatches(): List<MatchesResponse.Result> {
        val results = vivahApiService.getMatches().results
        coroutineScope.launch {
            results.map {
                Matches(
                    it.id.name,
                    it.name,
                    null,
                    it.picture.large
                )
            }.also {
                matchesDao.insertAllMatches(it)
            }
        }
        return results
    }
}