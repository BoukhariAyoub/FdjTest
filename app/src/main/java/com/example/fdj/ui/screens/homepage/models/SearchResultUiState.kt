package com.example.fdj.ui.screens.homepage.models

import com.example.fdj.domain.model.Team

data class SearchResultUiState(
    val isLoading: Boolean = false,
    val searchResults : List<Team> = emptyList(),
    val error: String = ""
)