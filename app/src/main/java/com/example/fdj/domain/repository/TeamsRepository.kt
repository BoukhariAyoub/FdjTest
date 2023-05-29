package com.example.fdj.domain.repository

import com.example.fdj.domain.model.Team

interface TeamsRepository {
    suspend fun getTeamsByLeagueName(leagueName: String): List<Team>
}