package com.olderwold.jlabs.github.feature.details.ui

internal sealed class YearReportUiState {
    fun areContentsTheSame(new: YearReportUiState): Boolean = when (val previous = this) {
        is Loaded -> {
            when (new) {
                is Loaded -> new.currentResult == previous.currentResult
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

    @Suppress("NestedBlockDepth")
    fun reduce(new: YearReportUiState): YearReportUiState = when (val previous = this) {
        is Loaded -> {
            when (new) {
                is Loaded -> {
                    val newResult: SafeResult<YearReport> = new.currentResult
                    val previousResult: SafeResult<YearReport> = previous.currentResult

                    when (newResult) {
                        is SafeResult.Error -> when (previousResult) {
                            is SafeResult.Error -> {
                                new.copy(previousResult = previousResult)
                            }
                            is SafeResult.Ok -> {
                                previous
                            }
                        }
                        is SafeResult.Ok -> {
                            new.copy(previousResult = previousResult)
                        }
                    }
                }
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
        val previousResult: SafeResult<YearReport>? = null,
        val currentResult: SafeResult<YearReport>,
    ) : YearReportUiState()
}
