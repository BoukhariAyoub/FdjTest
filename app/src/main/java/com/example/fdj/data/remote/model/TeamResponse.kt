package com.example.fdj.data.remote.model

import com.google.gson.annotations.SerializedName

data class TeamsResponse(
    @SerializedName("teams") val teams: List<TeamResponse>,
)

data class TeamResponse(
    @SerializedName("idTeam") val id: String,
    @SerializedName("strTeam") val name: String,
    @SerializedName("strTeamShort") val shortName: String?,
    @SerializedName("strAlternate") val tla: String?,
    @SerializedName("strTeamBadge") val teamBadge: String,
    @SerializedName("intFormedYear") val founded: String?,
    @SerializedName("strStadium") val stadium: String?,
    @SerializedName("intStadiumCapacity") val capacity: String?,
    @SerializedName("strStadiumLocation") val location: String?,
    @SerializedName("strWebsite") val website: String?,
    @SerializedName("strFacebook") val facebook: String?,
    @SerializedName("strTwitter") val twitter: String?,
    @SerializedName("strInstagram") val instagram: String?,
    @SerializedName("strYoutube") val youtube: String?
)