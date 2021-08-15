package com.olderwold.jlabs.github.feature.details.ui

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.olderwold.jlabs.github.emitThenDelay
import com.olderwold.jlabs.github.feature.details.BuildConfig
import com.olderwold.jlabs.github.feature.details.data.GithubApi
import com.olderwold.jlabs.github.feature.details.data.NetworkGetDetails
import com.olderwold.jlabs.github.feature.details.domain.GetDetails
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import okhttp3.logging.HttpLoggingInterceptor
import java.time.LocalDate
import java.util.concurrent.TimeUnit

@OptIn(FlowPreview::class)
internal class YearReportViewModel(
    private val getDetails: GetDetails,
    private val yearReportFactory: YearReport.Factory,
) : ViewModel() {
    /**
     * To avoid calling init multiples.
     * We want view model to perform loading
     * once and avoid using init {}
     */
    private var initialized: Boolean = false

    var uiState: Flow<YearReportUiState> = emptyFlow()
        private set

    val defaultState: YearReportUiState = YearReportUiState.Loading

    fun init(repoName: String) {
        if (initialized) return
        startTimedUpdates(repoName)
        initialized = true
    }

    private fun startTimedUpdates(repoName: String) {
        var previousState: YearReportUiState = defaultState
        val requestCommits = computeUiState(previousState, repoName)
            .map { newState ->
                previousState = newState
                newState
            }
            .distinctUntilChanged { old, new ->
                old.areContentsTheSame(new)
            }

        uiState = emitThenDelay(delay = 15, unit = TimeUnit.SECONDS)
            .flatMapConcat { requestCommits }
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.Lazily
            )
    }

    private fun computeUiState(
        previousState: YearReportUiState,
        repoName: String
    ): Flow<YearReportUiState> = flow {
        val newResult = runCatching { getDetails(repoName) }
            .map { details ->
                val year = details.firstCommitDate?.year ?: LocalDate.now().year
                yearReportFactory.create(details, year)
            }
            .toSafeResult()
        previousState.reduce(
            YearReportUiState.Loaded(currentResult = newResult)
        ).let { emit(it) }
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
            val repoDetailsViewModel = YearReportViewModel(getDetails, factory)
            repoDetailsViewModel.init(repoName)
            return repoDetailsViewModel as T
        }
    }
}
