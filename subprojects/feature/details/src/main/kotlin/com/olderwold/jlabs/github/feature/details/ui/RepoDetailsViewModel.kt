package com.olderwold.jlabs.github.feature.details.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.olderwold.jlabs.github.feature.details.data.GithubApi
import com.olderwold.jlabs.github.feature.details.data.NetworkGetDetails
import com.olderwold.jlabs.github.feature.details.domain.GetDetails
import java.time.LocalDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class RepoDetailsViewModel(
    private val getDetails: GetDetails,
    private val yearReportFactory: YearReport.Factory,
) : ViewModel() {
    /**
     * To avoid calling init multiples.
     * We want view model to perform loading
     * once and avoid using init {}
     */
    private var initialized: Boolean = false

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun init(repoName: String) {
        if (initialized) return
        loadDetails(repoName)
        initialized = true
    }

    private fun loadDetails(repoName: String) = viewModelScope.launch {
        withContext(Dispatchers.Default) {
            runCatching { getDetails(repoName) }
                .map { details ->
                    val year = details.firstCommitDate?.year ?: LocalDate.now().year
                    yearReportFactory.create(details, year)
                }
                .let(::emitState)
        }
    }

    private fun emitState(result: Result<YearReport>) {
        val previousResult = requireNotNull(_uiState.value) { "Should be set by default" }
        _uiState.value = previousResult.reduce(UiState.Loaded(currentResult = result))
    }

    sealed class UiState {
        fun reduce(new: UiState): UiState = when (val previous = this) {
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

        object Loading : UiState()

        data class Loaded(
            val previousResult: Result<YearReport>? = null,
            val currentResult: Result<YearReport>,
        ) : UiState()
    }

    class Factory(
        private val application: Application,
        private val repoName: String,
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val githubApi = GithubApi()
            val getDetails = NetworkGetDetails(githubApi)
            val factory = YearReport.Factory(application)
            val repoDetailsViewModel = RepoDetailsViewModel(getDetails, factory)
            repoDetailsViewModel.init(repoName)
            return repoDetailsViewModel as T
        }
    }
}
