package com.example.vivah.ui.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.example.vivah.db.model.MatchesResponse
import com.example.vivah.repository.MatchesRepository
import com.example.vivah.ui.base.BaseViewModel
import com.example.vivah.util.Resource
import timber.log.Timber

class MatchesViewModel(private val matchesRepository: MatchesRepository) : BaseViewModel() {


    fun getMatches(): LiveData<Resource<List<MatchesResponse.Result>>> {
        showProgressBar.value = true
        return liveData {
            Timber.d("%sthread livedatascope", Thread.currentThread().name)
            try {
                emit(Resource.success(matchesRepository.getMatches()))
            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error ocurred"))
            }
        }
    }

}