package com.olderwold.jlabs.github.feature.details.data

import com.olderwold.jlabs.github.feature.details.domain.GetDetails
import com.olderwold.jlabs.github.feature.details.domain.RepoDetails

internal class NetworkGetDetails(
    private val githubApi: GithubApi,
) : GetDetails {
    override suspend fun invoke(
        repoName: String
    ): RepoDetails {
        githubApi.repoDetails(repoName).firstOrNull()
        return RepoDetails()
    }
}
