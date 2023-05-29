package com.example.fdj.data.local.local

import androidx.room.*

@Entity(tableName = "league")
data class LeagueDto(
    @PrimaryKey val idLeague: Int,
    @ColumnInfo val strLeague: String,
    @ColumnInfo val strSport: String?,
    @ColumnInfo val strLeagueAlternate: String?
)