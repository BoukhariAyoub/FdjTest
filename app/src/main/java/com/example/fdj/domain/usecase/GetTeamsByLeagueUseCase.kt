package com.example.fdj.domain.usecase

import com.example.fdj.domain.model.Resource
import com.example.fdj.domain.model.Team
import com.example.fdj.domain.repository.TeamsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetTeamsByLeagueUseCase @Inject constructor(private val repository: TeamsRepository) {

    suspend fun getTeamsByLeague(leagueName: String): Flow<Resource<List<Team>>> = flow {
        try {
            emit(Resource.Loading)
            val teams = repository.getTeamsByLeagueName(leagueName).sortAndFilter()
            emit(Resource.Success(teams))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected Http error"))
        } catch (e: IOException) {
            emit(Resource.Error("Couldn't reach server. Check your internet connection"))
        } catch (e: Throwable) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
        }
    }

    /*
        Sorts the list of teams in reverse alphabetical order
        and filters it to show only every second team.
     */
    private fun List<Team>.sortAndFilter(): List<Team> {
        return this.sortedByDescending { it.name }
            .filterIndexed { index, _ -> index % 2 == 0 }
    }
}
