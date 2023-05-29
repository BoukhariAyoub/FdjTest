package com.example.fdj.domain.repository

import com.example.fdj.domain.model.League

interface LeagueRepository {
    suspend fun getLeagues(): List<League>
    suspend fun downloadLeagues()
}