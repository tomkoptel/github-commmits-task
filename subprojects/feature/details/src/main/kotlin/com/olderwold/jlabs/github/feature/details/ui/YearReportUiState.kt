package com.olderwold.jlabs.github.feature.details.ui

internal sealed class YearReportUiState {
    fun areContentsTheSame(new: YearReportUiState): Boolean = when (val previous = this) {
        is Loaded -> {
            when (new) {
                is Loaded -> new.currentResult.getOrNull() == previous.currentResult.getOrNull()
                Loading -> false
            }
        }
        Loading -> {
            when (new) {
                is Loaded -> false
                Loading -> true
            }
        }
    }

    fun reduce(new: YearReportUiState): YearReportUiState = when (val previous = this) {
        is Loaded -> {
            when (new) {
                is Loaded -> new.copy(previousResult = previous.currentResult)
                Loading -> new
            }
        }
        Loading -> {
            when (new) {
                is Loaded -> new
                Loading -> new
            }
        }
    }

    object Loading : YearReportUiState()

    data class Loaded(
        val previousResult: Result<YearReport>? = null,
        val currentResult: Result<YearReport>,
    ) : YearReportUiState()
}
