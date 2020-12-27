package com.example.vivah.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.vivah.db.entity.Matches
import com.example.vivah.repository.MatchesRepository
import com.example.vivah.ui.base.BaseViewModel
import com.example.vivah.util.Resource
import kotlinx.coroutines.launch

class MatchesViewModel(private val matchesRepository: MatchesRepository) : BaseViewModel() {

    // initialization time call to fetch the matches which should not be called
    // on configuration changes like device rotation
    init {
        showProgressBar.value = true
        fetchMatches()
    }

    /**
     * to make network call on particular user action like pull to refresh or
     * for periodic update from the web service we can use this function
     *
     */
    fun fetchMatches() {
        matchesRepository.getMatches()
    }

    fun getMatches(): LiveData<Resource<List<Matches>?>> {
        return matchesRepository.flow.asLiveData()
    }

    fun updateStatus(matches: Matches, status: Boolean) {
        ioDispatcher.launch {
            matchesRepository.updateMatchesStatus(matches.id, status)
        }
    }
}