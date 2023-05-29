package com.example.fdj.data.local.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface LeagueDao {

    @Query("SELECT * FROM league")
    fun getAll(): List<LeagueDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(leagues: List<LeagueDto>)

    @Query("DELETE FROM league")
    suspend fun deleteAll()
}