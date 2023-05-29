package com.example.fdj.ui.screens.homepage.models

data class SuggestionUiState(
    val isLoading: Boolean = false,
    val suggestions: List<String> = emptyList(),
    val error: String = ""
)