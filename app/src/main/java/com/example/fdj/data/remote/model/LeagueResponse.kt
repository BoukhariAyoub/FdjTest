package com.example.fdj.data.remote.model

import com.google.gson.annotations.SerializedName

data class LeaguesResponse(
    @SerializedName("leagues") val leagues: List<LeagueResponse>,
)
data class LeagueResponse(
    @SerializedName("idLeague") val idLeague: Int,
    @SerializedName("strLeague") val strLeague: String,
    @SerializedName("strSport") val strSport: String?,
    @SerializedName("strLeagueAlternate") val strLeagueAlternate: String?
)
