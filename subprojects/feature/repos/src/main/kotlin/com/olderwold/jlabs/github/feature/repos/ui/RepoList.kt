package com.olderwold.jlabs.github.feature.repos.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import com.olderwold.jlabs.github.feature.repos.domain.Repo

@Composable
fun RepoPage(
    viewModelProvider: ReposViewModelProvider,
    onItemClicked: (repoName: String) -> Unit = {}
) {
    val viewModel = viewModelProvider.provide()
    when (val uiState = viewModel.uiState.collectAsState().value) {
        is ReposViewModel.UiState.Loaded -> {
            uiState.data.fold(
                onSuccess = { repos ->
                    RepoList(repos, onItemClicked = onItemClicked)
                },
                onFailure = { error ->
                    Error(message = error.message)
                }
            )
        }
        ReposViewModel.UiState.Loading -> Waiting()
    }
}

@Composable
internal fun RepoList(
    repos: List<Repo>,
    onItemClicked: (repoName: String) -> Unit = {}
) {
    LazyColumn {
        items(repos) { item ->
            RepoItem(item = item, onItemClicked = onItemClicked)
        }
    }
}
