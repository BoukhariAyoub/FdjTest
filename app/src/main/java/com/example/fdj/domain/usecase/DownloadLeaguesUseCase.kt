package com.example.fdj.domain.usecase

import com.example.fdj.domain.repository.LeagueRepository
import javax.inject.Inject

class DownloadLeaguesUseCase @Inject constructor(private val repository: LeagueRepository) {

    suspend fun downloadLeagues() {
        repository.downloadLeagues()
    }
}