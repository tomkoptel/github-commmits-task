package com.olderwold.jlabs.github.feature.details.ui

import android.app.Application
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.olderwold.jlabs.github.feature.details.R

@Composable
fun RepoDetailsPage(
    repoName: String,
    modifier: Modifier = Modifier,
) {
    val application = LocalContext.current.applicationContext as Application
    val viewModel = viewModel<YearReportViewModel>(
        factory = YearReportViewModel.Factory(application, repoName)
    )
    when (val uiState = viewModel.uiState.collectAsState(viewModel.defaultState).value) {
        is YearReportUiState.Loaded -> {
            when (val currentResult = uiState.currentResult) {
                is SafeResult.Ok -> {
                    if (currentResult.data.isEmpty()) {
                        NoCommits()
                    } else {
                        CommitCarousel(currentResult.data, modifier = modifier)
                    }
                }
                is SafeResult.Error -> {
                    val previousResult = uiState.previousResult
                    if (previousResult == null) {
                        Text(text = "We failed to get results ${currentResult.throwable.message}")
                    } else {
                        when (previousResult) {
                            is SafeResult.Error -> {
                                Text(text = "We failed to get results ${currentResult.throwable.message}")
                            }
                            is SafeResult.Ok -> {
                                CommitCarousel(previousResult.data)

                                Snackbar(
                                    modifier = Modifier.padding(8.dp)
                                ) {
                                    Text(text = "Failed to get an update ${currentResult.throwable.message}")
                                }
                            }
                        }
                    }
                }
            }
        }
        YearReportUiState.Loading -> {
            Text(text = "Loading details...")
        }
    }
}

@Composable
internal fun NoCommits() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(R.string.no_commits))
    }
}
