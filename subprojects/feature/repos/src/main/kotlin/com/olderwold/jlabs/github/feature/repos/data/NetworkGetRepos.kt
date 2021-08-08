package com.olderwold.jlabs.github.feature.repos.data

import com.olderwold.jlabs.github.feature.repos.domain.GetRepos
import com.olderwold.jlabs.github.feature.repos.domain.Repo

internal class NetworkGetRepos(
    private val githubApi: GithubApi,
): GetRepos {
    override suspend fun invoke(): List<Repo> {
        return githubApi.repos().mapNotNull { dto ->
            dto.name?.let { Repo(it) }
        }
    }
}
