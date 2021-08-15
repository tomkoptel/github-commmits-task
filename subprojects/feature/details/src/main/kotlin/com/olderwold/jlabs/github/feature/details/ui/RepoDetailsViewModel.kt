package com.olderwold.jlabs.github.feature.details.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.olderwold.jlabs.github.feature.details.BuildConfig
import com.olderwold.jlabs.github.feature.details.data.GithubApi
import com.olderwold.jlabs.github.feature.details.data.NetworkGetDetails
import com.olderwold.jlabs.github.feature.details.domain.GetDetails
import com.olderwold.jlabs.github.interval
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import okhttp3.logging.HttpLoggingInterceptor
import java.time.LocalDate
import java.util.concurrent.TimeUnit

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

    var uiState: SharedFlow<UiState> = MutableStateFlow(UiState.Loading)
        private set

    @FlowPreview
    fun init(repoName: String) {
        if (initialized) return
        startTimedUpdates(repoName)
        initialized = true
    }

    private fun startTimedUpdates(repoName: String) {
        var previousState: UiState = UiState.Loading
        uiState = interval(delay = 1500, unit = TimeUnit.MILLISECONDS)
            .flatMapConcat {
                computeUiState(previousState, repoName)
                    .map { newState ->
                        previousState = newState
                        newState
                    }
            }
            .distinctUntilChanged { old, new ->
                old.areContentsTheSame(new)
            }
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily
            )
    }

    private fun computeUiState(
        previousState: UiState?,
        repoName: String
    ): Flow<UiState> = flow {
        val newResult = runCatching { getDetails(repoName) }
            .map { details ->
                val year = details.firstCommitDate?.year ?: LocalDate.now().year
                yearReportFactory.create(details, year)
            }
        if (previousState == null) {
            UiState.Loaded(currentResult = newResult)
        } else {
            previousState.reduce(UiState.Loaded(currentResult = newResult))
        }
    }

    sealed class UiState {
        fun areContentsTheSame(new: UiState): Boolean = when (val previous = this) {
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
            val githubApi = GithubApi(clientBuilder = {
                if (BuildConfig.DEBUG) {
                    addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            })
            val getDetails = NetworkGetDetails(githubApi)
            val factory = YearReport.Factory(application)
            val repoDetailsViewModel = RepoDetailsViewModel(getDetails, factory)
            repoDetailsViewModel.init(repoName)
            return repoDetailsViewModel as T
        }
    }
}
