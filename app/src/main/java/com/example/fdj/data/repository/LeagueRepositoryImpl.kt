package com.example.fdj.data.repository

import com.example.fdj.BuildConfig
import com.example.fdj.data.local.local.LeagueDao
import com.example.fdj.data.local.local.LeagueDto
import com.example.fdj.data.remote.SportDbApi
import com.example.fdj.data.remote.model.LeagueResponse
import com.example.fdj.domain.model.League
import com.example.fdj.domain.repository.LeagueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LeagueRepositoryImpl(
    private val api: SportDbApi,
    private val db: LeagueDao
) : LeagueRepository {

    /**
     * Download Leagues from Api to localDb
     */
    override suspend fun downloadLeagues() {
        val leagues = api.getLeagues(BuildConfig.API_KEY).leagues
            .map { it.toDb() }

        db.insertAll(leagues)
    }

    override suspend fun getLeagues(): List<League> {
        return withContext(Dispatchers.Default) {
            db.getAll().map {
                it.toDomain()
            }
        }
    }

    private fun LeagueDto.toDomain(): League {
        return League(
            idLeague = idLeague,
            strLeague = strLeague
        )
    }

    private fun LeagueResponse.toDb(): LeagueDto {
        return LeagueDto(
            idLeague = idLeague,
            strLeague = strLeague,
            strSport = strSport,
            strLeagueAlternate = strLeagueAlternate
        )
    }
}