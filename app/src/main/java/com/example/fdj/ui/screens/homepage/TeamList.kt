package com.example.fdj.ui.screens.homepage.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.fdj.R
import com.example.fdj.ui.screens.homepage.models.SearchResultUiState

@Composable
fun TeamList(result: SearchResultUiState, onItemClicked: (String) -> Unit) {


    when {
        result.searchResults.isNotEmpty() -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = result.searchResults, key = { it.id }) {
                    TeamItem(it.crestUrl) { onItemClicked(it.id) }
                }
            }
        }

        result.isLoading -> Loading()
        result.error.isNotBlank() -> Error(result.error)

    }
}

@Composable
private fun TeamItem(teamLogo: String, onItemClicked: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .size(120.dp)
            .fillMaxWidth()
            .clickable { onItemClicked() }) {
        SubcomposeAsyncImage(
            model = teamLogo,
            loading = {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            },
            contentDescription = stringResource(R.string.team_logo)
        )
    }
}

@Composable
private fun Loading() {
    Box(Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun Error(error: String) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Text(modifier = Modifier.align(Alignment.Center), text = error)
    }
}