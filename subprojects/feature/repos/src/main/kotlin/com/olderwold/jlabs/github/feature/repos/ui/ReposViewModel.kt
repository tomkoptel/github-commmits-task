package com.olderwold.jlabs.github.feature.repos.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.olderwold.jlabs.github.feature.repos.data.GithubApi
import com.olderwold.jlabs.github.feature.repos.data.NetworkGetRepos
import com.olderwold.jlabs.github.feature.repos.domain.GetRepos
import com.olderwold.jlabs.github.feature.repos.domain.Repo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

internal class ReposViewModel(
    private val getRepos: GetRepos,
) : ViewModel() {
    /**
     * To avoid calling init multiples.
     * We want view model to perform loading
     * once and avoid using init {}
     */
    private var initialized: Boolean = false

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    fun init() {
        if (initialized) return
        loadRepos()
        initialized = true
    }

    private fun loadRepos() = viewModelScope.launch {
        withContext(Dispatchers.Default) {
            _uiState.value = runCatching { getRepos() }
                .let { UiState.Loaded(it) }
        }
    }

    sealed class UiState {
        object Loading : UiState()
        data class Loaded(
            val data: Result<List<Repo>>,
        ) : UiState()
    }

    object Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            val githubApi = GithubApi()
            val reposViewModel = ReposViewModel(NetworkGetRepos(githubApi))
            reposViewModel.init()
            return reposViewModel as T
        }
    }
}
