package com.olderwold.jlabs.github.feature.details.data

import com.olderwold.jlabs.github.feature.details.domain.GetDetails
import com.olderwold.jlabs.github.feature.details.domain.RepoDetails

internal class NetworkGetDetails(
    private val githubApi: GithubApi,
    private val repoDetailsFactory: RepoDetailsFactory,
) : GetDetails {
    constructor(githubApi: GithubApi) : this(githubApi, RepoDetailsFactory())

    override suspend fun invoke(
        repoName: String
    ): RepoDetails {
        val commitsData = githubApi.repoDetails(repoName)
        return repoDetailsFactory.create(repoName, commitsData)
    }
}
