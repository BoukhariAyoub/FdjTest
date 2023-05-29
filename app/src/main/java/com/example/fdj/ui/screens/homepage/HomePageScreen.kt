package com.example.fdj.ui.screens.homepage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.fdj.R
import com.example.fdj.ui.screens.homepage.component.LeagueList
import com.example.fdj.ui.screens.homepage.component.TeamList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePageScreen(viewModel: HomePageViewModel = hiltViewModel()) {

    val query = viewModel.querySearch.collectAsStateWithLifecycle()
    val suggestions = viewModel.suggestions.collectAsStateWithLifecycle()
    val searchResults = viewModel.searchResults.collectAsStateWithLifecycle()
    var active by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                query = query.value,
                onQueryChange = { newText ->
                    viewModel.onQueryChanged(newText)
                },
                onSearch = { },
                active = active,
                onActiveChange = { active = it },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.clickable {
                            viewModel.clearSearch()
                            active = true
                        },
                        imageVector = Icons.Default.Delete,
                        contentDescription = stringResource(R.string.delete_icon_content_desc)
                    )
                }) {
                LeagueList(suggestions.value) {
                    viewModel.onLeagueSelected(it)
                    active = false
                }
            }
        }) { innerPadding ->
        Column(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            TeamList(searchResults.value) {}
        }
    }
}