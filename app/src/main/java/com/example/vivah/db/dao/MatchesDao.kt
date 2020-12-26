package com.example.vivah.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vivah.db.entity.Matches

@Dao
interface MatchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllMatches(listOfMatches: List<Matches>)

    @Query("select * from Matches")
    fun getListOfMatches(): List<Matches>
}