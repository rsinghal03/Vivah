package com.example.vivah.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.vivah.db.dao.MatchesDao
import com.example.vivah.db.entity.Matches

@Database(entities = [Matches::class], version = 1, exportSchema = false)
abstract class VivahDatabase : RoomDatabase() {

    abstract val matchesDao: MatchesDao
}