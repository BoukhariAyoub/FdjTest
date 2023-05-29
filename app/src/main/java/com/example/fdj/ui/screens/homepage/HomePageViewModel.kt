package com.example.fdj.ui.screens.homepage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fdj.domain.model.Resource
import com.example.fdj.domain.usecase.DownloadLeaguesUseCase
import com.example.fdj.domain.usecase.GetLeaguesByNameUseCase
import com.example.fdj.domain.usecase.GetTeamsByLeagueUseCase
import com.example.fdj.ui.screens.homepage.models.SearchResultUiState
import com.example.fdj.ui.screens.homepage.models.SuggestionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(
    private val downloadLeaguesUseCase: DownloadLeaguesUseCase,
    private val getTeamsByLeagueUseCase: GetTeamsByLeagueUseCase,
    private val getLeaguesByNameUseCase: GetLeaguesByNameUseCase,
) : ViewModel() {

    private val _querySearch = MutableStateFlow("")
    val querySearch: StateFlow<String> = _querySearch

    private val _suggestions = MutableStateFlow(SuggestionUiState(isLoading = false))
    val suggestions: StateFlow<SuggestionUiState> = _suggestions

    private val _searchResults = MutableStateFlow(SearchResultUiState(isLoading = false))
    val searchResults: StateFlow<SearchResultUiState> = _searchResults

    init {
        viewModelScope.launch(Dispatchers.IO) {
            //in every app start we refresh our db
            downloadLeaguesUseCase.downloadLeagues()
        }
    }

    fun clearSearch() {
        _querySearch.value = ""
    }

    fun onQueryChanged(query: String) {
        _querySearch.value = query

        viewModelScope.launch {
            getLeaguesByNameUseCase.getFilteredList(query).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _suggestions.emit(SuggestionUiState(suggestions = result.data.map { it.strLeague }))
                    }

                    is Resource.Error -> {
                        _suggestions.emit(SuggestionUiState(error = result.message))
                    }

                    is Resource.Loading -> {
                        _suggestions.emit(SuggestionUiState(isLoading = true))
                    }
                }
            }
        }
    }

    fun onLeagueSelected(leagueName: String) {
        _querySearch.value = leagueName
        _suggestions.value = SuggestionUiState(suggestions = emptyList())

        viewModelScope.launch(Dispatchers.IO) {
            getTeamsByLeagueUseCase.getTeamsByLeague(leagueName).collectLatest { result ->
                when (result) {
                    is Resource.Success -> {
                        _searchResults.emit(SearchResultUiState(searchResults = result.data))
                    }

                    is Resource.Error -> {
                        _searchResults.emit(SearchResultUiState(error = result.message))
                    }

                    is Resource.Loading -> {
                        _searchResults.emit(SearchResultUiState(isLoading = true))
                    }
                }
            }
        }
    }
}