package com.example.vivah.networking

import com.example.vivah.db.model.MatchesResponse
import retrofit2.http.GET

interface VivahApiService {

    @GET("api/?results=10")
    suspend fun getMatches(): MatchesResponse
}