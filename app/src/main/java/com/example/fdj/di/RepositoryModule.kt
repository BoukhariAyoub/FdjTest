package com.example.fdj.di


import com.example.fdj.data.local.local.LeagueDao
import com.example.fdj.data.remote.SportDbApi
import com.example.fdj.data.repository.LeagueRepositoryImpl
import com.example.fdj.data.repository.TeamRepositoryImpl
import com.example.fdj.domain.repository.LeagueRepository
import com.example.fdj.domain.repository.TeamsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLeagueRepository(api: SportDbApi, db: LeagueDao): LeagueRepository {
        return LeagueRepositoryImpl(api, db)
    }

    @Singleton
    @Provides
    fun provideTeamsRepository(api: SportDbApi): TeamsRepository {
        return TeamRepositoryImpl(api)
    }
}

