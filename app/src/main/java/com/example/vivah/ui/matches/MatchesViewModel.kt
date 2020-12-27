package com.example.vivah.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.vivah.db.entity.Matches
import com.example.vivah.repository.MatchesRepository
import com.example.vivah.ui.base.BaseViewModel
import com.example.vivah.util.Resource
import kotlinx.coroutines.launch

class MatchesViewModel(private val matchesRepository: MatchesRepository) : BaseViewModel() {


    fun getMatches(): LiveData<Resource<List<Matches>?>> {
        showProgressBar.value = true
        matchesRepository.getMatches()
        return matchesRepository.flow.asLiveData()
    }

    fun updateStatus(it: Matches, status: Boolean) {
        ioDispatcher.launch {
            matchesRepository.updateMatchesStatus(it.id, status)
        }
    }
}