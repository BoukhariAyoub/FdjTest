package com.example.fdj.ui.screens.homepage.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.fdj.R
import com.example.fdj.ui.screens.homepage.models.SuggestionUiState

@Composable
fun LeagueList(
    result: SuggestionUiState,
    onItemClicked: (String) -> Unit
) {
    when {
        result.suggestions.isNotEmpty() -> {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(items = result.suggestions, key = { it }) {
                    LeagueItem(it) { onItemClicked(it) }
                }
            }
        }

        result.isLoading -> Loading()
        result.error.isNotBlank() -> Error(result.error)
    }
}

@Composable
private fun LeagueItem(leagueName: String, onItemClicked: () -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable { onItemClicked() }) {
        Text(
            modifier = Modifier.padding(8.dp),
            text = leagueName
        )
    }
}

@Composable
private fun Loading() {
    Box(Modifier.fillMaxWidth()) {
        Text(text = stringResource(R.string.loading_text))
    }
}

@Composable
private fun Error(error: String) {
    Box(Modifier.fillMaxWidth()) {
        Text(text = error)
    }
}