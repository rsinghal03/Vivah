package com.example.vivah.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vivah.db.entity.Matches
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged

@Dao
interface MatchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMatches(listOfMatches: List<Matches>)

    @Query("select * from Matches")
    fun getLiveListOfMatches(): Flow<List<Matches>?>

    fun getDistinctLiveListOfMatches() = getLiveListOfMatches().distinctUntilChanged()

    @Query("update Matches set statusIsAccepted= :status where id =:id")
    suspend fun updateStatus(id: String, status: Boolean)
}