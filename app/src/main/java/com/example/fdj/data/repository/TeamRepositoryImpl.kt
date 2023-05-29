package com.example.fdj.data.repository

import com.example.fdj.BuildConfig
import com.example.fdj.data.remote.model.TeamResponse
import com.example.fdj.data.remote.SportDbApi
import com.example.fdj.domain.model.Team
import com.example.fdj.domain.repository.TeamsRepository

class TeamRepositoryImpl(private val api: SportDbApi) : TeamsRepository {


    override suspend fun getTeamsByLeagueName(leagueName: String): List<Team> {
        return api.getTeamsOfLeague(BuildConfig.API_KEY,leagueName).teams
            .map { it.toDomain() }
    }

    private fun TeamResponse.toDomain(): Team {
        return Team(
            id = id,
            name = name,
            crestUrl = teamBadge
        )
    }

}