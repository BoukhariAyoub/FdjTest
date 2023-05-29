package com.example.fdj.domain.usecase

import com.example.fdj.domain.model.League
import com.example.fdj.domain.model.Resource
import com.example.fdj.domain.repository.LeagueRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetLeaguesByNameUseCase @Inject constructor(private val repository: LeagueRepository) {

    fun getFilteredList(query: String): Flow<Resource<List<League>>> =
        flow {
            try {
                val localLeagues = repository.getLeagues()
                if (localLeagues.isNotEmpty()) {
                    //send success results if not empty
                    emit(Resource.Success(localLeagues.filteredList(query)))
                } else {
                    //if its empty we send loading
                    emit(Resource.Loading)
                    //if its empty we download from Api
                    repository.downloadLeagues()
                    //and send success results
                    emit(Resource.Success(repository.getLeagues().filteredList(query)))
                }
            } catch (e: HttpException) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected Http error"))
            } catch (e: IOException) {
                emit(Resource.Error("Couldn't reach server. Check your internet connection"))
            } catch (e: Throwable) {
                emit(Resource.Error(e.localizedMessage ?: "An unexpected error"))
            }
        }

    private fun List<League>.filteredList(query: String): List<League> {
        return this.filter { it.strLeague.containsIgnoreCaseAndOrder(query) }
    }

    private fun String.containsIgnoreCaseAndOrder(searchString: String): Boolean {
        val regex = searchString.split("\\s+".toRegex())
            .joinToString(".*") { Regex.escape(it) }
            .let { Regex(".*$it.*", RegexOption.IGNORE_CASE) }

        return regex.matches(this)
    }
}