package com.example.fdj.di

import android.content.Context
import com.example.fdj.data.local.local.AppDatabase
import com.example.fdj.data.local.local.LeagueDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    fun provideDao(appDatabase: AppDatabase): LeagueDao {
        return appDatabase.leagueDao()
    }
}