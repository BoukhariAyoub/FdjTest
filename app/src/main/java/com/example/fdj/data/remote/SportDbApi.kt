package com.example.fdj.data.remote

import com.example.fdj.data.remote.model.LeaguesResponse
import com.example.fdj.data.remote.model.TeamsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SportDbApi {
    @GET("{api_key}/all_leagues.php")
    suspend fun getLeagues(@Path("api_key") apiKey: String): LeaguesResponse

    @GET("{api_key}/search_all_teams.php")
    suspend fun getTeamsOfLeague(
        @Path("api_key") apiKey: String,
        @Query("l") leagueName: String
    ): TeamsResponse
}











