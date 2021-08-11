package com.olderwold.jlabs.github.feature.details.ui

import android.app.Application
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun RepoDetailsPage(repoName: String) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel = viewModel<RepoDetailsViewModel>(
        factory = RepoDetailsViewModel.Factory(application, repoName)
    )
    when (val uiState = viewModel.uiState.collectAsState().value) {
        is RepoDetailsViewModel.UiState.Loaded -> {
            uiState.currentResult.fold(
                onSuccess = {
                    RepoDetails(items = it)
                },
                onFailure = { currentError ->
                    val previousResult = uiState.previousResult?.getOrNull()
                    if (previousResult == null) {
                        Text(text = "We failed to get results ${currentError.message}")
                    } else {
                        RepoDetails(items = previousResult)

                        Snackbar(
                            modifier = Modifier.padding(8.dp)
                        ) {
                            Text(text = "Failed to get an update ${currentError.message}")
                        }
                    }
                }
            )
        }
        RepoDetailsViewModel.UiState.Loading -> {
            Text(text = "Loading details...")
        }
    }
}
